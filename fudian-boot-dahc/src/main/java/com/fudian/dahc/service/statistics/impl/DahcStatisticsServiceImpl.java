package com.fudian.dahc.service.statistics.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.dahc.mapper.business.DahcDataTemplateMapper;
import com.fudian.dahc.mapper.recordTrueing.DahcRecordProcedureFilesMapper;
import com.fudian.dahc.pojo.dto.QueryDahcRecordProcedureFilesDto;
import com.fudian.dahc.pojo.dto.StatisticsDto;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.business.DahcFilePhoto;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedure;
import com.fudian.dahc.pojo.entity.project.DahcProjectTable;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import com.fudian.dahc.pojo.entity.sys.DahcSysUserProcedure;
import com.fudian.dahc.service.business.ArchiveTypeService;
import com.fudian.dahc.service.business.DahcFilePhotoService;
import com.fudian.dahc.service.project.ProjectProcedureService;
import com.fudian.dahc.service.project.ProjectTableService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordProcedureFilesService;
import com.fudian.dahc.service.statistics.DahcStatisticsService;
import com.fudian.dahc.service.sys.IDahcSysUserProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fudian
 * @date 2023-02-24
 */
@Service
public class DahcStatisticsServiceImpl implements DahcStatisticsService {

    @Autowired
    private ProjectTableService projectTableService;

    @Autowired
    private IDahcSysUserProcedureService dahcSysUserProcedureService;

    @Autowired
    private ProjectProcedureService projectProcedureService;

    @Autowired
    private IDahcRecordProcedureFilesService dahcRecordProcedureFilesService;

    @Autowired
    private DahcFilePhotoService dahcFilePhotoService;

    @Autowired
    private ArchiveTypeService archiveTypeService;

    @Autowired
    private DahcDataTemplateMapper dahcDataTemplateMapper;

    @Autowired
    private DahcRecordProcedureFilesMapper dahcRecordProcedureFilesMapper;

    @Override
    public List<StatisticsDto> verificationOfProjectStatistics(Integer dataUnderVerification) {
        List<StatisticsDto> statisticsDtos = new ArrayList<>();
        List<DahcProjectTable> dahcProjectTables = new ArrayList<>();
        /*核查中数据*/
        if (dataUnderVerification == 0) {
            /*项目数据*/
            dahcProjectTables = projectTableService.list(Wrappers.<DahcProjectTable>lambdaQuery().eq(DahcProjectTable::getProjectState, 1));
        } else {
            /*项目数据*/
            dahcProjectTables = projectTableService.list();
        }
        List<String> projectIds = dahcProjectTables.stream().map(DahcProjectTable::getId).collect(Collectors.toList());
        /*查询核查人数*/
        List<DahcSysUserProcedure> sysUserProcedures = dahcSysUserProcedureService.list(Wrappers.<DahcSysUserProcedure>lambdaQuery().in(DahcSysUserProcedure::getProjectId, projectIds).groupBy(DahcSysUserProcedure::getUserId));
        /*工序数据*/
        List<DahcProjectProcedure> dahcProjectProcedures = projectProcedureService.list(Wrappers.<DahcProjectProcedure>lambdaQuery().in(DahcProjectProcedure::getProjectId, projectIds).orderByAsc(DahcProjectProcedure::getSort));
        /*获取案卷数据*/
        List<DahcRecordProcedureFiles> dahcRecordProcedureFiles = dahcRecordProcedureFilesService.list(Wrappers.<DahcRecordProcedureFiles>lambdaQuery().in(DahcRecordProcedureFiles::getProjectId, projectIds));
        /*图像数量*/
        List<DahcFilePhoto> dahcFilePhotos = dahcFilePhotoService.list(Wrappers.<DahcFilePhoto>lambdaQuery().in(DahcFilePhoto::getProjectId, projectIds).eq(DahcFilePhoto::getFileDelete, 0));
        for (DahcProjectTable projectTables : dahcProjectTables) {
            /*核查人数数量*/
            int sysUserProceduresCount = sysUserProcedures.stream().filter(o -> o.getProjectId().equals(projectTables.getId())).collect(Collectors.toList()).size();
            int dahcFilePhotosCount = dahcFilePhotos.stream().filter(o -> o.getProjectId().equals(projectTables.getId())).collect(Collectors.toList()).size();
            /*项目第一道工序ID*/
            int dahcRecordProcedureFilesCount = 0;
            List<DahcProjectProcedure> projectProcedures = dahcProjectProcedures.stream().filter(o -> o.getProjectId().equals(projectTables.getId())).collect(Collectors.toList());
            if (projectProcedures.size() > 0) {
                DahcProjectProcedure projectProcedure = projectProcedures.get(0);
                dahcRecordProcedureFilesCount = dahcRecordProcedureFiles.stream().filter(o -> o.getProjectId().equals(projectTables.getId())).filter(o -> o.getProcedureId().equals(projectProcedure.getId())).collect(Collectors.toList()).size();
            }
            /*获取案件表名称*/
            String archiveTypeName = archiveTypeService.getOne(Wrappers.<DahcArchiveType>lambdaQuery().eq(DahcArchiveType::getId, projectTables.getArchiveTypeId())).getTableLevel2En();
            /*案件数量*/
            int list = dahcDataTemplateMapper.queryTheAmountOfFileDataBasedOnTheProjectCount(projectTables.getId(), archiveTypeName);

            statisticsDtos.add(new StatisticsDto(projectTables.getProjectName(),
                    projectTables.getProjectState(),
                    getTheNumberOfDays(projectTables),
                    sysUserProceduresCount, dahcRecordProcedureFilesCount, list, dahcFilePhotosCount));
        }

        return statisticsDtos;
    }

