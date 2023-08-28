package com.fudian.dahc.service.business.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.CommonStatus;
import com.fudian.dahc.mapper.business.DahcArchiveTypeMapper;
import com.fudian.dahc.mapper.business.DahcMapperMapper;
import com.fudian.dahc.mapper.business.DahcMetadataMapper;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndMetadataVo;
import com.fudian.dahc.pojo.entity.business.DahcBusinessMapper;
import com.fudian.dahc.pojo.entity.business.DahcMetadata;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictData;
import com.fudian.dahc.pojo.query.MetadataQuery;
import com.fudian.dahc.service.base.impl.MyBaseServiceImpl;
import com.fudian.dahc.service.business.MetadataService;
import com.fudian.dahc.service.sys.IDahcSysDictDataService;
import com.fudian.dahc.util.common.AssertUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 2023/2/7
 */
@Service
public class MetadataServiceImpl extends MyBaseServiceImpl<DahcMetadata> implements MetadataService {

    @Autowired
    private DahcMetadataMapper dahcMetadataMapper;

    @Autowired
    private IDahcSysDictDataService sysDictDataService;

    @Autowired
    private DahcMapperMapper dahcBusinessMapper;

    @Override
    public int add(DahcMetadata dahcMetadata) {
        int i = dahcMetadataMapper.selectCountDuplicateChecking(dahcMetadata);
        AssertUtil.isTrueServiceInvoke(i == 0, CommonStatus.METHOD_NOT_ALLOWED, "重复的元数据,请重新添加");
        return super.add(dahcMetadata);
    }

    @Override
    public int update(DahcMetadata dahcMetadata) {
        int i = dahcMetadataMapper.selectCountDuplicateChecking(dahcMetadata);
        AssertUtil.isTrueServiceInvoke(i == 0, CommonStatus.METHOD_NOT_ALLOWED, "重复的元数据,请重新添加");
        return super.update(dahcMetadata);
    }


    @Override
    public List<DahcMetadata> searchPage(MetadataQuery metadataQuery) {
        MPJLambdaWrapper<DahcMetadata> lambda = new MPJLambdaWrapper<>();
        // 如果有条件，就按照条件查询
        lambda.select("t.*,s1.nick_name as createByName,s2.nick_name as updateByName");
        lambda.like(StringUtils.hasText(metadataQuery.getMetadataName()), DahcMetadata::getMetadataName, metadataQuery.getMetadataName());
        lambda.like(StringUtils.hasText(metadataQuery.getMetadataDesc()), DahcMetadata::getMetadataDesc, metadataQuery.getMetadataDesc());
        lambda.eq(StringUtils.hasText(metadataQuery.getMetadataType()), DahcMetadata::getMetadataType, metadataQuery.getMetadataType());
        lambda.between(Objects.nonNull(metadataQuery.getStartTime()), DahcMetadata::getCreateTime, metadataQuery.getStartTime(), metadataQuery.getEndTime());
        lambda.leftJoin("sys_user s1 on  t.create_by =s1.user_id ");
        lambda.leftJoin("sys_user s2 on  t.update_by =s2.user_id ");
        // 排序(倒序)
        lambda.orderByAsc(DahcMetadata::getCreateTime);
        return dahcMetadataMapper.selectList(lambda);
    }

    @Override
    public List<Map<String, Object>> queryMetadataSelect() {

        List<Map<String, Object>> listMap = null;
        try {
            List<DahcMetadata> list = dahcMetadataMapper.queryMetadataSelect();
            listMap = new ArrayList<>();
            for (DahcMetadata dahcMetadata : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("label", dahcMetadata.getMetadataName());
                map.put("value", dahcMetadata.getId());
                map.put("disabled", false);
                map.put("metadataType", dahcMetadata.getMetadataType());
//                map.put("metadataLong", dahcMetadata.getMetadataLong());
                map.put("metadataDesc", dahcMetadata.getMetadataDesc());
                listMap.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        }
        return listMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult deleteMetadataInBulk(List<Long> ids) {
        //List<DahcMetadata> list = this.list(Wrappers.<DahcMetadata>lambdaQuery().in(DahcMetadata::getId, ids));
        /*系统默认字段*/
        List<DahcSysDictData> sysDictData = sysDictDataService.list(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getDictType, "102"));
        for (Long id : ids) {
            for (DahcSysDictData sysDictDatum : sysDictData) {
                if (sysDictDatum.getCodeProperty().equals(String.valueOf(id))) {
                    return CommonResult.error("元数据：" + sysDictDatum.getFullName() + "为系统默认字段不能删除");
                }
            }

            List<DahcArchiveTypeAndMetadataVo> dahcMetadata = dahcMetadataMapper.queryWhetherToAssociateProfileTypeData(id);
            if (dahcMetadata.size() > 0) {
                return CommonResult.error("元数据：" + dahcMetadata.get(0).getMetadataName() +
                        "在档案类型为：" + dahcMetadata.get(0).getArchiveName() + "处引用，请先删除档案类型关联关系");
            }
        }

/*        for (Long id : ids) {

        }*/

        int i = this.batchDeleteByIds(ids);
        if (i > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.error("删除失败");
        }
    }

    @Override
    @Transactional
    public CommonResult updateMetadata(DahcMetadata dahcMetadata) {
        int i = dahcMetadataMapper.selectCountDuplicateChecking(dahcMetadata);
        AssertUtil.isTrueServiceInvoke(i == 0, CommonStatus.METHOD_NOT_ALLOWED, "重复的元数据");
        List<DahcSysDictData> list = sysDictDataService.list(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getDictType, "102"));
        for (DahcSysDictData dahcSysDictData : list) {
            if (!dahcSysDictData.getFullName().equals(dahcMetadata.getMetadataName()) && dahcSysDictData.getCodeProperty().equals(dahcMetadata.getId())) {
                return CommonResult.error("系统默认字段不能修改");
            }
        }
        dahcBusinessMapper.update(new DahcBusinessMapper(), Wrappers.<DahcBusinessMapper>lambdaUpdate()
                .eq(DahcBusinessMapper::getMetadataId, dahcMetadata.getId()).set(DahcBusinessMapper::getMetadataName, dahcMetadata.getMetadataName()));
        int update = super.update(dahcMetadata);
        if (update > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.error("修改失败");
        }
    }

}
