package com.fudian.dahc.service.recordTrueing.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.CommonStatus;
import com.fudian.dahc.mapper.recordTrueing.DahcRecordProcedureFilesMapper;
import com.fudian.dahc.pojo.dto.QueryElectronicArchivesDossierLevelDto;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;
import com.fudian.dahc.pojo.dto.TaskManagementProcessVo;
import com.fudian.dahc.pojo.dto.TaskRecordProcedureFilesDto;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.business.DahcDataTemplate;
import com.fudian.dahc.pojo.entity.log.DahcCheckRecordLog;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedure;
import com.fudian.dahc.pojo.entity.project.DahcProjectTable;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import com.fudian.dahc.pojo.entity.sys.DahcSysUserProcedure;
import com.fudian.dahc.service.business.ArchiveTypeService;
import com.fudian.dahc.service.business.DataTemplateService;
import com.fudian.dahc.service.log.IDahcCheckRecordLogService;
import com.fudian.dahc.service.project.ProjectProcedureService;
import com.fudian.dahc.service.project.ProjectTableService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordProcedureFilesService;
import com.fudian.dahc.service.sys.IDahcSysDictDataService;
import com.fudian.dahc.util.common.AssertUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fudian
 * @date 2023-02-24
 */
@Service
public class DahcRecordProcedureFilesServiceImpl extends ServiceImpl<DahcRecordProcedureFilesMapper, DahcRecordProcedureFiles> implements IDahcRecordProcedureFilesService {
    @Autowired
    private DahcRecordProcedureFilesMapper dahcRecordProcedureFilesMapper;

    @Autowired
    private ArchiveTypeService archiveTypeService;
    @Autowired
    private DataTemplateService dataTemplateService;
    @Autowired
    private ProjectProcedureService projectProcedureService;

    @Autowired
    private ProjectTableService projectTableService;


    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private IDahcSysDictDataService dahcSysDictDataService;
    @Autowired
    private IDahcCheckRecordLogService dahcCheckRecordLogService;

    @Override
    public List<DahcRecordProcedureFiles> conditionalQueries(DahcRecordProcedureFiles dahcRecordProcedureFiles) {
        List<DahcRecordProcedureFiles> dahcRecordProcedureFiles1 = dahcRecordProcedureFilesMapper.querySuspectData(dahcRecordProcedureFiles);

        return dahcRecordProcedureFiles1;
    }

    @Override
    public CommonResult associateTheCurrentOperation(Long userId, String procedureId) {
        /*查询当前用户是否关联当前工序*/
        Integer integer = dahcRecordProcedureFilesMapper.associateTheCurrentOperation(userId, procedureId);
        if (integer <= 0) {
            return CommonResult.error("没有关联当前工序");
        }
        return CommonResult.success();
    }

    @Override
    public CommonResult modifyTheVerifier(DahcRecordProcedureFiles dahcRecordProcedureFiles) {
        /*查询当前用户是否关联当前工序*/
        Integer integer = dahcRecordProcedureFilesMapper.associateTheCurrentOperation(dahcRecordProcedureFiles.getInspectId(), dahcRecordProcedureFiles.getProcedureId());
        if (integer <= 0) {
            return CommonResult.error("没有关联当前工序");
        }

        boolean update = this.updateById(dahcRecordProcedureFiles);
        if (update) {
            return CommonResult.success();
        } else {
            return CommonResult.error("修改失败");
        }
    }


    @Override
    public Integer theNumberOfFilesNotVerified(String procedureId) {
        List<DahcRecordProcedureFiles> list = this.list(Wrappers.<DahcRecordProcedureFiles>lambdaQuery().eq(DahcRecordProcedureFiles::getProcedureId, procedureId));
        return list.size();
    }

    @Override
    public Integer numberOfFilesVerified(String procedureId) {
        List<DahcRecordProcedureFiles> list = this.list(Wrappers.<DahcRecordProcedureFiles>lambdaQuery()
                .eq(DahcRecordProcedureFiles::getProcedureId, procedureId)
                .eq(DahcRecordProcedureFiles::getIsProcedureInspect, "1"));
        return list.size();
    }

