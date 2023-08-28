package com.fudian.dahc.service.sys.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.utils.DateUtils;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.common.utils.uuid.IdUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.mapper.sys.DahcSysDictDataMapper;
import com.fudian.dahc.mapper.sys.DahcSysDictTypeMapper;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictData;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictType;
import com.fudian.dahc.service.sys.IDahcSysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 字典类型Service业务层处理
 *
 * @author fudian
 * @date 2023-02-08
 */
@Service
public class DahcSysDictTypeServiceImpl  extends ServiceImpl<DahcSysDictTypeMapper,DahcSysDictType> implements IDahcSysDictTypeService
{
    @Autowired
    private DahcSysDictTypeMapper dahcSysDictTypeMapper;
    @Autowired
    private DahcSysDictDataMapper dahcSysDictDataMapper;
    /**
     * 查询字典类型
     *
     * @param dictId 字典类型主键
     * @return 字典类型
     */
    @Override
    public DahcSysDictType selectDahcSysDictTypeByDictId(Long dictId)
    {
        return dahcSysDictTypeMapper.selectDahcSysDictTypeByDictId(dictId);
    }

    /**
     * 查询字典类型列表
     *
     * @return 字典类型
     */
    @Override
    public CommonResult selectDahcSysDictTypeList()
    {
        try {
            DahcSysDictType dahcSysDictType = new DahcSysDictType();
            List<DahcSysDictType> dahcSysDictTypes = dahcSysDictTypeMapper.selectDahcSysDictTypeList(dahcSysDictType);
            if (dahcSysDictTypes.size() > 0) {
                List<Map<String, Object>> maps = new ArrayList<>();
                for (DahcSysDictType sysDictType : dahcSysDictTypes) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("label", sysDictType.getDictName());
                    map.put("value", sysDictType.getDictId());
                    map.put("remark", sysDictType.getRemark());
                    maps.add(map);
                }
                return CommonResult.success(maps);
            }
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(e.getMessage());
        }
    }

    /**
     * 新增字典类型
     *
     * @param dahcSysDictType 字典类型
     * @return 结果
     */
    @Override
    public int insertDahcSysDictType(DahcSysDictType dahcSysDictType)
    {
        /*查重*/
        List<DahcSysDictType> dahcSysDictTypes = dahcSysDictTypeMapper.selectList(Wrappers.<DahcSysDictType>lambdaQuery().eq(DahcSysDictType::getDictName, dahcSysDictType.getDictName()));
        if (dahcSysDictTypes.size() > 0) {
            throw new RuntimeException("档案类型名称重复");
        }
        dahcSysDictType.setDictId(IdUtils.fastSimpleUUID());
        dahcSysDictType.setCreateTime(DateUtils.getNowDate());
        dahcSysDictType.setCreateBy(SecurityUtils.getUsername());
        dahcSysDictType.setStatus("1");
        return dahcSysDictTypeMapper.insertDahcSysDictType(dahcSysDictType);
    }

    /**
     * 修改字典类型
     *
     * @param dahcSysDictType 字典类型
     * @return 结果
     */
    @Override
    public int updateDahcSysDictType(DahcSysDictType dahcSysDictType)
    {

        /*查重*/
        List<DahcSysDictType> dahcSysDictTypes = dahcSysDictTypeMapper.selectList(Wrappers.<DahcSysDictType>lambdaQuery()
                .eq(DahcSysDictType::getDictName, dahcSysDictType.getDictName()).ne(DahcSysDictType::getDictId,dahcSysDictType.getDictId()));
        if (dahcSysDictTypes.size() > 0) {
            throw new RuntimeException("档案类型名称重复");
        }

        dahcSysDictType.setUpdateBy(SecurityUtils.getUsername());
        dahcSysDictType.setUpdateTime(DateUtils.getNowDate());
        return dahcSysDictTypeMapper.updateDahcSysDictType(dahcSysDictType);
    }

    /**
     * 批量删除字典类型
     *
     * @param dictIds 需要删除的字典类型主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDahcSysDictTypeByDictIds(String dictIds)
    {
        /*判断是否可删除*/
        String status = dahcSysDictTypeMapper.selectOne(Wrappers.<DahcSysDictType>lambdaQuery().eq(DahcSysDictType::getDictId, dictIds)).getStatus();
        if (status.equals("0")) {
            throw new RuntimeException("系统默认数据，无法删除");
        }
        List<DahcSysDictData> sysDictData = dahcSysDictDataMapper.selectList(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getDictType, dictIds));
        if (sysDictData.size() > 0) {
            throw new RuntimeException("存在字典数据，请先删除关联的字典数据");
        }
        int i = dahcSysDictTypeMapper.deleteDahcSysDictTypeByDictIds(dictIds);
        /*删除关联字典数据关系*/
        dahcSysDictDataMapper.delete(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getDictType, dictIds));
        return i;
    }

    /**
     *字典转换
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @author MCY
     * @date 2023/2/9 10:13
     */
    @Override
    public List<Map<String, String>> queryDictType() {
        List<Map<String, String>> listMap = null;
        try {
            List<DahcSysDictType> list = this.list(Wrappers.<DahcSysDictType>lambdaQuery());
            listMap = new ArrayList<>();
            for (DahcSysDictType sysDictionary : list) {
                Map<String, String> map = new HashMap<>();
                map.put("label", sysDictionary.getDictName());
                map.put("value", sysDictionary.getDictId());
                listMap.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        }
        return listMap;
    }

    /**
     * 删除字典类型信息
     *
     * @param dictId 字典类型主键
     * @return 结果
     */
    @Override
    public int deleteDahcSysDictTypeByDictId(Long dictId)
    {
        return dahcSysDictTypeMapper.deleteDahcSysDictTypeByDictId(dictId);
    }
}