    @Override
    public List<StatisticsDto> verificationItemsAndPeopleCounting(Integer dataUnderVerification) {
        List<DahcProjectTable> dahcProjectTables = new ArrayList<>();
        List<StatisticsDto> statisticsDtos = new ArrayList<>();
        /*核查中数据*/
        if (dataUnderVerification == 0) {
            /*项目数据*/
            dahcProjectTables = projectTableService.list(Wrappers.<DahcProjectTable>lambdaQuery().eq(DahcProjectTable::getProjectState, 1));
        } else {
            /*项目数据*/
            dahcProjectTables = projectTableService.list();
        }
        List<String> projectIds = dahcProjectTables.stream().map(DahcProjectTable::getId).collect(Collectors.toList());

        /*获取案卷数据*/
        List<DahcRecordProcedureFiles> dahcRecordProcedureFiles = dahcRecordProcedureFilesService.list(Wrappers.<DahcRecordProcedureFiles>lambdaQuery()
                .in(DahcRecordProcedureFiles::getProjectId, projectIds).isNotNull(DahcRecordProcedureFiles::getAccomplishTime));

        List<LocalDate> dates = dahcRecordProcedureFiles.stream().map(DahcRecordProcedureFiles::getAccomplishTime).collect(Collectors.toList());
        //List<LocalDate> localDates = dates.stream().map(date -> Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()).collect(Collectors.toList());
        //Map<String, Long> countByYearMonth = dates.stream()
        //        .collect(Collectors.groupingBy(d -> d.format(DateTimeFormatter.ofPattern("yyyy年M月")), Collectors.counting()));
        /*查询核查人数*/
        List<DahcSysUserProcedure> sysUserProcedures = dahcSysUserProcedureService.list(Wrappers.<DahcSysUserProcedure>lambdaQuery().in(DahcSysUserProcedure::getProjectId, projectIds).groupBy(DahcSysUserProcedure::getUserId));

        for (LocalDate localDate : dates) {
            /*一个月的数据*/
            List<DahcRecordProcedureFiles> procedureFilesTime = dahcRecordProcedureFiles.stream().filter(f -> f.getAccomplishTime().getMonthValue() == localDate.getMonthValue()).collect(Collectors.toList());
            /*一个月的案卷id*/
            List<String> procedureFilesTimeIds = procedureFilesTime.stream().map(DahcRecordProcedureFiles::getFilesId).collect(Collectors.toList());

            /*核查人数数量*/
            for (DahcProjectTable projectTable : dahcProjectTables) {
                /*获取案件表名称*/
                String archiveTypeName = archiveTypeService.getOne(Wrappers.<DahcArchiveType>lambdaQuery().eq(DahcArchiveType::getId, projectTable.getArchiveTypeId())).getTableLevel2En();
                /*案件数量*/
                List<Map<String, Long>> numberOfCasesCount = dahcDataTemplateMapper.checkTheNumberOfCasesCount(projectTable.getId(), archiveTypeName, procedureFilesTimeIds);
                List<DahcFilePhoto> dahcFilePhotos = new ArrayList<>();
                int dahcFilePhotosCount = 0;
                if (numberOfCasesCount.size() > 0) {
                    /*案件id集合*/
                    List<Long> numberOfCasesIds = numberOfCasesCount.stream().map(m -> m.get("id")).collect(Collectors.toList());
                    /*图像数量*/
                    dahcFilePhotos = dahcFilePhotoService.list(Wrappers.<DahcFilePhoto>lambdaQuery().in(DahcFilePhoto::getPid, numberOfCasesIds).eq(DahcFilePhoto::getFileDelete, 0));
                    dahcFilePhotosCount = dahcFilePhotos.stream().filter(o -> o.getProjectId().equals(projectTable.getId())).collect(Collectors.toList()).size();
                }

                int sysUserProceduresCount = sysUserProcedures.stream().filter(o -> o.getProjectId().equals(projectTable.getId())).collect(Collectors.toList()).size();
                List<DahcRecordProcedureFiles> recordProcedureFiles = procedureFilesTime.stream().filter(o -> o.getProjectId().equals(projectTable.getId())).collect(Collectors.toList());
                statisticsDtos.add(new StatisticsDto(projectTable.getProjectName(),
                        projectTable.getProjectState(),
                        sysUserProceduresCount,
                        recordProcedureFiles.size(), numberOfCasesCount.size(), dahcFilePhotosCount, String.valueOf(localDate.getYear()), String.valueOf(localDate.getMonthValue())));
            }
        }
        /*去重*/
        statisticsDtos = statisticsDtos.stream().distinct().collect(Collectors.toList());
        /*排序*/
        statisticsDtos = statisticsDtos.stream().sorted(Comparator.comparing(StatisticsDto::getYear)).collect(Collectors.toList());
        return statisticsDtos;
    }