    /**
     * 查询档案未核查数量
     *
     * @return com.fudian.common.pojo.CommonGridResult
     * @author MCY
     * @date 2023/3/27 11:53
     */
    @Override
    public List<DahcRecordProcedureFiles> theNumberOfUncheckedFilesInTheQueryFile(DahcRecordProcedureFiles files) {
        List<DahcRecordProcedureFiles> dahcRecordProcedureFiles = new ArrayList<>();
        if (files.getIsProcedureInspect().equals("0")) {
            /*查询实体未核查数量*/
            dahcRecordProcedureFiles = dahcRecordProcedureFilesMapper.selectDahcRecordProcedureFilesList(files);
        }

        Boolean rolePermissions = dahcSysDictDataService.getRolePermissions(SecurityUtils.getUserId());
        if (files.getIsProcedureInspect().equals("2")) {
            //if (!SecurityUtils.getUsername().equals("admin") && !rolePermissions) {
                files.setInspectId(SecurityUtils.getUserId());
            //}
            /*查询电子未核查数量*/
            dahcRecordProcedureFiles = dahcRecordProcedureFilesMapper.selectDahcRecordProcedureFilesList(files);
        }


        return dahcRecordProcedureFiles;
    }


    @Override
    public List<DahcDataTemplate> theTaskManagementPageQueriesTheSuspectData(TaskRecordProcedureFilesDto dahcRecordProcedureFiles, List<String> recordProcedureFiles) {

        //List<Long> filsId = recordProcedureFiles.stream().map(DahcRecordProcedureFiles::getFilesId).collect(Collectors.toList()).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        /*根据项目名称获取档案类型，获取档案数据表名称*/
        String profileDataTableName = archiveTypeService.getsTheFileTableNameBasedOnTheFileType(dahcRecordProcedureFiles.getProjectId());
        List<DahcDataTemplate> dahcDataTemplates = dataTemplateService.queryBasedOnPrimaryKeyIDData(profileDataTableName, recordProcedureFiles,
                dahcRecordProcedureFiles.getPageSize(), dahcRecordProcedureFiles.getPageNum(),dahcRecordProcedureFiles.getAttrOrder(),dahcRecordProcedureFiles.getCaseNum());
        return dahcDataTemplates;
    }

