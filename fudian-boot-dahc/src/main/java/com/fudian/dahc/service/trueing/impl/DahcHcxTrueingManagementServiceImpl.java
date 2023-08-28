package com.fudian.dahc.service.trueing.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.utils.DateUtils;
import com.fudian.common.utils.uuid.IdUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.mapper.project.ProjectTableMapper;
import com.fudian.dahc.mapper.trueingManage.DahcHcxTrueingManagementMapper;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;
import com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement;
import com.fudian.dahc.pojo.entity.trueingManage.ProcedureInspectDto;
import com.fudian.dahc.pojo.query.ProcedureInspectQuery;
import com.fudian.dahc.service.trueing.IDahcHcxTrueingManagementService;
import com.fudian.dahc.service.trueing.IDahcHcxTrueingStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fudian
 * @date 2023-02-09
 */
@Service
public class DahcHcxTrueingManagementServiceImpl extends ServiceImpl<DahcHcxTrueingManagementMapper, DahcHcxTrueingManagement> implements IDahcHcxTrueingManagementService {
    @Autowired
    private DahcHcxTrueingManagementMapper dahcHcxTrueingManagementMapper;

    @Autowired
    private IDahcHcxTrueingStandardService dahcHcxTrueingStandardService;

    @Autowired
    private ProjectTableMapper projectTableMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public DahcHcxTrueingManagement selectDahcHcxTrueingManagementById(Long id) {
        return dahcHcxTrueingManagementMapper.selectDahcHcxTrueingManagementById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcHcxTrueingManagement 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DahcHcxTrueingManagement> selectDahcHcxTrueingManagementList(DahcHcxTrueingManagement dahcHcxTrueingManagement) {
        List<DahcHcxTrueingManagement> dahcHcxTrueingManagements = new ArrayList<>();
        if (dahcHcxTrueingManagement.getTransitionIds() != null) {
            /*查询一级及二级节点*/
            dahcHcxTrueingManagements = dahcHcxTrueingManagementMapper.selectTrueingScopeStairList(dahcHcxTrueingManagement);
        } else {
            /*查询三级节点*/
            dahcHcxTrueingManagements = dahcHcxTrueingManagementMapper.selectDahcHcxTrueingManagementList(dahcHcxTrueingManagement);
        }
        for (DahcHcxTrueingManagement hcxTrueingManagement : dahcHcxTrueingManagements) {
            /*拼接核查标准*/
            hcxTrueingManagement.setExamineStasString(dahcHcxTrueingStandardService.queryById(hcxTrueingManagement.getId()));
        }
        return dahcHcxTrueingManagements;
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param dahcHcxTrueingManagement 【请填写功能名称】
     * @return 结果
     */
    @Override
    @Transactional
    public CommonResult insertDahcHcxTrueingManagement(DahcHcxTrueingManagement dahcHcxTrueingManagement) {
        try {
            List<DahcHcxTrueingManagement> managements = this.list(Wrappers.<DahcHcxTrueingManagement>lambdaQuery()
                    .eq(DahcHcxTrueingManagement::getTrueingName, dahcHcxTrueingManagement.getTrueingName())
                    .eq(DahcHcxTrueingManagement::getTrueingScopeStair, dahcHcxTrueingManagement.getTrueingScopeStair()));
            if (managements.size() > 0) {
                return CommonResult.error("重复核查项名称");
            }
            String uuid = IdUtils.fastSimpleUUID();
            dahcHcxTrueingManagement.setId(uuid);
            dahcHcxTrueingManagement.setCreateTime(DateUtils.getNowDate());
            int i = dahcHcxTrueingManagementMapper.insertDahcHcxTrueingManagement(dahcHcxTrueingManagement);
            if (i > 0) {
                List<String> list = new ArrayList<>();
                List<Map<Object, String>> examineStas = dahcHcxTrueingManagement.getExamineStas();
                if (examineStas.size() > 0) {
                    for (Map<Object, String> examineSta : examineStas) {
                        list.add(examineSta.get("value"));
                    }
                    /*新增核查标准*/
                    Boolean aBoolean = dahcHcxTrueingStandardService.addTrueingStandard(list, uuid);
                    if (aBoolean) {
                        return CommonResult.success("新增成功");
                    } else {
                        return CommonResult.error("新增失败");
                    }
                }
                return CommonResult.success("新增成功");
            }
            return CommonResult.error("新增失败");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(e.getMessage());
        }
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcHcxTrueingManagement 【请填写功能名称】
     * @return 结果
     */
    @Override
    @Transactional
    public CommonResult updateDahcHcxTrueingManagement(DahcHcxTrueingManagement dahcHcxTrueingManagement) {
        /*删除工序和核查项绑定关系*/
        String[] strings = {dahcHcxTrueingManagement.getId()};
        List<RecordTrueingDto> dahcProjectTables = projectTableMapper.queryProjectState(strings);
        if (dahcProjectTables.size() > 0) {
            for (RecordTrueingDto dto : dahcProjectTables) {
                throw new RuntimeException("核查项存在关联工序，无法编辑。项目名称为：" + dto.getProjectName() + "，工序名称为：" + dto.getProcedureName());
            }
        }
        List<DahcHcxTrueingManagement> managements = this.list(Wrappers.<DahcHcxTrueingManagement>lambdaQuery()
                .eq(DahcHcxTrueingManagement::getTrueingName, dahcHcxTrueingManagement.getTrueingName())
                .eq(DahcHcxTrueingManagement::getTrueingScopeStair, dahcHcxTrueingManagement.getTrueingScopeStair())
                .ne(DahcHcxTrueingManagement::getId, dahcHcxTrueingManagement.getId()));
        if (managements.size() > 0) {
            return CommonResult.error("重复核查项名称");
        }
        dahcHcxTrueingManagement.setUpdateTime(DateUtils.getNowDate());
        int i = dahcHcxTrueingManagementMapper.updateDahcHcxTrueingManagement(dahcHcxTrueingManagement);
        if (i > 0) {
            /*删除关联数据*/
            Boolean aBoolean = dahcHcxTrueingStandardService.deleteById(dahcHcxTrueingManagement.getId());
            if (aBoolean) {
                /*新增数据*/
                List<String> list = new ArrayList<>();
                List<Map<Object, String>> examineStas = dahcHcxTrueingManagement.getExamineStas();
                if (examineStas.size() > 0) {
                    for (Map<Object, String> examineSta : examineStas) {
                        list.add(examineSta.get("value"));
                    }
                    /*新增核查标准*/
                    Boolean boolean1 = dahcHcxTrueingStandardService.addTrueingStandard(list, dahcHcxTrueingManagement.getId());
                    if (boolean1) {
                        return CommonResult.success("修改成功");
                    } else {
                        return CommonResult.error("修改失败");
                    }
                }
            } else {
                return CommonResult.error("修改失败");
            }
        } else {
            return CommonResult.error("修改失败");
        }
        return CommonResult.error("修改失败");

    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    @Transactional
    public CommonResult deleteDahcHcxTrueingManagementByIds(String[] ids) {
        try {
            /*删除关联数据*/
            for (String id : ids) {
                /*删除核查标准*/
                dahcHcxTrueingStandardService.deleteById(id);
            }
            /*删除工序和核查项绑定关系*/
            List<RecordTrueingDto> dahcProjectTables = projectTableMapper.queryProjectState(ids);
            if (dahcProjectTables.size() > 0) {
                for (RecordTrueingDto dto : dahcProjectTables) {
                    throw new RuntimeException("核查项存在关联工序，无法删除。项目名称为：" + dto.getProjectName() + "，工序名称为：" + dto.getProcedureName());
                }
            }
            int i = dahcHcxTrueingManagementMapper.deleteDahcHcxTrueingManagementByIds(ids);
            if (i > 0) {
                return CommonResult.success("删除成功");
            } else {
                return CommonResult.error("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 根据工序和项目查询核查项数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/20 18:05
     */
    @Override
    public CommonResult queryProcedureInspect(ProcedureInspectQuery procedureInspectQuery) {
        List<ProcedureInspectDto> procedureInspectDtos = dahcHcxTrueingManagementMapper.queryProcedureInspect(procedureInspectQuery);
        return CommonResult.success(procedureInspectDtos);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDahcHcxTrueingManagementById(Long id) {
        return dahcHcxTrueingManagementMapper.deleteDahcHcxTrueingManagementById(id);
    }

    /**
     * 根据字典id删除关联的核查项数据
     *
     * @return int
     * @author MCY
     * @date 2023/2/10 14:37
     */
    @Override
    @Transactional
    public Boolean deleteDictDataById(String trueingScopeStairId) {
        Boolean remove = false;

        /*删除关联数据*/
        List<DahcHcxTrueingManagement> list = this.list(Wrappers.<DahcHcxTrueingManagement>lambdaQuery().eq(DahcHcxTrueingManagement::getTrueingScopeStair, trueingScopeStairId));
        if (list.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (DahcHcxTrueingManagement dahcHcxTrueingManagement : list) {
                ids.add(dahcHcxTrueingManagement.getId());
            }
            for (String id : ids) {
                remove = dahcHcxTrueingStandardService.deleteById(id);
            }
        }
        /*删除核查项数据*/
        if (remove) {
            remove = this.remove(Wrappers.<DahcHcxTrueingManagement>lambdaQuery().eq(DahcHcxTrueingManagement::getTrueingScopeStair, trueingScopeStairId));
        }
        return remove;
    }

    /**
     * 根据id集合查询数据
     *
     * @return java.util.List<com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement>
     * @author MCY
     * @date 2023/2/13 18:28
     */
    @Override
    public List<DahcHcxTrueingManagement> queryProcedureTrueing(List<String> trueingIds, Boolean b, String trueingId) {
        List<DahcHcxTrueingManagement> dahcHcxTrueingManagements = new ArrayList<>();
        /*b等于true，查询工序为选择的数据*/
        if (b) {
            dahcHcxTrueingManagements = this.list(Wrappers.<DahcHcxTrueingManagement>lambdaQuery().in(DahcHcxTrueingManagement::getId, trueingIds));
        } else {
            /*b等于fales，查询工序为未选择的数据*/
            dahcHcxTrueingManagements = this.list(Wrappers.<DahcHcxTrueingManagement>lambdaQuery().notIn(DahcHcxTrueingManagement::getId, trueingIds)
                    .eq(DahcHcxTrueingManagement::getTrueingScopeStair, trueingId));
        }
        if (dahcHcxTrueingManagements.size() > 0) {
            for (DahcHcxTrueingManagement dahcHcxTrueingManagement : dahcHcxTrueingManagements) {
                /*拼接核查标准*/
                dahcHcxTrueingManagement.setExamineStasString(dahcHcxTrueingStandardService.queryById(dahcHcxTrueingManagement.getId()));
            }
        }
        return dahcHcxTrueingManagements;
    }

    /**
     * 根据集合id查询数据
     *
     * @return java.util.List<com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement>
     * @author MCY
     * @date 2023/2/14 11:59
     */
    @Override
    public List<DahcHcxTrueingManagement> queryProcedureTrueingIds(List<String> ids) {
        if (ids.size() > 0) {
            return this.list(Wrappers.<DahcHcxTrueingManagement>lambdaQuery().in(DahcHcxTrueingManagement::getId, ids));
        }
        return new ArrayList<DahcHcxTrueingManagement>();
    }
}
