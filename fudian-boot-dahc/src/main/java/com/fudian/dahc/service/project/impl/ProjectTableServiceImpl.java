package com.fudian.dahc.service.project.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.CommonStatus;
import com.fudian.dahc.mapper.business.DahcArchiveTypeMapper;
import com.fudian.dahc.mapper.project.ProjectProcedureMapper;
import com.fudian.dahc.mapper.project.ProjectTableMapper;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedure;
import com.fudian.dahc.pojo.entity.project.DahcProjectTable;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import com.fudian.dahc.pojo.query.ProjectTableQuery;
import com.fudian.dahc.service.base.impl.MyBaseServiceImpl;
import com.fudian.dahc.service.business.DataTemplateService;
import com.fudian.dahc.service.project.ProjectProcedureService;
import com.fudian.dahc.service.project.ProjectTableService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordProcedureFilesService;
import com.fudian.dahc.service.sys.IDahcSysDictDataService;
import com.fudian.dahc.util.common.AssertUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 2023/2/7
 */
@Service
public class ProjectTableServiceImpl extends MyBaseServiceImpl<DahcProjectTable> implements ProjectTableService {


    @Autowired
    private ProjectTableMapper projectTableMapper;

    @Autowired
    private ProjectProcedureService projectProcedureService;


    @Autowired
    private ProjectProcedureMapper projectProcedureMapper;

    @Autowired
    private DataTemplateService dataTemplateService;

    @Autowired
    private DahcArchiveTypeMapper dahcArchiveTypeMapper;

    @Autowired
    private IDahcRecordProcedureFilesService iDahcRecordProcedureFilesService;
    @Autowired
    private IDahcSysDictDataService dahcSysDictDataService;


    @Override
    public List<DahcProjectTable> searchPage(ProjectTableQuery projectTableQuery) {
        //根据用户查询项目
        Long userId = SecurityUtils.getUserId();
        Collection<? extends GrantedAuthority> authorities = SecurityUtils.getLoginUser().getAuthorities();
        MPJLambdaWrapper<DahcProjectTable> lambda = new MPJLambdaWrapper<>();
        //判断用户isAdmin
        lambda.select("t.*,s1.nick_name as createByName,s2.nick_name as updateByName");
        lambda.like(StringUtils.hasText(projectTableQuery.getProjectTableName()), DahcProjectTable::getProjectName, projectTableQuery.getProjectTableName());
        lambda.eq(Objects.nonNull(projectTableQuery.getProjectState()), DahcProjectTable::getProjectState, projectTableQuery.getProjectState());
        lambda.leftJoin("sys_user s1 on  t.create_by =s1.user_id ");
        lambda.leftJoin("sys_user s2 on  t.update_by =s2.user_id ");
        lambda.orderByAsc(DahcProjectTable::getCreateTime);
        return projectTableMapper.selectList(lambda);
    }

    @Override
    @Transactional
    public int add(DahcProjectTable dahcProjectTable) {
        List<DahcProjectTable> dahcProjectTables = projectTableMapper.selectList(Wrappers.<DahcProjectTable>lambdaQuery().eq(DahcProjectTable::getProjectName, dahcProjectTable.getProjectName()));
        AssertUtil.isTrueServiceInvoke(dahcProjectTables.size() <= 0, CommonStatus.ERROR, "项目名称重复");
        return super.add(dahcProjectTable);
    }

    @Override
    public int update(DahcProjectTable dahcProjectTable) {
        List<DahcProjectTable> dahcProjectTables = projectTableMapper.selectList(Wrappers.<DahcProjectTable>lambdaQuery()
                .eq(DahcProjectTable::getProjectName, dahcProjectTable.getProjectName()).ne(DahcProjectTable::getId, dahcProjectTable.getId()));
        AssertUtil.isTrueServiceInvoke(dahcProjectTables.size() <= 0, CommonStatus.ERROR, "项目名称重复");
        return super.update(dahcProjectTable);
    }

