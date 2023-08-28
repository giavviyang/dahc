package com.fudian.dahc.service.recordTrueing.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.uuid.IdUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.mapper.recordTrueing.DahcRecordProcedureFilesMapper;
import com.fudian.dahc.mapper.recordTrueing.DahcRecordTrueingResultMapper;
import com.fudian.dahc.pojo.dto.QueryElectronicArchivesDossierLevelDto;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;
import com.fudian.dahc.pojo.dto.UpdateImpeachResultDto;
import com.fudian.dahc.pojo.entity.log.DahcCheckRecordLog;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedure;
import com.fudian.dahc.pojo.entity.recordTrueing.*;
import com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement;
import com.fudian.dahc.service.log.IDahcCheckRecordLogService;
import com.fudian.dahc.service.project.ProjectProcedureService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordProcedureFilesService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordTrueingResultService;
import com.fudian.dahc.service.sys.IDahcSysDictDataService;
import com.fudian.dahc.service.trueing.IDahcHcxTrueingManagementService;
import com.fudian.dahc.service.trueing.IDahcHcxTrueingStandardService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fudian
 * @date 2023-02-21
 */
@Service
public class DahcRecordTrueingResultServiceImpl extends ServiceImpl<DahcRecordTrueingResultMapper, DahcRecordTrueingResult> implements IDahcRecordTrueingResultService {
    @Autowired
    private DahcRecordTrueingResultMapper dahcRecordTrueingResultMapper;

    @Autowired
    private IDahcHcxTrueingManagementService dahcHcxTrueingManagementService;
    @Autowired
    private IDahcHcxTrueingStandardService dahcHcxTrueingStandardService;
    @Autowired
    private ProjectProcedureService projectProcedureService;
    @Autowired
    private IDahcRecordProcedureFilesService dahcRecordProcedureFilesService;

    @Autowired
    private DahcRecordProcedureFilesMapper dahcRecordProcedureFilesMapper;
    @Autowired
    private IDahcRecordTrueingResultService dahcRecordTrueingResultService;
    @Autowired
    private IDahcSysDictDataService dahcSysDictDataService;
    @Autowired
    private IDahcCheckRecordLogService dahcCheckRecordLogService;

    /**
     * 查询核查结果
     *
     * @return java.util.List<com.fudian.dahc.pojo.dto.RecordTrueingDto>
     * @author MCY
     * @date 2023/2/23 16:38
     */
    @Override
    public List<RecordTrueingDto> queryProcedureInspect(RecordTrueingDto dto) {
        /*根据工序id和核查项人id判断是否有核查结果*/
        List<RecordTrueingDto> recordTrueingDtos = new ArrayList<>();
        LambdaQueryWrapper<DahcRecordProcedureFiles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DahcRecordProcedureFiles::getProcedureId, dto.getProcedureId())
                .orderByAsc(DahcRecordProcedureFiles::getGetTime);
        Boolean rolePermissions = dahcSysDictDataService.getRolePermissions(SecurityUtils.getUserId());
        //if (!SecurityUtils.getUsername().equals("admin") && !rolePermissions) {
        queryWrapper.eq(DahcRecordProcedureFiles::getInspectId, SecurityUtils.getUserId());
        //}
        if (StringUtils.isNotEmpty(dto.getFilesId())) {
            queryWrapper.eq(DahcRecordProcedureFiles::getFilesId, dto.getFilesId());
        }
        if (!StringUtils.isNotEmpty(dto.getIsProcedureInspect())) {
            queryWrapper.eq(DahcRecordProcedureFiles::getIsProcedureInspect, "2");
        }

        List<DahcRecordProcedureFiles> list = dahcRecordProcedureFilesMapper.selectList(queryWrapper);
        /*单纯查询核查项*/
        recordTrueingDtos = dahcRecordTrueingResultMapper.queryInspectTableList(dto);