    @Override
    public List<StatisticsDto> checkTheStatisticsOfTheResultsOfTheProject(Integer dataUnderVerification) {

        List<DahcProjectTable> dahcProjectTables = new ArrayList<>();
        List<StatisticsDto> statisticsDtos = new ArrayList<>();
        /*核查中数据*/
        if (dataUnderVerification == 0) {
            /*项目数据*/
            dahcProjectTables = projectTableService.list(Wrappers.<DahcProjectTable>lambdaQuery().eq(DahcProjectTable::getProjectState, 1));
        } else {
            /*项目数据*/
            dahcProjectTables = projectTableService.list();
        }
        List<String> projectIds = dahcProjectTables.stream().map(DahcProjectTable::getId).collect(Collectors.toList());
        /*查询核查人数*/
        //List<DahcSysUserProcedure> sysUserProcedures = dahcSysUserProcedureService.list(Wrappers.<DahcSysUserProcedure>lambdaQuery().in(DahcSysUserProcedure::getProjectId, projectIds).groupBy(DahcSysUserProcedure::getUserId));
        /*工序数据*/
        List<DahcProjectProcedure> projectProcedureList = projectProcedureService.list(Wrappers.<DahcProjectProcedure>lambdaQuery().in(DahcProjectProcedure::getProjectId, projectIds).orderByAsc(DahcProjectProcedure::getSort));
        /*获取案卷数据*/
        List<DahcRecordProcedureFiles> dahcRecordProcedureFiles = dahcRecordProcedureFilesService.list(Wrappers.<DahcRecordProcedureFiles>lambdaQuery().in(DahcRecordProcedureFiles::getProjectId, projectIds));
        /*实体*/
        List<String> entityList = new ArrayList<>();
        entityList.add("/physicalFile");
        /*电子*/
        List<String> electronList = new ArrayList<>();
        electronList.add("/electronicTemplateFirst");
        electronList.add("/electronicTemplateSecond");
        electronList.add("/electronicTemplateThird");
        /*电子对照实体*/
        List<String> electronicControlEntityList = new ArrayList<>();
        electronicControlEntityList.add("/electReferPhyTemplateFirst");
        electronicControlEntityList.add("/electReferPhyTemplateSecond");
        electronicControlEntityList.add("/electReferPhyTemplateThird");
        /*核查结果数据*/
        List<QueryDahcRecordProcedureFilesDto> entityFiles = dahcRecordProcedureFilesMapper.categorizeDataBasedOnPages(entityList);
        List<QueryDahcRecordProcedureFilesDto> electronFiles = dahcRecordProcedureFilesMapper.categorizeDataBasedOnPages(electronList);
        List<QueryDahcRecordProcedureFilesDto> electronicControlFiles = dahcRecordProcedureFilesMapper.categorizeDataBasedOnPages(electronicControlEntityList);
        /*查询核查结果数据*/
        for (DahcProjectTable dahcProjectTable : dahcProjectTables) {
            /*项目第一道工序ID*/
            int dahcRecordProcedureFilesCount = 0;
            List<DahcProjectProcedure> projectProcedureList1= projectProcedureList.stream().filter(o -> o.getProjectId().equals(dahcProjectTable.getId())).collect(Collectors.toList());
            if (projectProcedureList1.size() > 0) {
                DahcProjectProcedure projectProcedure = projectProcedureList1.get(0);
                dahcRecordProcedureFilesCount = dahcRecordProcedureFiles.stream().filter(o -> o.getProjectId().equals(dahcProjectTable.getId())).filter(o -> o.getProcedureId().equals(projectProcedure.getId())).collect(Collectors.toList()).size();
            }
            /*当前项目工序数据*/
            List<DahcProjectProcedure> projectProcedures = projectProcedureList.stream().filter(p -> p.getProjectId().equals(dahcProjectTable.getId())).collect(Collectors.toList());
            for (DahcProjectProcedure projectProcedure : projectProcedures) {
                /*实体*/
                List<QueryDahcRecordProcedureFilesDto> entityFilesDtos = entityFiles.stream().filter(f -> f.getProcedureId().equals(projectProcedure.getId())).collect(Collectors.toList());
                List<QueryDahcRecordProcedureFilesDto> electronFilesDtos = electronFiles.stream().filter(f -> f.getProcedureId().equals(projectProcedure.getId())).collect(Collectors.toList());
                List<QueryDahcRecordProcedureFilesDto> electronicControlFilesDtos = electronicControlFiles.stream().filter(f -> f.getProcedureId().equals(projectProcedure.getId())).collect(Collectors.toList());
                /*合格*/
                statisticsDtos.add(new StatisticsDto(dahcProjectTable.getProjectName(), dahcProjectTable.getProjectState(), getTheNumberOfDays(dahcProjectTable), dahcRecordProcedureFilesCount,projectProcedure.getProcedureName(),
                        getTheQualifiedQuantity(entityFilesDtos, "qualified"), getTheNonConformingQuantity(entityFilesDtos,"unqualified","change"), getTheNonConformingQuantity(entityFilesDtos,"unqualified","impeach"),
                        getTheQualifiedQuantity(electronFilesDtos, "qualified"), getTheNonConformingQuantity(electronFilesDtos,"unqualified","change"), getTheNonConformingQuantity(electronFilesDtos,"unqualified","impeach"),
                        getTheQualifiedQuantity(electronicControlFilesDtos, "qualified"), getTheNonConformingQuantity(electronicControlFilesDtos,"unqualified","change"), getTheNonConformingQuantity(electronicControlFilesDtos,"unqualified","impeach")
                ));

            }
        }
        return statisticsDtos;
    }

