package com.fudian.dahc.service.project.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.uuid.IdUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.mapper.business.DahcArchiveTypeMapper;
import com.fudian.dahc.mapper.project.DahcProjectProcedureTrueingMapper;
import com.fudian.dahc.mapper.project.ProjectProcedureMapper;
import com.fudian.dahc.mapper.sys.DahcSysUserProcedureMapper;
import com.fudian.dahc.mapper.trueingManage.DahcHcxTrueingManagementMapper;
import com.fudian.dahc.pojo.dto.UserVo;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedure;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedureTrueing;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictData;
import com.fudian.dahc.pojo.entity.sys.DahcSysUserProcedure;
import com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement;
import com.fudian.dahc.pojo.query.ProjectProcedureQuery;
import com.fudian.dahc.service.base.impl.MyBaseServiceImpl;
import com.fudian.dahc.service.project.IDahcProjectProcedureTrueingService;
import com.fudian.dahc.service.project.ProjectProcedureService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordProcedureFilesService;
import com.fudian.dahc.service.sys.IDahcSysDictDataService;
import com.fudian.dahc.service.sys.IDahcSysUserProcedureService;
import com.fudian.dahc.service.trueing.IDahcHcxTrueingManagementService;
import com.fudian.dahc.service.trueing.IDahcHcxTrueingStandardService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 2023/2/7
 */
@Service
public class ProjectProcedureServiceImpl extends MyBaseServiceImpl<DahcProjectProcedure> implements ProjectProcedureService {


    @Autowired
    private ProjectProcedureMapper projectProcedureMapper;

    @Autowired
    private DahcHcxTrueingManagementMapper dahcHcxTrueingManagementMapper;
    @Autowired
    private IDahcProjectProcedureTrueingService dahcProjectProcedureTrueingService;

    @Autowired
    private IDahcHcxTrueingManagementService dahcHcxTrueingManagementService;

    @Autowired
    private IDahcHcxTrueingStandardService dahcHcxTrueingStandardService;
    @Autowired
    private DahcProjectProcedureTrueingMapper dahcProjectProcedureTrueingMapper;

    @Autowired
    private IDahcSysUserProcedureService dahcSysUserProcedureService;

    @Autowired
    private DahcSysUserProcedureMapper dahcSysUserProcedureMapper;

    @Autowired
    private DahcArchiveTypeMapper dahcArchiveTypeMapper;

    @Autowired
    private IDahcSysDictDataService dahcSysDictDataService;

    @Autowired
    private IDahcRecordProcedureFilesService dahcRecordProcedureFilesService;

    @Override
    public List<DahcProjectProcedure> searchPage(ProjectProcedureQuery projectProcedureQuery) {
        Long userId = SecurityUtils.getUserId();
        MPJLambdaWrapper<DahcProjectProcedure> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.selectAll(DahcProjectProcedure.class);
        queryWrapper.selectAs(DahcProjectProcedure::getProjectId, "projectIda");
        if (StringUtils.isNotEmpty(projectProcedureQuery.getProjectProcedureName())) {
            queryWrapper.like(DahcProjectProcedure::getProcedureName, projectProcedureQuery.getProjectProcedureName());
        }
/*        Boolean rolePermissions = dahcSysDictDataService.getRolePermissions(SecurityUtils.getUserId());
        if (!SecurityUtils.isAdmin(userId)&&!rolePermissions) {
            queryWrapper.leftJoin("dahc_sys_user_procedure dsup on dsup.procedure_id =t.id");
            queryWrapper.eq("dsup.user_id", userId);
        }*/
        queryWrapper.eq(DahcProjectProcedure::getProjectId, projectProcedureQuery.getProjectId());
        queryWrapper.orderByAsc(DahcProjectProcedure::getSort);
        List<DahcProjectProcedure> dahcProjectProcedures = projectProcedureMapper.selectList(queryWrapper);

        //关联创建人信息
        List<UserVo> userVos = dahcArchiveTypeMapper.queryAllUsers();
        Map<Long, UserVo> collect = userVos.stream().collect(Collectors.toMap(UserVo::getId, userVo -> userVo));
        dahcProjectProcedures.parallelStream().forEach(a -> {
            if (collect.containsKey(a.getCreateBy())) {
                UserVo userVo = collect.get(a.getCreateBy());
                a.setCreateByName(userVo.getName());
            } else {
                a.setCreateByName("未知");
            }
            if (collect.containsKey(a.getUpdateBy())) {
                UserVo userVo = collect.get(a.getUpdateBy());
                a.setUpdateByName(userVo.getName());
            } else {
                a.setUpdateByName("未知");
            }
            a.setTheNumberOfFilesNotVerified(dahcRecordProcedureFilesService.theNumberOfFilesNotVerified(a.getId()));
            a.setNumberOfFilesVerified(dahcRecordProcedureFilesService.numberOfFilesVerified(a.getId()));
            a.setNumberOfPeopleVerified(projectProcedureMapper.numberOfPeopleVerified(a.getId()));
        });
        return dahcProjectProcedures;
    }