        if (list.size() > 0) {
            /*回显核查结果*/
            /*根据核查结果id获取核查结果*/
            for (RecordTrueingDto recordTrueingDto : recordTrueingDtos) {
                recordTrueingDto.setRollId(list.get(0).getFilesName());
                recordTrueingDto.setRecordProcedureFilesId(list.get(0).getId());
                recordTrueingDto.setFilesId(list.get(0).getFilesId());
                /*拼接数据*/
                recordTrueingDto = jointRecordTrueingDto(recordTrueingDto, list.get(0));
            }
        } else {
            /*拼接核查标准*/
            for (RecordTrueingDto recordTrueingDto : recordTrueingDtos) {
                recordTrueingDto.setExamineStasString(dahcHcxTrueingStandardService.queryById(recordTrueingDto.getTrueingId()));
            }
        }
        return recordTrueingDtos;
    }


    /*给返回数据赋值*/
    private RecordTrueingDto assignAValueToTheReturnedData(RecordTrueingDto recordTrueingDto, List<DahcRecordTrueingResult> recordTrueingResults) {
        recordTrueingDto.setRemark(recordTrueingResults.get(0).getRemark());
        recordTrueingDto.setRollId(recordTrueingResults.get(0).getRollId());
        recordTrueingDto.setFilesId(recordTrueingResults.get(0).getFileId());
        recordTrueingDto.setRecordProcedureFilesId(recordTrueingResults.get(0).getRecordProcedureFilesId());
        return recordTrueingDto;
    }

    /*拼接件和页数据*/
    private RecordTrueingDto stitchingAndPageData(List<DahcRecordTrueingResult> recordTrueingResults, RecordTrueingDto recordTrueingDto) {
        /*拼接基本数据*/
        /*是否合格*/
        if (recordTrueingResults.size() > 0) {
            recordTrueingDto.setCheckResult(new ArrayList<String>() {{
                add(recordTrueingResults.get(0).getCheckResultState());
            }});
            /*是否存疑*/
            recordTrueingDto.setProcessMode(new ArrayList<String>() {{
                add(recordTrueingResults.get(0).getIsQuestion());
            }});
            /*是否是原件*/
            recordTrueingDto.setChangeContent(new ArrayList<String>() {{
                add(recordTrueingResults.get(0).getIsMasterCopy());
            }});
            /*备注*/
            recordTrueingDto = assignAValueToTheReturnedData(recordTrueingDto, recordTrueingResults);
        }
        return recordTrueingDto;
    }


    /*拼接核查结果数据*/
    private RecordTrueingDto jointRecordTrueingDto(RecordTrueingDto recordTrueingDto, DahcRecordProcedureFiles dahcRecordProcedureFiles) {
        /*拼接基本数据*/
        /*没有核查件和页的数据*/
        List<DahcRecordTrueingResult> recordTrueingResults = dahcRecordTrueingResultMapper.selectList(Wrappers.<DahcRecordTrueingResult>lambdaQuery()
                .eq(DahcRecordTrueingResult::getRecordProcedureFilesId, dahcRecordProcedureFiles.getId()).eq(DahcRecordTrueingResult::getTrueingId, recordTrueingDto.getTrueingId()));
        if (recordTrueingResults.size() > 0) {
            List<Map<String, Long>> pageNumMaps = new ArrayList<>();
            recordTrueingDto = stitchingAndPageData(recordTrueingResults, recordTrueingDto);
            for (DahcRecordTrueingResult recordTrueingResult : recordTrueingResults) {
                Map<String, Long> map = new HashMap<>();
                map.put("arrItem", recordTrueingResult.getNumberOfCases());
                map.put("arrPage", recordTrueingResult.getPageNumFile());
                pageNumMaps.add(map);
                recordTrueingDto.setPageNumS(pageNumMaps);
            }
        }
        /*拼接核查标准*/
        recordTrueingDto.setExamineStasString(dahcHcxTrueingStandardService.queryById(recordTrueingDto.getTrueingId()));
        return recordTrueingDto;
    }

    /*验证档号是否正确*/
    private void verifyThatTheFileNumberIsCorrect(List<RecordTrueingDto> dto) {


        /*查询当前用户是否关联当前工序*/
        Integer integer = dahcRecordProcedureFilesMapper.associateTheCurrentOperation(SecurityUtils.getUserId(), dto.get(0).getProcedureId());
        if (integer <= 0) {
            throw new RuntimeException("没有关联当前工序,没有权限提交或保存");
        }



        /*判断当前工序是否存在改档号数据*/
        DahcRecordProcedureFiles procedureFiles = new DahcRecordProcedureFiles();
        procedureFiles.setProcedureId(dto.get(0).getProcedureId());
        procedureFiles.setFilesName(dto.get(0).getRollId());
        List<DahcRecordProcedureFiles> recordProcedureFiles = dahcRecordProcedureFilesMapper.selectDahcRecordProcedureFilesList(procedureFiles);
        if (recordProcedureFiles.size() > 0) {
            for (DahcRecordProcedureFiles recordProcedureFile : recordProcedureFiles) {
                if (recordProcedureFile.getIsProcedureInspect().equals("1")) {
                    throw new RuntimeException("案卷已完成核查");
                } else if (!recordProcedureFile.getInspectId().equals(SecurityUtils.getUserId())) {
                    throw new RuntimeException("案卷已被其他核查人员领取");
                }
            }
        } else {
            throw new RuntimeException("当前工序不存在该案卷");
        }
    }

    /**
     * 新增核查结果
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/4/13 17:20
     */
    @Transactional
    @Override
    public CommonResult insertProcedureInspect(List<RecordTrueingDto> dto) {

        /*删除之前保存的核查结果*/
        /*根据工序id和卷id删除当前卷保存的关系*/
        try {
            //验证档号是否正确
            if (StringUtils.isNotEmpty(dto.get(0).getRollId())) {
                verifyThatTheFileNumberIsCorrect(dto);
            }
            /*判断当前提交数据是否存在*/
            int i = dahcRecordTrueingResultMapper.deleteDahcRecordTrueingResultByIds(dto);
            ArrayList<DahcRecordTrueingResult> pageNumRecordTrueingResults = new ArrayList<>();
            /*合格数据*/
            ArrayList<DahcRecordTrueingResult> dahcRecordTrueingResults = new ArrayList<>();

            /*是否存疑*/
            boolean impeach = false;
            /*添加新的核查结果*/
            for (RecordTrueingDto recordTrueingDto : dto) {
                /*拆解不合格数据*/
                List<Map<String, Long>> pageNumS = recordTrueingDto.getPageNumS();
                if (pageNumS.size() > 0) {
                    for (Map<String, Long> pageNum : pageNumS) {
                        /*件*/
                        //if (pageNum.size() > 1) {
                        DahcRecordTrueingResult dahcRecordTrueingResult = new DahcRecordTrueingResult();
                        BeanUtils.copyProperties(recordTrueingDto, dahcRecordTrueingResult);
                        Long arrItem = pageNum.get("arrItem");
                        /*页*/
                        Long arrPage = pageNum.get("arrPage");
                        dahcRecordTrueingResult.setFileId(recordTrueingDto.getFilesId());
                        dahcRecordTrueingResult.setPageNumFile(arrPage);
                        dahcRecordTrueingResult.setImpeachPageNumFile(arrPage);
                        dahcRecordTrueingResult.setNumberOfCases(arrItem);
                        dahcRecordTrueingResult.setImpeachNumberOfCases(arrItem);
                        /*处理新增数据*/
                        pageNumRecordTrueingResults.add(disposeInspectData(dahcRecordTrueingResult, recordTrueingDto));

                    }
                } else {
                    /*      添加合格数据*/
                    DahcRecordTrueingResult dahcRecordTrueingResult = new DahcRecordTrueingResult();
                    BeanUtils.copyProperties(recordTrueingDto, dahcRecordTrueingResult);
                    /*     处理新增数据*/
                    dahcRecordTrueingResults.add(disposeInspectData(dahcRecordTrueingResult, recordTrueingDto));
                }


                /*StringUtils.isEmpty(recordTrueingDto.getImpeach()),判断是否是存疑页面提交的数据，如果是存疑则不走下面方法*/
                recordTrueingDto.getProcessMode().removeAll(Collections.singleton(null)); //清空list集合里的All elements are null
                if (recordTrueingDto.getProcessMode().size() > 0) {
                    if (recordTrueingDto.getProcessMode().get(0).equals("impeach") && StringUtils.isEmpty(recordTrueingDto.getImpeach())) {
                        impeach = true;
                    }
                }
            }
            int i2 = 0;
            int i3 = 0;
            Integer submit = null;
            if (pageNumRecordTrueingResults.size() > 0) {
                i2 = dahcRecordTrueingResultMapper.insertDahcRecordTrueingResults(pageNumRecordTrueingResults);
            }
            if (dahcRecordTrueingResults.size() > 0) {
                i3 = dahcRecordTrueingResultMapper.insertDahcRecordTrueingResults(dahcRecordTrueingResults);
            }
            /* 修改案卷状态*/
            if (dto.get(0).getIsInspectAccomplish() == 0 && !impeach) {
                /*提交执行方法*/
                updateAndAddRecordProcedure(dto, 0);
                submit = 0;
            } else if (impeach && dto.get(0).getIsInspectAccomplish() == 0) {
                /*存疑执行方法*/
                processDataImpeach(dto);
                submit = 1;
            } else {
                /* 返回执行方法*/
                processDataOnReturn(dto);
            }
            if (i2 + i3 > 0) {
                DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.get(0).getProjectId(), dto.get(0).getProcedureId(),dto.get(0).isCheckStatus());
                dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                        dto.get(0).getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                        dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), dahcCheckRecordLog.getFileTableName(),
                        dto.get(0).getRollId(), null, dto.get(0).getFilesId(), null, 0,
                        JSON.toJSONString(dto), null, submit,dahcCheckRecordLog.getCheckStatus()));
                return CommonResult.success("提交成功");
            } else {
                /*抛出异常*/
                throw new RuntimeException("提交失败，请联系管理员");
            }
        } catch (BeansException e) {
            e.printStackTrace();
            //return CommonResult.error(e.getMessage());
            throw new RuntimeException("提交失败，请联系管理员");
        }

    }


    /*返回时处理数据*/
    private void processDataOnReturn(List<RecordTrueingDto> dto) {
        dahcRecordProcedureFilesService.update(Wrappers.<DahcRecordProcedureFiles>lambdaUpdate()
                .eq(DahcRecordProcedureFiles::getId, dto.get(0).getRecordProcedureFilesId())
                .set(DahcRecordProcedureFiles::getIsReturnSave, 1));
    }

    /*存疑处理数据*/
    private void processDataImpeach(List<RecordTrueingDto> dto) {
        dahcRecordProcedureFilesService.update(Wrappers.<DahcRecordProcedureFiles>lambdaUpdate()
                .eq(DahcRecordProcedureFiles::getId, dto.get(0).getRecordProcedureFilesId())
                .set(DahcRecordProcedureFiles::getIsProcedureInspect, 3)
                .set(DahcRecordProcedureFiles::getIsReturnSave, 1)
                .set(DahcRecordProcedureFiles::getIsImpeach, 0));

    }

    /*处理新增的数据*/
    private DahcRecordTrueingResult disposeInspectData(DahcRecordTrueingResult dahcRecordTrueingResult, RecordTrueingDto recordTrueingDto) {

        dahcRecordTrueingResult.setInspectName(SecurityUtils.getUsername());
        dahcRecordTrueingResult.setInspectId(SecurityUtils.getUserId());

        /*合格不合格*/
        recordTrueingDto.getCheckResult().removeAll(Collections.singleton(null)); //清空list集合里的All elements are null
        if (recordTrueingDto.getCheckResult().size() > 0) {
            dahcRecordTrueingResult.setCheckResultState(recordTrueingDto.getCheckResult().get(0));
        }
/*        else {
            throw new RuntimeException("核查结果不能为空");
        }*/
        /*是否存疑*/
        recordTrueingDto.getProcessMode().removeAll(Collections.singleton(null)); //清空list集合里的All elements are null
        if (recordTrueingDto.getProcessMode().size() > 0) {
            dahcRecordTrueingResult.setIsQuestion(recordTrueingDto.getProcessMode().get(0));
            if (recordTrueingDto.getProcessMode().get(0).equals("impeach")) {
                /*存疑数据*/
                dahcRecordTrueingResult.setQuestionableData(1);
            }
        }
        /*是否是原件*/
        recordTrueingDto.getChangeContent().removeAll(Collections.singleton(null)); //清空list集合里的All elements are null
        if (recordTrueingDto.getChangeContent().size() > 0) {
            dahcRecordTrueingResult.setIsMasterCopy(recordTrueingDto.getChangeContent().get(0));
        }
        return dahcRecordTrueingResult;
    }


    /*新增提交核查结果的同时，修改档案状态表*/
    private int updateAndAddRecordProcedure(List<RecordTrueingDto> dto, Integer state) {

        ///*获取当前是否第一个工序，第一个工序修改，剩下的工序为新增*/
        List<DahcProjectProcedure> procedureList = projectProcedureService.list(Wrappers.<DahcProjectProcedure>lambdaQuery()
                .eq(DahcProjectProcedure::getProjectId, dto.get(0).getProjectId())
                .orderByAsc(DahcProjectProcedure::getSort));
        if (procedureList.size() == 0) {
            throw new RuntimeException("提交失败");
        }
        /*添加当前工序案卷的记录信息*/
        /*获取当前id属于当前节点的第几工序*/
        for (int i = 0; i < procedureList.size(); i++) {
            if (dto.get(0).getProcedureId().equals(procedureList.get(i).getId())) {
                /*修改当前工序案卷的记录信息*/
                LambdaUpdateWrapper<DahcRecordProcedureFiles> updateWrapper = Wrappers.<DahcRecordProcedureFiles>lambdaUpdate()
                        //.eq(DahcRecordProcedureFiles::getProcedureId, dto.get(i).getProcedureId())
                        .eq(DahcRecordProcedureFiles::getProcedureId, procedureList.get(i).getId())
                        .eq(DahcRecordProcedureFiles::getProjectId, dto.get(0).getProjectId())
                        .eq(DahcRecordProcedureFiles::getFilesName, dto.get(0).getRollId())
                        .set(DahcRecordProcedureFiles::getIsProcedureInspect, 1)
                        .set(DahcRecordProcedureFiles::getFilesId, dto.get(0).getFilesId())
                        .set(DahcRecordProcedureFiles::getIsReturnSave, 0)
                        .set(DahcRecordProcedureFiles::getAccomplishTime, new Date());

                /*0-正常提交  1-存疑提交*/
                if (state == 0) {
                    updateWrapper.set(DahcRecordProcedureFiles::getInspectId, SecurityUtils.getUserId());
                } else {
                    updateWrapper.set(DahcRecordProcedureFiles::getImpeachSolveId, SecurityUtils.getUserId());
                }
                dahcRecordProcedureFilesService.update(updateWrapper);

                /*判断是否是最后一个工序*/
                if (i != procedureList.size() - 1) {
                    /*新增下个工序的档案关联数据*/
                    DahcRecordProcedureFiles procedureFiles = new DahcRecordProcedureFiles();
                    procedureFiles.setId(IdUtils.fastSimpleUUID());
                    procedureFiles.setProcedureId(procedureList.get(i + 1).getId());
                    procedureFiles.setFilesName(dto.get(0).getRollId());
                    procedureFiles.setIsProcedureInspect("0");
                    procedureFiles.setFilesId(dto.get(0).getFilesId());
                    procedureFiles.setProjectId(dto.get(0).getProjectId());
                    procedureFiles.setCreateTime(new Date());
                    dahcRecordProcedureFilesService.save(procedureFiles);
                }
                break;
            }
        }
        return 0;
    }


    /**
     * 电子档案: 查询电子档案数据 案卷级
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/2 12:15
     */
    @Override
    public List<Map<String, Object>> queryElectronicArchivesDossierLevel(QueryElectronicArchivesDossierLevelDto dto, Integer start) {
        /*根据工序id、表名、和核查人查询领取的案卷数据*/
        List<Map<String, Object>> maps = dahcRecordProcedureFilesMapper.queryElectronicArchivesDossierLevel(
                dto.getArchiveLevelName(), dto.getProcedureId(), dto.getIsProcedureInspect(), SecurityUtils.getUserId(), dto.getAttrOrder(), dto.getCaseNum(), start);
        return maps;
    }

    @Override
    public CommonGridResult queryElectronicArchivesDossierLevelTemplateTwo(QueryElectronicArchivesDossierLevelDto dto) {
        /*根据工序id、表名、和核查人查询领取的案卷数据*/
        List<Map<String, Object>> maps = new ArrayList<>();
        Page page = new Page();
        Boolean rolePermissions = dahcSysDictDataService.getRolePermissions(SecurityUtils.getUserId());
        //if (!SecurityUtils.getUsername().equals("admin") && !rolePermissions) {
        //    /*管理员用户*/
        //    if (dto.getIsProcedureInspect().equals("0")) {
        //        maps = dahcRecordTrueingResultService.queryElectronicArchivesDossierLevel(dto,1);
        //    } else {
        //        /*参看有没有核查到一半的数据*/
        //        maps = dahcRecordProcedureFilesMapper.queryElectronicArchivesDossierLevelTemplateTwo(
        //                dto.getArchiveLevelName(), dto.getProcedureId(), dto.getIsProcedureInspect(), SecurityUtils.getUserId(), dto.getAttrOrder(), dto.getCaseNum(),0);
        //        if (maps.size() == 0) {
        //            /*查询领取数据*/
        //            maps = dahcRecordProcedureFilesMapper.queryElectronicArchivesDossierLevelTemplateTwo(
        //                    dto.getArchiveLevelName(), dto.getProcedureId(), dto.getIsProcedureInspect(), SecurityUtils.getUserId(), dto.getAttrOrder(), dto.getCaseNum(),1);
        //        }
        //    }
        //} else {
        maps = dahcRecordTrueingResultService.queryElectronicArchivesDossierLevel(dto, 0);
        if (maps.size() == 0) {
            PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
            maps = dahcRecordTrueingResultService.queryElectronicArchivesDossierLevel(dto, 1);
            page = (Page) maps;
        }
        //}

        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), maps);
    }

    /**
     * 查询普通核查项数据
     *
     * @return com.fudian.dahc.pojo.dto.RecordTrueingDto
     * @author MCY
     * @date 2023/3/3 11:12
     */
    @Override
    public List<RecordTrueingDto> queryCheckItems(RecordTrueingDto dto) {
        List<RecordTrueingDto> dtoList = dahcRecordTrueingResultMapper.queryInspectTableList(dto);
        return dtoList;
    }


    /**
     * 回显核查结果
     *
     * @return java.util.List<com.fudian.dahc.pojo.dto.RecordTrueingDto>
     * @author MCY
     * @date 2023/3/3 11:17
     */
    @Override
    public RecordTrueingDto echoTheVerificationResults(RecordTrueingDto recordTrueingDto, DahcRecordProcedureFiles dahcRecordProcedureFiles) {

        /*没有核查件和页的数据*/
        List<DahcRecordTrueingResult> recordTrueingResults = dahcRecordTrueingResultMapper.selectList(Wrappers.<DahcRecordTrueingResult>lambdaQuery()
                .eq(DahcRecordTrueingResult::getRecordProcedureFilesId, dahcRecordProcedureFiles.getId())
                .eq(DahcRecordTrueingResult::getTrueingId, recordTrueingDto.getTrueingId())
                .eq(DahcRecordTrueingResult::getIsNumberOfCases, 1)
                .eq(DahcRecordTrueingResult::getIsPageNumFile, 1));

        /*件数据*/
        List<DahcRecordTrueingResult> numberOfCasesResults = dahcRecordTrueingResultMapper.selectList(Wrappers.<DahcRecordTrueingResult>lambdaQuery()
                .eq(DahcRecordTrueingResult::getRecordProcedureFilesId, dahcRecordProcedureFiles.getId())
                .eq(DahcRecordTrueingResult::getTrueingId, recordTrueingDto.getTrueingId())
                .eq(DahcRecordTrueingResult::getIsNumberOfCases, 0));
        /*页数据*/
        List<DahcRecordTrueingResult> pageNumFileResults = dahcRecordTrueingResultMapper.selectList(Wrappers.<DahcRecordTrueingResult>lambdaQuery()
                .eq(DahcRecordTrueingResult::getRecordProcedureFilesId, dahcRecordProcedureFiles.getId())
                .eq(DahcRecordTrueingResult::getTrueingId, recordTrueingDto.getTrueingId())
                .eq(DahcRecordTrueingResult::getIsPageNumFile, 0));
        if (recordTrueingResults.size() > 0) {
            recordTrueingDto = stitchingAndPageData(recordTrueingResults, recordTrueingDto);
        }
        if (numberOfCasesResults.size() > 0) {
            recordTrueingDto = stitchingAndPageData(numberOfCasesResults, recordTrueingDto);
        }

        if (pageNumFileResults.size() > 0) {
            recordTrueingDto = stitchingAndPageData(pageNumFileResults, recordTrueingDto);
        }

        /*拼接件*/
        List<Map<String, Long>> maps = new ArrayList<>();
        if (numberOfCasesResults.size() > 0) {
            for (DahcRecordTrueingResult dahcRecordTrueingResult : numberOfCasesResults) {
                Map<String, Long> caseNumSMap = new HashMap<>();
                caseNumSMap.put("value", dahcRecordTrueingResult.getNumberOfCases());
                maps.add(caseNumSMap);
            }
        }
        recordTrueingDto.setCaseNumS(maps);
        /*拼接页*/
        List<Map<String, Long>> pageNumMaps = new ArrayList<>();
        if (pageNumFileResults.size() > 0) {
            for (DahcRecordTrueingResult dahcRecordTrueingResult : pageNumFileResults) {
                Map<String, Long> pageNumSMap = new HashMap<>();
                pageNumSMap.put("value", dahcRecordTrueingResult.getPageNumFile());
                pageNumMaps.add(pageNumSMap);
            }
        }
        recordTrueingDto.setPageNumS(pageNumMaps);


        /*拼接核查标准*/
        recordTrueingDto.setExamineStasString(dahcHcxTrueingStandardService.queryById(recordTrueingDto.getTrueingId()));
        return recordTrueingDto;
    }


    @Override
    public List<RecordTrueingDto> doubtQueryVerificationResultData(QueryElectronicArchivesDossierLevelDto dto) {
        List<RecordTrueingDto> dtoList = new ArrayList<>();
        /*根据档案核查结果id查询数据*/
        if (dto.getIsImpeach() == null) {
            /*存疑*/
            dtoList = dahcRecordTrueingResultMapper.doubtQueryVerificationResultData("impeach", dto.getRecordProcedureFilesId());
        } else {
            dtoList = dahcRecordTrueingResultMapper.doubtQueryVerificationResultData(null, dto.getRecordProcedureFilesId());
        }


        /*拼接核查结果*/
        for (RecordTrueingDto recordTrueingDto : dtoList) {

            /*处理基本数据*/
            recordTrueingDto = impeachStitchingAndPageData(recordTrueingDto);

            /*处理存疑件页数据*/
            List<DahcRecordTrueingResult> recordTrueingResults = dahcRecordTrueingResultMapper.selectList(Wrappers.<DahcRecordTrueingResult>lambdaQuery().eq(DahcRecordTrueingResult::getRecordProcedureFilesId, dto.getRecordProcedureFilesId())
                    .eq(DahcRecordTrueingResult::getTrueingId, recordTrueingDto.getTrueingId()));
            List<Map<String, Long>> mapList = new ArrayList<>();
            for (DahcRecordTrueingResult recordTrueingResult : recordTrueingResults) {
                HashMap<String, Long> map = new HashMap<>();
                if (recordTrueingResult.getImpeachNumberOfCases() != null || recordTrueingResult.getImpeachPageNumFile() != null) {
                    map.put("arrItem", recordTrueingResult.getImpeachNumberOfCases());
                    map.put("arrPage", recordTrueingResult.getImpeachPageNumFile());
                    mapList.add(map);
                }
            }
            recordTrueingDto.setImpeachPageNumS(mapList);

            /*是否显示表头*/
            DahcHcxTrueingManagement one = dahcHcxTrueingManagementService.getOne(Wrappers.<DahcHcxTrueingManagement>lambdaQuery().eq(DahcHcxTrueingManagement::getId, recordTrueingDto.getTrueingId()));
            recordTrueingDto.setShowPiece(Integer.valueOf(one.getShowPiece()));
            recordTrueingDto.setShowPageNumber(Integer.valueOf(one.getShowPageNumber()));

            /*拼接核查标准*/
            recordTrueingDto.setExamineStasString(dahcHcxTrueingStandardService.queryById(recordTrueingDto.getTrueingId()));
        }

        return dtoList;
    }


    /*拼接件和页数据*/
    private RecordTrueingDto impeachStitchingAndPageData(RecordTrueingDto recordTrueingDto) {
        /*拼接基本数据*/
        /*是否合格*/
        RecordTrueingDto finalRecordTrueingDto = recordTrueingDto;
        recordTrueingDto.setCheckResult(new ArrayList<String>() {{
            add(finalRecordTrueingDto.getCheckResultState());
        }});
        /*是否存疑*/
        recordTrueingDto.setProcessMode(new ArrayList<String>() {{
            add(finalRecordTrueingDto.getIsQuestion());
        }});
        /*是否是原件*/
        recordTrueingDto.setChangeContent(new ArrayList<String>() {{
            add(finalRecordTrueingDto.getIsMasterCopy());
        }});
        return recordTrueingDto;
    }


    @Override
    @Transactional
    public CommonResult submissionOfVerificationResultsInDoubt(List<RecordTrueingDto> dto) {

        try {
            /*清空存疑数据*/
            if (dto.size() > 0) {

                this.update(Wrappers.<DahcRecordTrueingResult>lambdaUpdate()
                        .in(DahcRecordTrueingResult::getTrueingId, dto.stream().map(RecordTrueingDto::getTrueingId).collect(Collectors.toList()))
                        .eq(DahcRecordTrueingResult::getRecordProcedureFilesId, dto.get(0).getRecordProcedureFilesId())
                        .set(DahcRecordTrueingResult::getImpeachNumberOfCases, null)
                        .set(DahcRecordTrueingResult::getImpeachPageNumFile, null));

                /*处理页数据等*/
                for (RecordTrueingDto recordTrueingDto : dto) {
                    /*获取对应的核查结果id集合*/
                    List<DahcRecordTrueingResult> recordTrueingResults = dahcRecordTrueingResultMapper.selectList(Wrappers.<DahcRecordTrueingResult>lambdaQuery()
                            .eq(DahcRecordTrueingResult::getTrueingId, recordTrueingDto.getTrueingId())
                            .eq(DahcRecordTrueingResult::getRecordProcedureFilesId, recordTrueingDto.getRecordProcedureFilesId()));
                    List<Map<String, Long>> impeachPageNumS = recordTrueingDto.getImpeachPageNumS();
                    /*修改存疑 -- 页数据*/
                    if (impeachPageNumS.size() > 0) {
                        for (int i = 0; i < impeachPageNumS.size(); i++) {
                            recordTrueingResults.get(i).setImpeachPageNumFile(impeachPageNumS.get(i).get("arrPage"));
                            recordTrueingResults.get(i).setImpeachNumberOfCases(impeachPageNumS.get(i).get("arrItem"));
                            dahcRecordTrueingResultMapper.updateById(recordTrueingResults.get(i));
                        }
                    }
                    /*修改基本数据*/
                    recordTrueingDto.getProcessMode().removeAll(Collections.singleton(null)); //清空list集合里的All elements are null
                    if (recordTrueingDto.getProcessMode().size() > 0) {
                        this.update(Wrappers.<DahcRecordTrueingResult>lambdaUpdate()
                                .in(DahcRecordTrueingResult::getId, recordTrueingResults.stream().map(DahcRecordTrueingResult::getId).collect(Collectors.toList()))
                                .set(DahcRecordTrueingResult::getIsQuestion, recordTrueingDto.getProcessMode().get(0))
                                .set(DahcRecordTrueingResult::getRemark, recordTrueingDto.getRemark()));
                    }
                }
                Integer submit = null;
                /*修改档案核查结果状态提交到下一工序*/
                if (dto.get(0).getIsInspectAccomplish() == 0) {
                    /*提交执行方法*/
                    updateAndAddRecordProcedure(dto, 1);
                    submit = 0;
                }
                DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.get(0).getProjectId(), dto.get(0).getProcedureId(),dto.get(0).isCheckStatus());
                dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                        dto.get(0).getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                        dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), dahcCheckRecordLog.getFileTableName(),
                        dto.get(0).getRollId(), null, dto.get(0).getFilesId(), null, 0,
                        JSON.toJSONString(dto), null, submit,dahcCheckRecordLog.getCheckStatus()));
            } else {
                return CommonResult.error("不存在核查结果");
            }

            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("提交失败");
        }

    }


}