    /**
     * 实体档案：领取档案数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/28 15:53
     */
    @Override
    @Transactional
    public CommonResult drawEntityFile(DahcRecordProcedureFiles procedureFiles) {

        /*查询当前用户是否关联当前工序*/
        Integer integer = dahcRecordProcedureFilesMapper.associateTheCurrentOperation(SecurityUtils.getUserId(), procedureFiles.getProcedureId());
        if (integer <= 0) {
            return CommonResult.error("没有关联当前工序");
        }

        /*查询源表，判断是否存在数据， 未完成*/
        String i = null;
        try {
            i = dahcRecordProcedureFilesMapper.queryFileNumber(procedureFiles.getFileTableName(), procedureFiles.getFileTableNameAttr(), procedureFiles.getFilesName(), procedureFiles.getProjectId());
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error("存在两份及以上相同档号的数据或档号数据不存在，请联系管理员");
        }
        if (!StringUtils.isNotEmpty(i)) {
            return CommonResult.error("档号数据不存在");
        }
        /*查询档案是否已核查*/
        DahcRecordProcedureFiles recordProcedureFiles = this.getOne(Wrappers.<DahcRecordProcedureFiles>lambdaQuery()
                .eq(DahcRecordProcedureFiles::getProcedureId, procedureFiles.getProcedureId())
                .eq(DahcRecordProcedureFiles::getFilesName, procedureFiles.getFilesName())
        );
        if (recordProcedureFiles != null) {

            if (recordProcedureFiles.getInspectId() == null) {
                /*数据没有领取人*/
                boolean update = this.update(Wrappers.<DahcRecordProcedureFiles>lambdaUpdate().
                        eq(DahcRecordProcedureFiles::getProjectId, procedureFiles.getProjectId())
                        .eq(DahcRecordProcedureFiles::getProcedureId, procedureFiles.getProcedureId())
                        .eq(DahcRecordProcedureFiles::getFilesName, procedureFiles.getFilesName())
                        .set(DahcRecordProcedureFiles::getIsProcedureInspect, "2")
                        .set(DahcRecordProcedureFiles::getGetTime, new Date())
                        .set(DahcRecordProcedureFiles::getFilesId, i)
                        .set(DahcRecordProcedureFiles::getInspectId, SecurityUtils.getUserId()));
                Map<String, Object> map = new HashMap<>();
                map.put("filesId", i);
                map.put("recordProcedureFilesId", this.getOne(Wrappers.<DahcRecordProcedureFiles>lambdaQuery()
                        .eq(DahcRecordProcedureFiles::getProjectId, procedureFiles.getProjectId())
                        .eq(DahcRecordProcedureFiles::getProcedureId, procedureFiles.getProcedureId())
                        .eq(DahcRecordProcedureFiles::getFilesName, procedureFiles.getFilesName())
                ).getId());
                if (update) {
                    DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(procedureFiles.getProjectId(), procedureFiles.getProcedureId(),procedureFiles.isCheckStatus());
                    dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                            procedureFiles.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                            dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), null,
                            procedureFiles.getFilesName(),null, null,recordProcedureFiles.getFilesId(), 0,null, JSON.toJSONString(procedureFiles),null,dahcCheckRecordLog.getCheckStatus()));
                    return CommonResult.success("领取成功", map);
                } else {
                    return CommonResult.error("领取失败");
                }
            } else if (recordProcedureFiles.getInspectId().equals(SecurityUtils.getUserId())) {
                if (recordProcedureFiles.getIsProcedureInspect().equals("1")) {
                    return CommonResult.error("案卷已提交");
                } else {
                    return CommonResult.error("不能重复领取案卷");
                }
            } else {
                return CommonResult.error("案卷已被其他人员领取");
            }


        } else {
            return CommonResult.error("当前工序不存在该档号数据");
        }

    }


    /**
     * 根据档案id集合领取档案
     *
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/2 17:41
     */
    @Override
    public CommonResult collectYourFileByIds(DahcRecordProcedureFiles procedureFiles) {
        String[] fileIds = procedureFiles.getFileIds() ;

        /*   根据ID判断是否关联当前工序*/
        Integer integer = dahcRecordProcedureFilesMapper.theIDDeterminesTheCurrentProcess(SecurityUtils.getUserId(), fileIds);
        if (integer <= 0) {
            return CommonResult.error("没有关联当前工序");
        }

        boolean update = this.update(Wrappers.<DahcRecordProcedureFiles>lambdaUpdate()
                .in(DahcRecordProcedureFiles::getId, Arrays.asList(fileIds))
                .eq(DahcRecordProcedureFiles::getIsProcedureInspect, "0")
                .set(DahcRecordProcedureFiles::getIsProcedureInspect, "2")
                .set(DahcRecordProcedureFiles::getInspectId, SecurityUtils.getUserId())
                .set(DahcRecordProcedureFiles::getGetTime, new Date())
        );

        if (update) {
            DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(procedureFiles.getProjectId(), procedureFiles.getProcedureId(),procedureFiles.isCheckStatus());
            dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                    procedureFiles.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                    dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), null,
                    procedureFiles.getFilesName(),null, null,procedureFiles.getFilesId(), 0,null, JSON.toJSONString(procedureFiles),null,dahcCheckRecordLog.getCheckStatus()));
            return CommonResult.success("领取成功");
        } else {
            return CommonResult.error("领取失败");
        }

    }


    /**
     * 查询电子档案是否有核查到一半的数据，并根据核查时间回显最近一次核查到一半的数据
     *
     * @return java.util.List<com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles>
     * @author MCY
     * @date 2023/3/3 10:54
     */
    @Override
    public DahcRecordProcedureFiles queryTheResultsOfTheVerificationWereNotCompleted(RecordTrueingDto dto) {
        /*根据工序id、用户id查询当前工序是否存在的数据*/
        dto.setInspectId(SecurityUtils.getUserId());
        List<DahcRecordProcedureFiles> dahcRecordProcedureFiles = dahcRecordProcedureFilesMapper.queryTheResultsOfTheVerificationWereNotCompleted(dto);
        if (dahcRecordProcedureFiles.size() > 0) {
            return dahcRecordProcedureFiles.get(0);
        } else {
            return null;
        }
    }

    /**
     * 批量插入
     *
     * @param dahcRecordProcedureFiles
     */
    @Override
    public boolean bulkInsert(List<DahcRecordProcedureFiles> dahcRecordProcedureFiles) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        DahcRecordProcedureFilesMapper mapper = sqlSession.getMapper(DahcRecordProcedureFilesMapper.class);
        dahcRecordProcedureFiles.forEach(mapper::insert);
        sqlSession.commit();
        sqlSession.clearCache();
        return true;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Object> startVerificationButton(String projectId) {
        //传入项目id
        DahcProjectTable byId = projectTableService.findById(Long.valueOf(projectId));
        AssertUtil.isTrueServiceInvoke(byId != null, CommonStatus.ERROR, "未查询到该项目");
        String archiveTypeId = byId.getArchiveTypeId();
        DahcArchiveType byId1 = archiveTypeService.findById(Long.valueOf(archiveTypeId));
        AssertUtil.isTrueServiceInvoke(byId1 != null, CommonStatus.ERROR, "查询出错,根据id:" + archiveTypeId + "未查询到此类型");
        String archiveLevelName = byId1.getTableLevel1En();
        //根据档案类型id查询元数据中主键名
        String dh = archiveTypeService.queryProfileDefaultFieldBindingAttr(archiveTypeId, byId1.getTableLevel1En(), "档号");
        AssertUtil.isTrueServiceInvoke(dh != null, CommonStatus.ERROR, "未能找到档号");
        String attr = "attr" + dh;
        //根据项目查询第一个工序的id
        DahcProjectProcedure dahcProjectProcedure = projectProcedureService.queryTheFirstOperation(projectId);
        AssertUtil.isTrueServiceInvoke(dahcProjectProcedure != null, CommonStatus.ERROR, "未查询到工序");
        String procedureId = dahcProjectProcedure.getId();
        //查询该项目中所有任务未开始状态的数据
        List<Map<String, String>> data = dataTemplateService.queryDataThatHasNotBeenChecked(archiveLevelName, attr, projectId);
        if (data.size() > 0) {
            List<DahcRecordProcedureFiles> filesList = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            for (Map<String, String> a : data) {
                String s = String.valueOf(a.get("id"));
                ids.add(s);
                Object filesName = a.get(attr);
                //isProcedureInspect 为0 表示开启核查任务 还未被领取
                filesList.add(new DahcRecordProcedureFiles(
                        s, String.valueOf(filesName), procedureId, projectId, "0",new Date()));
            }
            //把数据加入到任务表中
            boolean b = bulkInsert(filesList);
            AssertUtil.isTrueServiceInvoke(b, CommonStatus.ERROR, "插入失败");
            //修改数据的状态 1 未开始核查 -> 2 开始核查
            dataTemplateService.modifyStatusValueInList(archiveLevelName, 2, 1, ids);
        }
        return CommonResult.success();
    }

    /**
     * 根据档案类型id和案卷id查询案卷数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/7 16:59
     */
    @Override
    public List<Map<String, String>> queryCaseLevelArchivesDossierLevelTemplateTwo(QueryElectronicArchivesDossierLevelDto dto) {
        /*根据档案类型获取 案件表名*/
        DahcArchiveType archiveType = archiveTypeService.findById(Long.valueOf(dto.getArchiveId()));
        String attr = archiveTypeService.queryProfileDefaultFieldBindingAttr(dto.getArchiveId(), archiveType.getTableLevel2En(), "顺序号/件号");
        /*根据案件表名和案卷id获取案件数据*/
        List<Map<String, String>> maps = dahcRecordProcedureFilesMapper.queryCaseDataBasedOnFileID(
                archiveType.getTableLevel1En(),
                dto.getFilesId(),
                archiveType.getTableLevel2En(),
                dto.getAttrOrder(),
                dto.getCaseNum(),
                "attr" + attr,
                dto.getStartTheVerification()
        );
        return maps;
    }

    @Override
    public List<Map<String, String>> queryArchivesDossierLevelTemplateTwoBackup(QueryElectronicArchivesDossierLevelDto dto) {
        /*根据档案类型获取 案件表名*/
        DahcArchiveType archiveType = archiveTypeService.findById(Long.valueOf(dto.getArchiveId()));
        String attr = archiveTypeService.queryProfileDefaultFieldBindingAttr(dto.getArchiveId(), archiveType.getTableLevel2En(), "顺序号/件号");
        /*根据案件表名和案卷id获取案件数据*/
        List<Map<String, String>> maps = dahcRecordProcedureFilesMapper.queryCaseDataBasedOnFileID(
                archiveType.getTableLevel1En() + "_backup",
                dto.getFilesId(),
                archiveType.getTableLevel2En() + "_backup",
                dto.getAttrOrder(),
                dto.getCaseNum(),
                "attr" + attr,
                dto.getStartTheVerification()
        );
        return maps;
    }

    /**
     * 任务管理完成核查
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/16 18:12
     */
    @Override
    @Transactional
    public CommonResult taskManagementCompletesTheVerification(String[] strings) {
        try {
            List<String> filesIds = Arrays.asList(strings);
            /*根据id查询状态是否有未核查的数据*/
            for (String id : filesIds) {
                List<DahcRecordProcedureFiles> list = this.list(Wrappers.<DahcRecordProcedureFiles>lambdaQuery()
                        .ne(DahcRecordProcedureFiles::getIsProcedureInspect, "1")
                        .eq(DahcRecordProcedureFiles::getFilesId, id));
                if (list.size() > 0) {
                    return CommonResult.error("档号为" + list.get(0).getFilesName() + "的档案数据核查工序未完成");
                }

                /*判断工序数量是否完整*/
                /*核查结果数量*/
                //DahcRecordProcedureFiles one = this.getOne(Wrappers.<DahcRecordProcedureFiles>lambdaQuery().eq(DahcRecordProcedureFiles::getId, id));
                List<DahcRecordProcedureFiles> recordProcedureFiles = this.list(Wrappers.<DahcRecordProcedureFiles>lambdaQuery().eq(DahcRecordProcedureFiles::getFilesId, id));
                /*工序数量*/
                List<DahcProjectProcedure> projectProcedures = projectProcedureService.list(Wrappers.<DahcProjectProcedure>lambdaQuery().eq(DahcProjectProcedure::getProjectId, recordProcedureFiles.get(0).getProjectId()));
                if (recordProcedureFiles.size() < projectProcedures.size()) {
                    return CommonResult.error("档号为" + recordProcedureFiles.get(0).getFilesName() + "的档案数据有未核查工序");
                }
            }
            boolean update = this.update(Wrappers.<DahcRecordProcedureFiles>lambdaUpdate().in(DahcRecordProcedureFiles::getFilesId, filesIds).set(DahcRecordProcedureFiles::getIsAccomplishInspect, 0));
            if (update) {
                return CommonResult.success("修改成功");
            } else {
                return CommonResult.error("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    /**
     * 首页核查结果查询
     *
     * @return com.fudian.common.core.page.TableDataInfo
     * @author MCY
     * @date 2023/4/10 10:20
     */
    @Override
    public Integer homepageCheckResultQuery(DahcRecordProcedureFiles files) {
        /*        *//*查询当前用户是否关联工序*//*
        List<DahcSysUserProcedure> sysUserProcedures = dahcRecordProcedureFilesMapper.querySysUserProcedure(files.getProjectId(), SecurityUtils.getUserId());
        List<DahcRecordProcedureFiles> recordProcedureFiles = new ArrayList<>();
        if (sysUserProcedures.size() > 0) {
            files.setInspectId(SecurityUtils.getUserId());
            //recordProcedureFiles = dahcRecordProcedureFilesMapper.selectDahcRecordProcedureFilesList(files);
            recordProcedureFiles = dahcRecordProcedureFilesMapper.selectList(Wrappers.<DahcRecordProcedureFiles>lambdaQuery()
                    .eq(DahcRecordProcedureFiles::getProjectId, files.getProjectId())
                    .eq(DahcRecordProcedureFiles::getInspectId, SecurityUtils.getUserId())
                    .eq(DahcRecordProcedureFiles::getIsProcedureInspect, files.getIsProcedureInspect())
                    .in(DahcRecordProcedureFiles::getProcedureId, sysUserProcedures.stream().map(DahcSysUserProcedure::getProcedureId).collect(Collectors.toList())));
        } else {*/
        List<DahcRecordProcedureFiles> recordProcedureFiles = dahcRecordProcedureFilesMapper.selectDahcRecordProcedureFilesList(files);
        //}
        return recordProcedureFiles.size();
    }

    @Override
    public Integer homePageToBePickedUpTaskQuery(DahcRecordProcedureFiles files) {
        /*        *//*查询当前用户是否关联工序*//*
        List<DahcSysUserProcedure> sysUserProcedures = dahcRecordProcedureFilesMapper.querySysUserProcedure(files.getProjectId(), SecurityUtils.getUserId());
        Integer i = 0;
        if (sysUserProcedures.size() > 0) {
            i = dahcRecordProcedureFilesMapper.homePageToBePickedUpTaskQuery(files.getProjectId(), SecurityUtils.getUserId()).size();
        } else {*/
        Integer i = dahcRecordProcedureFilesMapper.selectDahcRecordProcedureFilesList(files).size();
        //}
        return i;
    }

    @Override
    public Integer theTaskHasBeenClaimed(DahcRecordProcedureFiles files) {
        /*        *//*查询当前用户是否关联工序*//*
        List<DahcSysUserProcedure> sysUserProcedures = dahcRecordProcedureFilesMapper.querySysUserProcedure(files.getProjectId(), SecurityUtils.getUserId());
        Integer i = 0;
        if (sysUserProcedures.size() > 0) {
            i = dahcRecordProcedureFilesMapper.selectList(Wrappers.<DahcRecordProcedureFiles>lambdaQuery()
                    .eq(DahcRecordProcedureFiles::getProjectId, files.getProjectId())
                    .eq(DahcRecordProcedureFiles::getInspectId, SecurityUtils.getUserId())
                    .ne(DahcRecordProcedureFiles::getIsProcedureInspect, 0)).size();
        } else {*/
        Integer i = dahcRecordProcedureFilesMapper.selectList(Wrappers.<DahcRecordProcedureFiles>lambdaQuery()
                .eq(DahcRecordProcedureFiles::getProjectId, files.getProjectId())
                .ne(DahcRecordProcedureFiles::getIsProcedureInspect, 0)).size();
        //}
        return i;
    }

    @Override
    public DahcRecordProcedureFiles checkWhetherTheVerificationIsIncomplete(RecordTrueingDto dto) {
        DahcRecordProcedureFiles one = this.getOne(Wrappers.<DahcRecordProcedureFiles>lambdaQuery()
                .eq(DahcRecordProcedureFiles::getProcedureId, dto.getProcedureId())
                .eq(DahcRecordProcedureFiles::getFilesId, dto.getFileNumberId())
                .eq(DahcRecordProcedureFiles::getIsReturnSave, 1)
                .eq(DahcRecordProcedureFiles::getInspectId, SecurityUtils.getUserId()));
        return one;
    }


    @Override
    public List<Map<String, Object>> queryBasedOnTheIDOfTheVerificationResult(QueryElectronicArchivesDossierLevelDto dto) {
        return dahcRecordProcedureFilesMapper.queryBasedOnTheIDOfTheVerificationResult(dto.getArchiveLevelName(), dto.getProcedureId(), dto.getAttrOrder(), dto.getCaseNum());
    }

    @Override
    public List<TaskManagementProcessVo> taskManagementQueryOperations(TaskManagementProcessVo taskManagementProcessVo) {

        return dahcRecordProcedureFilesMapper.taskManagementQueryOperations(taskManagementProcessVo.getFilesId());
    }
}
