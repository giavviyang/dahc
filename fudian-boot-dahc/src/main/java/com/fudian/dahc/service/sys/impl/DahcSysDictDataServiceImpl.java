package com.fudian.dahc.service.sys.impl;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.DateUtils;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.uuid.IdUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.mapper.business.DahcMetadataMapper;
import com.fudian.dahc.mapper.sys.DahcSysDictDataMapper;
import com.fudian.dahc.pojo.entity.business.DahcMetadata;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictData;
import com.fudian.dahc.service.trueing.IDahcHcxTrueingManagementService;
import com.fudian.dahc.service.sys.IDahcSysDictDataService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fudian
 * @date 2023-02-08
 */
@Service
public class DahcSysDictDataServiceImpl extends ServiceImpl<DahcSysDictDataMapper, DahcSysDictData> implements IDahcSysDictDataService {
    @Autowired
    private DahcSysDictDataMapper dahcSysDictDataMapper;

    @Autowired
    private IDahcHcxTrueingManagementService dahcHcxTrueingManagementService;

    @Autowired
    private DahcMetadataMapper dahcMetadataMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public DahcSysDictData selectDahcSysDictDataById(String id) {
        return dahcSysDictDataMapper.selectDahcSysDictDataById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcSysDictData 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public CommonGridResult selectDahcSysDictDataList(DahcSysDictData dahcSysDictData) {
        /*查询一级节点数据*/
        LambdaQueryWrapper<DahcSysDictData> queryWrapperFirst = new LambdaQueryWrapper<>();
        queryWrapperFirst.eq(DahcSysDictData::getType, "0");
        //queryWrapperFirst.orderByDesc(DahcSysDictData::getCreateTime);
        if (StringUtils.isNotEmpty(dahcSysDictData.getFullName())) {
            queryWrapperFirst.like(DahcSysDictData::getFullName, dahcSysDictData.getFullName());
        }
        if (StringUtils.isNotEmpty(dahcSysDictData.getDictType())) {
            queryWrapperFirst.eq(DahcSysDictData::getDictType, dahcSysDictData.getDictType());
        }
        PageHelper.startPage(dahcSysDictData.getPageNum(), dahcSysDictData.getPageSize());
        List<DahcSysDictData> list = this.list(queryWrapperFirst);
        for (DahcSysDictData sysDictData : list) {
            LambdaQueryWrapper<DahcSysDictData> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DahcSysDictData::getEparentCode, sysDictData.getId()).eq(DahcSysDictData::getType, "1");
            if (StringUtils.isNotEmpty(dahcSysDictData.getDictType())) {
                queryWrapper.eq(DahcSysDictData::getDictType, dahcSysDictData.getDictType());
            }
            List<DahcSysDictData> listSecondLevel = this.list(queryWrapper);
            sysDictData.setChildren(listSecondLevel);
        }
        Page page = (Page) list;
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    @Override
    public CommonGridResult selectDahcSysDictDataListAll(DahcSysDictData dahcSysDictData) {
        /*查询一级节点数据*/
        LambdaQueryWrapper<DahcSysDictData> queryWrapperFirst = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(dahcSysDictData.getFullName())) {
            queryWrapperFirst.like(DahcSysDictData::getFullName, dahcSysDictData.getFullName());
        }
        queryWrapperFirst.eq(DahcSysDictData::getType, "0");
        PageHelper.startPage(dahcSysDictData.getPageNum(), dahcSysDictData.getPageSize());
        List<DahcSysDictData> list = this.list(queryWrapperFirst);
        for (DahcSysDictData sysDictData : list) {
            LambdaQueryWrapper<DahcSysDictData> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DahcSysDictData::getEparentCode, sysDictData.getId()).eq(DahcSysDictData::getType, "1");
            if (StringUtils.isNotEmpty(dahcSysDictData.getDictType())) {
                queryWrapper.eq(DahcSysDictData::getDictType, dahcSysDictData.getDictType());
            }
            List<DahcSysDictData> listSecondLevel = this.list(queryWrapper);
            sysDictData.setChildren(listSecondLevel);
        }
        Page page = (Page) list;
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    /**
     * 核查项tree查询
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/9 10:44
     */
    @Override
    public CommonResult queryInspectTree(String dictTypeId) {
        try {
            /*获取一级节点数据*/
            List<DahcSysDictData> listFirst = this.list(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getType, 0).eq(DahcSysDictData::getDictType, dictTypeId));
            if (listFirst.size() > 0) {
                /*返回的dataMap*/
                List<Map<String, Object>> returnMap = new ArrayList<>();
                for (DahcSysDictData dahcSysDictData : listFirst) {
                    Map<String, Object> mapFirst = new HashMap<>();
                    mapFirst.put("label", dahcSysDictData.getFullName());
                    mapFirst.put("value", dahcSysDictData.getId());
                    mapFirst.put("type", dahcSysDictData.getType());
                    mapFirst.put("codeProperty", dahcSysDictData.getCodeProperty());
                    mapFirst.put("children", secondNodeMap(dahcSysDictData, 1));
                    returnMap.add(mapFirst);
                }
                return CommonResult.success(returnMap);
            }
            return CommonResult.error("没有查询到数据");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(e.getMessage());
        }
    }

    /**
     * 获取二级节点数据
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author MCY
     * @date 2023/2/9 10:59
     */
    private List<Map<String, Object>> secondNodeMap(DahcSysDictData dahcSysDictData, Integer type) {
        List<DahcSysDictData> list = this.list(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getType, type).eq(DahcSysDictData::getEparentCode, dahcSysDictData.getId()));
        List<Map<String, Object>> returnMap = new ArrayList<>();
        if (list.size() > 0) {
            for (DahcSysDictData sysDictData : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("label", sysDictData.getFullName());
                map.put("value", sysDictData.getId());
                map.put("type", sysDictData.getType());
                map.put("codeProperty", sysDictData.getCodeProperty());
                map.put("children", threeLeveLnodeMap(sysDictData, 2));
                returnMap.add(map);
            }
        }
        return returnMap;
    }

    /**
     * 获取三级节点数据
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author MCY
     * @date 2023/2/9 10:59
     */
    private List<Map<String, Object>> threeLeveLnodeMap(DahcSysDictData dahcSysDictData, Integer type) {
        List<DahcSysDictData> list = this.list(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getType, type).eq(DahcSysDictData::getEparentCode, dahcSysDictData.getId()));
        List<Map<String, Object>> returnMap = new ArrayList<>();
        if (list.size() > 0) {
            for (DahcSysDictData sysDictData : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("label", sysDictData.getFullName());
                map.put("value", sysDictData.getId());
                map.put("type", sysDictData.getType());
                map.put("codeProperty", sysDictData.getCodeProperty());
                returnMap.add(map);
            }
        }
        return returnMap;
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param dahcSysDictData 【请填写功能名称】
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDahcSysDictData(DahcSysDictData dahcSysDictData) {
        /*新增档案类型默认数据*/
        if (StringUtils.isNotEmpty(dahcSysDictData.getCodeProperty())) {
            if (dahcSysDictData.getDictType().equals("101") || dahcSysDictData.getDictType().equals("102")) {
                DahcMetadata dahcMetadata = dahcMetadataMapper.selectOne(Wrappers.<DahcMetadata>lambdaQuery().eq(DahcMetadata::getMetadataName, dahcSysDictData.getCodeProperty()));
                if (dahcMetadata == null) {
                    throw new RuntimeException("未找到对应的元数据名称");
                }
                dahcSysDictData.setCodeProperty(dahcMetadata.getId());
            }
        } else {
            throw new RuntimeException("请输入代码属性");
        }
        List<DahcSysDictData> sysDictData = dahcSysDictDataMapper.selectList(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getEparentCode, dahcSysDictData.getEparentCode()).eq(DahcSysDictData::getFullName, dahcSysDictData.getFullName()));
        if (sysDictData.size() > 0) {
            throw new RuntimeException("数据重复");
        }
        dahcSysDictData.setId(IdUtils.fastSimpleUUID());
        dahcSysDictData.setCodeAttr(1l);
        dahcSysDictData.setCreateBy(SecurityUtils.getUsername());
        dahcSysDictData.setCreateTime(DateUtils.getNowDate());
        return dahcSysDictDataMapper.insertDahcSysDictData(dahcSysDictData);
    }
    @Override
    @Transactional
    public int auditManagementAdd(DahcSysDictData dahcSysDictData) {
        List<DahcSysDictData> sysDictData = dahcSysDictDataMapper.selectList(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getEparentCode, dahcSysDictData.getEparentCode()).eq(DahcSysDictData::getFullName, dahcSysDictData.getFullName()));
        if (sysDictData.size() > 0) {
            throw new RuntimeException("数据重复");
        }
        dahcSysDictData.setId(IdUtils.fastSimpleUUID());
        dahcSysDictData.setCodeAttr(1l);
        dahcSysDictData.setCreateBy(SecurityUtils.getUsername());
        dahcSysDictData.setCreateTime(DateUtils.getNowDate());
        return dahcSysDictDataMapper.insertDahcSysDictData(dahcSysDictData);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcSysDictData 【请填写功能名称】
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDahcSysDictData(DahcSysDictData dahcSysDictData) {
        List<DahcSysDictData> sysDictData = dahcSysDictDataMapper.selectList(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getEparentCode, dahcSysDictData.getEparentCode())
                .eq(DahcSysDictData::getFullName, dahcSysDictData.getFullName()).ne(DahcSysDictData::getId,dahcSysDictData.getId()));
        if (sysDictData.size() > 0) {
            throw new RuntimeException("数据重复");
        }
        dahcSysDictData.setUpdateTime(DateUtils.getNowDate());
        return dahcSysDictDataMapper.updateDahcSysDictData(dahcSysDictData);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    @Transactional
    public CommonResult deleteDahcSysDictDataByIds(String[] ids) {


        List<DahcSysDictData> sysDictData = dahcSysDictDataMapper.selectList(Wrappers.<DahcSysDictData>lambdaQuery().in(DahcSysDictData::getId, ids));
        if (sysDictData.size() > 0) {
            for (DahcSysDictData sysDictDatum : sysDictData) {
                if (sysDictDatum.getCodeAttr()==0) {
                    return CommonResult.error(sysDictDatum.getFullName()+"为系统默认数据，无法删除");
                }
            }
        }


        List<DahcSysDictData> sysDictData1 = dahcSysDictDataMapper.selectList(Wrappers.<DahcSysDictData>lambdaQuery().in(DahcSysDictData::getEparentCode, ids));
        if (sysDictData1.size() > 0) {
            return CommonResult.error("存在名称为："+sysDictData1.get(0).getFullName()+"的字典数据，请先删除子字典数据");
        }


        /*勾选三级节点删除数据*/
        List<String> listIds = Arrays.asList(ids);
        /*查询删除数据状态*/
        List<DahcSysDictData> list = this.list(Wrappers.<DahcSysDictData>lambdaQuery().in(DahcSysDictData::getId, listIds).eq(DahcSysDictData::getType, 2));
        try {
            if (list.size() > 0) {
                for (DahcSysDictData dahcSysDictData : list) {
                    /*删除三级节点*/
                    if (dahcSysDictData.getType() == 2) {
                        int i = dahcSysDictDataMapper.deleteDahcSysDictDataByIds(ids);
                        if (i > 0) {
                            /*删除关联的核查项数据*/
                             dahcHcxTrueingManagementService.deleteDictDataById(dahcSysDictData.getId());
                                return CommonResult.success("删除成功");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error("删除失败");
        }

        /*勾选一级节点删除数据*/
        /*查询二级节点数据*/
        List<DahcSysDictData> listFirst = this.list(Wrappers.<DahcSysDictData>lambdaQuery().in(DahcSysDictData::getEparentCode, listIds).eq(DahcSysDictData::getType, 1));
        if (listFirst.size() > 0) {
            /*二级节点id集合*/
            List<String> listSecondLevelIds = new ArrayList<>();
            /*三级级节点id集合*/
            List<String> listThreeLevellIds = new ArrayList<>();
            for (DahcSysDictData dahcSysDictData : listFirst) {
                listSecondLevelIds.add(dahcSysDictData.getId());
            }
            /*查询三级节点数据*/
            List<DahcSysDictData> listThreeLevell = this.list(Wrappers.<DahcSysDictData>lambdaQuery().in(DahcSysDictData::getEparentCode, listSecondLevelIds).eq(DahcSysDictData::getType, 2));
            if (listThreeLevell.size() > 0) {
                for (DahcSysDictData dahcSysDictData : listThreeLevell) {
                    listThreeLevellIds.add(dahcSysDictData.getId());
                    /*删除关联的核查项数据*/
                    Boolean aBoolean = dahcHcxTrueingManagementService.deleteDictDataById(dahcSysDictData.getId());
                }
            }
            /*删除三级级节点数据*/
            boolean remove = false;
            if (listThreeLevellIds.size() > 0) {
                remove = this.remove(Wrappers.<DahcSysDictData>lambdaQuery().in(DahcSysDictData::getId, listThreeLevellIds));
            }
            int i = 0;
            /*删除二级级节点数据*/
            if (listSecondLevelIds.size() > 0) {
                i = dahcSysDictDataMapper.deleteDahcSysDictDataByIds(new String[listSecondLevelIds.size()]);
            }
            /*删除一级级节点数据*/
            //i = dahcSysDictDataMapper.deleteDahcSysDictDataByIds(ids);
        }
        List<DahcSysDictData> list1 = this.list(Wrappers.<DahcSysDictData>lambdaQuery().in(DahcSysDictData::getId, listIds).eq(DahcSysDictData::getType, 0));
        if (list1.size() > 0) {
            int i = dahcSysDictDataMapper.deleteDahcSysDictDataByIds(ids);
            if (i > 0) {
                return CommonResult.success("删除成功");
            } else {
                return CommonResult.error("删除失败");
            }
        }

        /*勾选二级节点删除数据*/
        /*查询三级级节点数据*/
        List<DahcSysDictData> listThreeLevell = this.list(Wrappers.<DahcSysDictData>lambdaQuery().in(DahcSysDictData::getEparentCode, listIds).eq(DahcSysDictData::getType, 2));
        if (listThreeLevell.size() > 0) {
            /*三级级节点id集合*/
            List<String> listThreeLevellIds = new ArrayList<>();
            for (DahcSysDictData dahcSysDictData : listThreeLevell) {
                listThreeLevellIds.add(dahcSysDictData.getId());
                /*删除关联的核查项数据*/
                Boolean aBoolean = dahcHcxTrueingManagementService.deleteDictDataById(dahcSysDictData.getId());
            }
            /*删除三级节点数据*/
            Boolean remove = this.remove(Wrappers.<DahcSysDictData>lambdaQuery().in(DahcSysDictData::getId, listThreeLevellIds));
            /*删除二级级节点数据*/
            int i = dahcSysDictDataMapper.deleteDahcSysDictDataByIds(ids);
            if (i > 0) {
                return CommonResult.success("删除成功");
            } else {
                return CommonResult.error("删除失败");
            }
        } else {
            List<DahcSysDictData> list11 = this.list(Wrappers.<DahcSysDictData>lambdaQuery().in(DahcSysDictData::getId, listIds).eq(DahcSysDictData::getType, 1));
            if (list11.size() > 0) {
                int i = dahcSysDictDataMapper.deleteDahcSysDictDataByIds(ids);
                if (i > 0) {
                    return CommonResult.success("删除成功");
                } else {
                    return CommonResult.error("删除失败");
                }
            }
        }
        return CommonResult.error("删除失败");
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDahcSysDictDataById(String id) {
        return dahcSysDictDataMapper.deleteDahcSysDictDataById(id);
    }

    @Override
    public List<Map<String, String>> queryDictDataTransition() {
        List<Map<String, String>> listMap = null;
        try {
            List<DahcSysDictData> list = this.list(Wrappers.<DahcSysDictData>lambdaQuery()
                    .eq(DahcSysDictData::getType, 2).eq(DahcSysDictData::getDictType, "100"));
            listMap = new ArrayList<>();
            for (DahcSysDictData dahcSysDictData : list) {
                Map<String, String> map = new HashMap<>();
                map.put("label", dahcSysDictData.getFullName());
                map.put("value", dahcSysDictData.getId());
                listMap.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        }
        return listMap;
    }

    @Override
    public List<Map<String, String>> queryDictDataTransitionId(String dictTypeId) {
        List<Map<String, String>> listMap = null;
        try {
            List<DahcSysDictData> list = this.list(Wrappers.<DahcSysDictData>lambdaQuery()
                    .eq(DahcSysDictData::getType, 0).eq(DahcSysDictData::getDictType, dictTypeId));
            listMap = new ArrayList<>();
            for (DahcSysDictData dahcSysDictData : list) {
                Map<String, String> map = new HashMap<>();
                map.put("label", dahcSysDictData.getFullName());
                map.put("value", dahcSysDictData.getCodeProperty());
                listMap.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        }
        return listMap;
    }

    /**
     *根据工序的字典id获取字典属性
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/24 13:56
     */
    @Override
    public CommonResult queryProcedureData(String dictDataId) {
        String eparentCode = this.getOne(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getId, dictDataId)).getEparentCode();
        String codeProperty = this.getOne(Wrappers.<DahcSysDictData>lambdaQuery()
                .eq(DahcSysDictData::getId, eparentCode)
                .eq(DahcSysDictData::getType, 1)).getCodeProperty();
        return CommonResult.success(codeProperty);
    }

    @Override
    public Boolean getRolePermissions(Long userId) {
        int rolePermissions = dahcSysDictDataMapper.getRolePermissions(userId);
        if (rolePermissions > 0) {
            return true;
        }
        return false;
    }
}