    /*获取合格数量*/
    private Integer getTheQualifiedQuantity(List<QueryDahcRecordProcedureFilesDto> filesDtos, String checkResultState) {
        Integer i = 0;

        if (filesDtos.size() > 0) {
            List<QueryDahcRecordProcedureFilesDto> collect = filesDtos.stream().filter(f -> f.getCheckResultState() != null && f.getCheckResultState().equals(checkResultState)).collect(Collectors.toList());
            i = collect.size();
        }
        return i;
    }

    /*获取不合格数量*/
    private Integer getTheNonConformingQuantity(List<QueryDahcRecordProcedureFilesDto> filesDtos, String checkResultState,String isQuestion) {
        Integer i = 0;
        if (filesDtos.size() > 0) {
            List<QueryDahcRecordProcedureFilesDto> collect = filesDtos.stream().filter(f -> f.getCheckResultState() != null &&  f.getCheckResultState().equals(checkResultState))
                    .filter( f-> f.getIsQuestion() != null && f.getIsQuestion().equals(isQuestion)).collect(Collectors.toList());
            i = collect.size();
        }
        return i;
    }

    /*获取天数*/
    private Integer getTheNumberOfDays(DahcProjectTable projectTable) {

        long s = 0l;
        /*核查中*/
        if (projectTable.getProjectState().equals("1")) {
            s = new Date().getTime() - projectTable.getStartPracticalTime().getTime();  //endeTime和startTime是两个要计算的时间
        } else if (projectTable.getProjectState().equals("2")) {
            /*完成核查*/
            s = projectTable.getEndPracticalTime().getTime() - projectTable.getStartPracticalTime().getTime();
        }
        TimeUnit time = TimeUnit.DAYS;
        Integer days = Math.toIntExact(time.convert(s, TimeUnit.MILLISECONDS));
        return days;
    }
}