    /**
     * 用于项目下拉框
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author MCY
     * @date 2023/2/16 15:44
     */
    @Override
    public CommonResult queryProjectPullDown() {
        List<Map<String, String>> listMap = null;
        try {
            List<DahcProjectTable> projectTables = this.list();
            listMap = new ArrayList<>();
            for (DahcProjectTable dahcProjectTable : projectTables) {
                Map<String, String> map = new HashMap<>();
                map.put("label", dahcProjectTable.getProjectName());
                map.put("value", dahcProjectTable.getId());
                map.put("archivesId", dahcProjectTable.getArchiveTypeId());
                map.put("projectState", dahcProjectTable.getProjectState());
                listMap.add(map);
            }
            return CommonResult.success(listMap);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }


    @Override
    @Transactional
    public CommonResult batchDeleteByIdsProjectTable(List<Long> ids) {
        try {
            //删除项目关联的数据
            MPJLambdaWrapper<DahcArchiveType> wrapper = new MPJLambdaWrapper<>();
            wrapper.selectAll(DahcArchiveType.class);
            wrapper.leftJoin(DahcProjectTable.class, DahcProjectTable::getArchiveTypeId, DahcArchiveType::getId);
            wrapper.in(DahcProjectTable::getId, ids);
            List<DahcArchiveType> dahcArchiveTypes = dahcArchiveTypeMapper.selectList(wrapper);
            dahcArchiveTypes.forEach(a -> {
                projectTableMapper.deleteProjectAssociatedData(ids, a.getTableLevel1En());
                projectTableMapper.deleteProjectAssociatedData(ids, a.getTableLevel2En());
            });
            int i = projectTableMapper.deleteBatchIds(ids);
            if (i > 0) {
                /*删除关联工序*/
                List<String> strings = new ArrayList<>();
                List<DahcProjectProcedure> list = projectProcedureMapper.selectList(Wrappers.<DahcProjectProcedure>lambdaQuery().in(DahcProjectProcedure::getProjectId, ids));
                if (list.size() > 0) {
                    for (DahcProjectProcedure dahcProjectProcedure : list) {
                        strings.add(dahcProjectProcedure.getId());
                    }
                    projectProcedureService.deleteProcedure(strings);
                }
                return CommonResult.success("删除成功");
            }
            return CommonResult.error("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error("删除失败");
        }
    }

    /**
     * 开始核查
     */
    @Override
    @Transactional
    public CommonResult startInspect(DahcProjectTable dahcProjectTable) {
        /*判断是否存在工序数据*/
        DahcProjectProcedure dahcProjectProcedure = projectProcedureService.queryTheFirstOperation(dahcProjectTable.getId());
        AssertUtil.isTrueServiceInvoke(dahcProjectProcedure != null, CommonStatus.ERROR, "该项目没有定义工序，无法开始核查");

        dahcProjectTable.setStartPracticalTime(new Date());
        int updateById = projectTableMapper.updateById(dahcProjectTable);
        AssertUtil.isTrueServiceInvoke(updateById > 0, CommonStatus.ERROR, "开始核查失败");

        String projectId = dahcProjectTable.getId();
        String archiveTypeId = dahcProjectTable.getArchiveTypeId();
        String procedureId = dahcProjectProcedure.getId();

        DahcArchiveType dahcArchiveType = dahcArchiveTypeMapper.selectById(archiveTypeId);
        AssertUtil.isTrueServiceInvoke(dahcArchiveType != null, CommonStatus.ERROR, "未找到对应档案");

        String tableLevel1En = dahcArchiveType.getTableLevel1En();
        String dh = dahcArchiveTypeMapper.queryProfileDefaultFieldBindingAttr(archiveTypeId, tableLevel1En, "档号");
        AssertUtil.isTrueServiceInvoke(dh != null, CommonStatus.ERROR, "未找到档号");

        //查询该项目中所有任务未开始状态的数据
        List<Map<String, String>> data = dataTemplateService.queryDataThatHasNotBeenChecked(tableLevel1En, "attr" + dh, projectId);
        List<DahcRecordProcedureFiles> filesList = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (Map<String, String> a : data) {
            String s = String.valueOf(a.get("id"));
            ids.add(s);
            Object filesName = a.get("attr" + dh);
            //isProcedureInspect 为0 表示开启核查任务 还未被领取
            filesList.add(new DahcRecordProcedureFiles(
                    s, String.valueOf(filesName), procedureId, dahcProjectTable.getId(), "0",new Date()));
        }
        if (ids.size() > 0) {
            //把数据加入到任务表中
            iDahcRecordProcedureFilesService.bulkInsert(filesList);
            dataTemplateService.modifyStatusValueInList(tableLevel1En, 2, 1, ids);
        }
        return CommonResult.success("开始核查任务" + filesList.size() + "条数据", null);
    }

    @Override
    @Transactional
    public CommonResult endOfVerification(DahcProjectTable dahcProjectTable) {

        /*获取当前项目所有的核查结果数据是否已完成*/
        List<DahcRecordProcedureFiles> list = iDahcRecordProcedureFilesService.list(Wrappers.<DahcRecordProcedureFiles>lambdaQuery()
                .eq(DahcRecordProcedureFiles::getProjectId, dahcProjectTable.getId()).eq(DahcRecordProcedureFiles::getIsAccomplishInspect, 1));
        if (list.size() > 0) {
            return CommonResult.error("案卷" + list.get(0).getFilesName() + "没有完成核查，请去任务管理模块手动确认");
        } else {
            /*修改档案项目状态*/
            int update = projectTableMapper.update(new DahcProjectTable(), Wrappers.<DahcProjectTable>lambdaUpdate().eq(DahcProjectTable::getId, dahcProjectTable.getId())
                    .set(DahcProjectTable::getProjectState, 2).set(DahcProjectTable::getEndPracticalTime,new Date()));
            if (update > 0) {
                return CommonResult.success();
            } else {
                return CommonResult.error("修改失败");
            }
        }
    }

    @Override
    public CommonResult queryTheProjectOperationTree() {
        List<DahcProjectTable> dahcProjectTables = projectTableMapper.selectList(Wrappers.<DahcProjectTable>lambdaQuery()
                .eq(DahcProjectTable::getProjectState, 1));
        if (dahcProjectTables.size() > 0) {
            /*theProcessOfQueryingForSuspiciousData*/
            List<DahcProjectProcedure> procedureList = projectProcedureMapper.theProcessOfQueryingForSuspiciousData();
            for (DahcProjectTable dahcProjectTable : dahcProjectTables) {
                dahcProjectTable.setProjectAndProcessState(0);
                dahcProjectTable.setChildren(procedureList.stream().filter(d -> d.getProjectId().equals(dahcProjectTable.getId())).collect(Collectors.toList()));
            }
        }
        return CommonResult.success(dahcProjectTables);
    }


    @Override
    public CommonResult queryThePersonalManagementProjectTree() {
        List<DahcProjectTable> dahcProjectTables = new ArrayList<>();
        //Boolean rolePermissions = dahcSysDictDataService.getRolePermissions(SecurityUtils.getUserId());
        //if ("admin".equals(SecurityUtils.getUsername()) || rolePermissions) {
        //    dahcProjectTables = projectTableMapper.queryThePersonalProjectTree(null);
        //} else {
            dahcProjectTables = projectTableMapper.queryThePersonalProjectTree(SecurityUtils.getUserId());
        //}

        for (DahcProjectTable dahcProjectTable : dahcProjectTables) {
            List<DahcProjectProcedure> procedureList = new ArrayList<>();
            //if ("admin".equals(SecurityUtils.getUsername()) || rolePermissions) {
            //    procedureList = projectProcedureMapper.queryThePersonalProcedureTree(null, dahcProjectTable.getId());
            //} else {
                procedureList = projectProcedureMapper.queryThePersonalProcedureTree(SecurityUtils.getUserId(), dahcProjectTable.getId());
            //}
            dahcProjectTable.setProjectAndProcessState(0);
            dahcProjectTable.setChildren(procedureList);
        }
        return CommonResult.success(dahcProjectTables);
    }
    @Override
    public CommonResult queryThePersonalTaskTree() {
        List<DahcProjectTable> dahcProjectTables = new ArrayList<>();
        Boolean rolePermissions = dahcSysDictDataService.getRolePermissions(SecurityUtils.getUserId());
        if ("admin".equals(SecurityUtils.getUsername()) || rolePermissions) {
            dahcProjectTables = projectTableMapper.queryThePersonalProjectTree(null);
        } else {
            dahcProjectTables = projectTableMapper.queryThePersonalProjectTree(SecurityUtils.getUserId());
        }

        for (DahcProjectTable dahcProjectTable : dahcProjectTables) {
            List<DahcProjectProcedure> procedureList = new ArrayList<>();
            if ("admin".equals(SecurityUtils.getUsername()) || rolePermissions) {
                procedureList = projectProcedureMapper.queryThePersonalTaskTree(null, dahcProjectTable.getId());
            } else {
                procedureList = projectProcedureMapper.queryThePersonalTaskTree(SecurityUtils.getUserId(), dahcProjectTable.getId());
            }
            dahcProjectTable.setProjectAndProcessState(0);
            dahcProjectTable.setChildren(procedureList);
        }
        return CommonResult.success(dahcProjectTables);
    }

    /**
     * 查询项目批次 tree树
     *
     * @return
     * @author MCY
     * @date 2023/3/30 12:25
     */
    @Override
    public CommonResult queryArchiveBatchData(DahcProjectTable dahcProjectTable) {
        /*根据项目查询表名称*/
        DahcArchiveType archiveType = projectTableMapper.queryTableNamesBasedOnItems(dahcProjectTable.getArchiveTypeId());
        AssertUtil.isTrueServiceInvoke(archiveType != null, CommonStatus.ERROR, "未找到档案类型");
        List<Map<String, Object>> maps = projectTableMapper.queryArchiveBatchData(archiveType.getTableLevel1En(), dahcProjectTable.getId());
        return CommonResult.success(maps);
    }

    /**
     * 根据用户查询项目数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/24 10:15
     */
    @Override
    public CommonResult queryUserProject() {

        List<DahcProjectTable> dahcProjectTables = new ArrayList<>();
        Boolean rolePermissions = dahcSysDictDataService.getRolePermissions(SecurityUtils.getUserId());
        /*判断是否是admin用户*/
        if (SecurityUtils.getUsername().equals("admin") || rolePermissions) {
            dahcProjectTables = projectTableMapper.queryProjectPullDown();
        } else {
            dahcProjectTables = projectTableMapper.queryUserProject(String.valueOf(SecurityUtils.getUserId()));
        }
        return CommonResult.success(dahcProjectTables);
    }

    /**
     * list 去重
     *
     * @param list
     * @return
     */
    public static List<DahcProjectTable> delRepeat(List<DahcProjectTable> list) {
        List<DahcProjectTable> myList = list.stream().distinct().collect(Collectors.toList());
        return myList;
    }


}