    @Override
    @Transactional
    public CommonResult addProcedure(DahcProjectProcedure dahcProjectProcedure) {
        try {
            List<DahcProjectProcedure> dahcProjectProcedures = projectProcedureMapper.selectList(Wrappers.<DahcProjectProcedure>lambdaQuery()
                    .eq(DahcProjectProcedure::getSort, dahcProjectProcedure.getSort())
                    .eq(DahcProjectProcedure::getProjectId, dahcProjectProcedure.getProjectId()));
            List<DahcProjectProcedure> dahcProcedureNames = projectProcedureMapper.selectList(Wrappers.<DahcProjectProcedure>lambdaQuery()
                    .eq(DahcProjectProcedure::getProcedureName, dahcProjectProcedure.getProcedureName())
                    .eq(DahcProjectProcedure::getProjectId, dahcProjectProcedure.getProjectId()));
            if (dahcProjectProcedures.size() > 0) {
                return CommonResult.error("工序排序重复");
            }
            if (dahcProcedureNames.size() > 0) {
                return CommonResult.error("工序名称重复");
            }
            /*新增工序数据*/
            String uuid = IdUtils.fastSimpleUUID();
            dahcProjectProcedure.setId(uuid);
            dahcProjectProcedure.setCreateTime(LocalDateTime.now());
            dahcProjectProcedure.setCreateBy(SecurityUtils.getUserId());
            dahcProjectProcedure.setCreateByString(SecurityUtils.getUsername());
            dahcProjectProcedure.setTrueingPagePath(dahcSysDictDataService.getOne(Wrappers.<DahcSysDictData>lambdaQuery()
                    .eq(DahcSysDictData::getId, dahcProjectProcedure.getTrueingPagePathId())
                    .eq(DahcSysDictData::getType, 1)).getCodeProperty());
            int insert = projectProcedureMapper.insert(dahcProjectProcedure);
            if (insert > 0) {
                Boolean aBoolean = true;
                /*新增关联数据*/
                List<String> strings = new ArrayList<>();
                List<DahcHcxTrueingManagement> dahcHcxTrueingManageList = dahcProjectProcedure.getDahcHcxTrueingManageList();
                for (DahcHcxTrueingManagement dahcHcxTrueingManagement : dahcHcxTrueingManageList) {
                    strings.add(dahcHcxTrueingManagement.getId());
                }
                if (strings.size() > 0) {
                    aBoolean = dahcProjectProcedureTrueingService.addProcedureTrueing(strings, uuid);
                }
                if (aBoolean) {
                    return CommonResult.success("新增成功");
                } else {
                    return CommonResult.error("新增失败");
                }
            } else {
                return CommonResult.success("新增成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(e.getMessage());
        }
    }


    /**
     * 修改工序
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/14 10:41
     */
    @Override
    @Transactional
    public CommonResult updateProcedure(DahcProjectProcedure dahcProjectProcedure) {
        /*查重*/
        List<DahcProjectProcedure> dahcProjectProcedures = projectProcedureMapper.selectList(Wrappers.<DahcProjectProcedure>lambdaQuery()
                .eq(DahcProjectProcedure::getSort, dahcProjectProcedure.getSort())
                .eq(DahcProjectProcedure::getProjectId, dahcProjectProcedure.getProjectId())
                .ne(DahcProjectProcedure::getId, dahcProjectProcedure.getId()));
        List<DahcProjectProcedure> dahcProcedureNames = projectProcedureMapper.selectList(Wrappers.<DahcProjectProcedure>lambdaQuery()
                .eq(DahcProjectProcedure::getProcedureName, dahcProjectProcedure.getProcedureName())
                .eq(DahcProjectProcedure::getProjectId, dahcProjectProcedure.getProjectId())
                .ne(DahcProjectProcedure::getId, dahcProjectProcedure.getId()));
        if (dahcProjectProcedures.size() > 0) {
            return CommonResult.error("工序排序重复");
        }
        if (dahcProcedureNames.size() > 0) {
            return CommonResult.error("工序名称重复");
        }
        dahcProjectProcedure.setTrueingPagePath(dahcSysDictDataService.getOne(Wrappers.<DahcSysDictData>lambdaQuery()
                .eq(DahcSysDictData::getId, dahcProjectProcedure.getTrueingPagePathId())
                .eq(DahcSysDictData::getType, 1)).getCodeProperty());
        this.updateById(dahcProjectProcedure);
        /*根据工序id删除关联核查项数据*/
        dahcProjectProcedureTrueingService.deleteProcedureTrueing(dahcProjectProcedure.getId());
        /*保存新的关联关系*/
        Boolean bBoolean = true;
        List<String> strings = new ArrayList<>();
        List<DahcHcxTrueingManagement> dahcHcxTrueingManageList = dahcProjectProcedure.getDahcHcxTrueingManageList();
        if (dahcHcxTrueingManageList.size() > 0) {
            for (DahcHcxTrueingManagement dahcHcxTrueingManagement : dahcHcxTrueingManageList) {
                strings.add(dahcHcxTrueingManagement.getId());
            }
            bBoolean = dahcProjectProcedureTrueingService.addProcedureTrueing(strings, dahcProjectProcedure.getId());
        }
        if (bBoolean) {
            return CommonResult.success("修改成功");
        } else {
            return CommonResult.error("修改失败");
        }
    }

    /**
     * 批量删除工序
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/14 11:13
     */
    @Override
    @Transactional
    public CommonResult deleteProcedure(List<String> ids) {
        try {
            List<DahcProjectProcedureTrueing> list = dahcProjectProcedureTrueingService.list(Wrappers.<DahcProjectProcedureTrueing>lambdaQuery().in(DahcProjectProcedureTrueing::getProcedureId, ids));
            if (list.size() > 0) {
                return CommonResult.error("存在关联核查项数据，请先删除核查项关联关系");
            }
            /*删除工序*/
            int delete = projectProcedureMapper.delete(Wrappers.<DahcProjectProcedure>lambdaQuery().in(DahcProjectProcedure::getId, ids));
            if (delete > 0) {
                /*删除关联数据*/
                dahcProjectProcedureTrueingService.deleteProcedureTrueingIds(ids);
                return CommonResult.success("删除成功");
            }
            return CommonResult.error("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(e.getMessage());
        }
    }

    /**
     * 删除工序核查项
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/14 14:27
     */
    @Override
    @Transactional
    public CommonResult deleteProcedureTrueingId(String trueingId) {
        boolean remove = dahcProjectProcedureTrueingService.remove(Wrappers.<DahcProjectProcedureTrueing>lambdaQuery().eq(DahcProjectProcedureTrueing::getTrueingId, trueingId));
        if (remove) {
            return CommonResult.success("删除成功");
        } else {
            return CommonResult.error("删除失败");
        }

    }

    /**
     * 查询用户关联工序
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/16 14:36
     */
    @Override
    public CommonResult queryUserProcedure(Map<String, String> userMessage) {
        String userId = userMessage.get("userId");
        String projectId = userMessage.get("projectId");
        Boolean b = Boolean.valueOf(userMessage.get("boolean"));

        /*查询用户关联工序表*/
        List<DahcSysUserProcedure> list = dahcSysUserProcedureService.list(Wrappers.<DahcSysUserProcedure>lambdaQuery()
                .eq(DahcSysUserProcedure::getUserId, userId).eq(DahcSysUserProcedure::getProjectId, projectId));
        /*关联工序id集合*/
        List<String> procedureIds = new ArrayList<>();
        List<DahcProjectProcedure> procedureList = new ArrayList<>();
        if (list.size() > 0) {
            for (DahcSysUserProcedure dahcSysUserProcedure : list) {
                procedureIds.add(dahcSysUserProcedure.getProcedureId());
            }
        } else if (list.size() == 0 && b) {
            /*没有关联数据，返回空*/
            return CommonResult.success(procedureList);
        } else if (list.size() == 0 && !b) {
            /*没有关联数据，返回当前项目的所有工序数据*/
            procedureList = projectProcedureMapper.selectList(Wrappers.<DahcProjectProcedure>lambdaQuery().eq(DahcProjectProcedure::getProjectId, projectId).orderByAsc(DahcProjectProcedure::getSort));
            return CommonResult.success(procedureList);
        }

        /*查询用户关联的工序数据*/
        if (b) {
            procedureList = projectProcedureMapper.selectList(Wrappers.<DahcProjectProcedure>lambdaQuery()
                    .in(DahcProjectProcedure::getId, procedureIds).eq(DahcProjectProcedure::getProjectId, projectId).orderByAsc(DahcProjectProcedure::getSort));
        } else {
            /*查询用户未关联的工序数据*/
            procedureList = projectProcedureMapper.selectList(Wrappers.<DahcProjectProcedure>lambdaQuery()
                    .notIn(DahcProjectProcedure::getId, procedureIds).eq(DahcProjectProcedure::getProjectId, projectId).orderByAsc(DahcProjectProcedure::getSort));
        }
        return CommonResult.success(procedureList);
    }


    @Override
    @Transactional
    public CommonResult saveUserProcedure(Map<String, Object> userMessage) {
        Integer userId = (Integer) userMessage.get("userId");
        String projectId = (String) userMessage.get("projectId");
        List<String> procedureIds = (List<String>) userMessage.get("procedureIds");
        /*删除关联关系*/
        dahcSysUserProcedureService.remove(Wrappers.<DahcSysUserProcedure>lambdaQuery()
                .eq(DahcSysUserProcedure::getProjectId, projectId).eq(DahcSysUserProcedure::getUserId, userId));
        int i = 1;
        /*保存数据*/
        if (procedureIds.size() > 0) {
            i = dahcSysUserProcedureMapper.saveUserProcedure(Integer.toString(userId), projectId, procedureIds);
        }
        if (i > 0) {
            return CommonResult.success("新增成功");
        }
        return CommonResult.error("新增失败");
    }

    /**
     * 根据id查询核查项数据
     *
     * @return java.util.List<com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement>
     * @author MCY
     * @date 2023/2/14 11:54
     */
    @Override
    public CommonGridResult queryInspectTableList(ProjectProcedureQuery projectProcedureQuery) {
        /*根据工序id查询关联表，获取关联数据的核查id集合*/
        //List<String> strings = dahcProjectProcedureTrueingService.queryProcedureTrueing(projectProcedureQuery.getId());
        PageHelper.startPage(projectProcedureQuery.getPageNum(), projectProcedureQuery.getPageSize());
        /*根据id集合查询核查项数据*/
        List<DahcHcxTrueingManagement> list = dahcHcxTrueingManagementMapper.queryProcedureTrueingIds(projectProcedureQuery.getId());
        //List<DahcHcxTrueingManagement> list = dahcHcxTrueingManagementService.queryProcedureTrueingIds(strings);
        if (list.size() > 0) {
            for (DahcHcxTrueingManagement dahcHcxTrueingManagement : list) {
                /*拼接核查标准*/
                dahcHcxTrueingManagement.setExamineStasString(dahcHcxTrueingStandardService.queryById(dahcHcxTrueingManagement.getId()));
            }
        }
        Page<DahcHcxTrueingManagement> page = (Page) list;
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    /**
     * 编辑回显未选择的核查项数据
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/13 18:21
     */
    @Override
    public CommonResult searchProcedureUnselectedList(Map<String, String> procedureId) {
        /*项目id*/
        String procedureId1 = procedureId.get("procedureId");
        /*否--未选择核查项数据  是---选择的核查项数据*/
        Boolean b = Boolean.valueOf(procedureId.get("boolean"));
        /*核查项范围id*/
        String trueingId = procedureId.get("trueingId");

        /*获取关联核查项id数据*/
        List<String> trueingIds = dahcProjectProcedureTrueingService.queryProcedureTrueing(procedureId1);
        List<DahcHcxTrueingManagement> dahcHcxTrueingManagements = new ArrayList<>();
        if (b) {
            /*查询工序关联的核查项*/
            if (trueingIds.size() > 0) {
                dahcHcxTrueingManagements = dahcHcxTrueingManagementService.queryProcedureTrueing(trueingIds, b, trueingId);
            }
            return CommonResult.success(dahcHcxTrueingManagements);
        } else {
            /*查询工序未关联的核查项*/
            if (trueingIds.size() > 0) {
                dahcHcxTrueingManagements = dahcHcxTrueingManagementService.queryProcedureTrueing(trueingIds, b, trueingId);
            } else {
                /*如果长度等于零，查询当前核查范围的所有的*/
                dahcHcxTrueingManagements = dahcHcxTrueingManagementService.list(Wrappers.<DahcHcxTrueingManagement>lambdaQuery()
                        .eq(DahcHcxTrueingManagement::getTrueingScopeStair, trueingId));
            }
            return CommonResult.success(dahcHcxTrueingManagements);
        }

    }

    /**
     * 工序上下移
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/14 16:30
     */
    @Override
    @Transactional
    public CommonResult procedureUpAndDown(Map<String, String> id) {
        int i = projectProcedureMapper.modifyTheSort(id.get("id1"), id.get("id2"));
        if (i > 0) {
            return CommonResult.success("修改成功");
        } else {
            return CommonResult.error("修改失败");
        }
    }

    @Override
    @Transactional
    public CommonResult procedureInspectUpAndDown(Map<String, String> trueingId) {
        int i = dahcProjectProcedureTrueingMapper.procedureInspectUpAndDown(trueingId.get("id1"), trueingId.get("id2"), trueingId.get("procedureId"));
        if (i > 0) {
            return CommonResult.success("修改成功");
        } else {
            return CommonResult.error("修改失败");
        }
    }

    /**
     * 根据项目和登录人员id查询工序数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/24 11:09
     */
    @Override
    public CommonResult queryUserProjectProcedure(String projectId) {
        List<DahcProjectProcedure> dahcProjectProcedures = new ArrayList<>();
        Boolean rolePermissions = dahcSysDictDataService.getRolePermissions(SecurityUtils.getUserId());
        if (SecurityUtils.getUserId() == 1 || rolePermissions) {
            //dahcProjectProcedures = projectProcedureMapper.queryUserProjectProcedure(projectId, "");
            dahcProjectProcedures = projectProcedureMapper.selectList(Wrappers.<DahcProjectProcedure>lambdaQuery().eq(DahcProjectProcedure::getProjectId, projectId).orderByAsc(DahcProjectProcedure::getSort));
        } else {
            dahcProjectProcedures = projectProcedureMapper.queryUserProjectProcedure(projectId, String.valueOf(SecurityUtils.getUserId()));
        }
        return CommonResult.success(dahcProjectProcedures);
    }


    /**
     * 查询排在首个的工序
     */
    @Override
    public DahcProjectProcedure queryTheFirstOperation(String projectId) {
        return projectProcedureMapper.queryTheFirstOperation(projectId);
    }

}
