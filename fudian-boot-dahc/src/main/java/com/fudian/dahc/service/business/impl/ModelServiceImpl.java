package com.fudian.dahc.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.dahc.common.CommonStatus;
import com.fudian.dahc.mapper.business.DahcMapperMapper;
import com.fudian.dahc.mapper.business.DahcModelMapper;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.business.DahcBusinessMapper;
import com.fudian.dahc.pojo.entity.business.DahcModel;
import com.fudian.dahc.pojo.query.ModelQuery;
import com.fudian.dahc.service.base.impl.MyBaseServiceImpl;
import com.fudian.dahc.service.business.ModelService;
import com.fudian.dahc.util.common.AssertUtil;
import com.github.pagehelper.PageHelper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 2023/2/7
 */
@Service
public class ModelServiceImpl extends MyBaseServiceImpl<DahcModel> implements ModelService {

    @Autowired
    private DahcModelMapper dahcModelMapper;

    @Autowired
    private DahcMapperMapper dahcMapperMapper;

    @Override
    public List<DahcModel> searchPage(ModelQuery modelQuery) {
        MPJLambdaWrapper<DahcModel> lambda = new MPJLambdaWrapper<>();
        lambda.select("t.*,s1.nick_name as createByName,s2.nick_name as updateByName");
        lambda.eq(DahcModel::getArchiveTableLevel, "1");
        lambda.like(StringUtils.hasText(modelQuery.getModelName()), DahcModel::getModelName, modelQuery.getModelName());
        lambda.like(StringUtils.hasText(modelQuery.getModelDesc()), DahcModel::getModelDesc, modelQuery.getModelDesc());
        lambda.between(Objects.nonNull(modelQuery.getStartTime()), DahcModel::getCreateTime, modelQuery.getStartTime(), modelQuery.getEndTime());
        lambda.eq(StringUtils.hasText(modelQuery.getArchiveTypeId()), DahcModel::getArchiveTypeId, modelQuery.getArchiveTypeId());
        lambda.leftJoin("sys_user s1 on  t.create_by =s1.user_id ");
        lambda.leftJoin("sys_user s2 on  t.update_by =s2.user_id ");
        lambda.orderByAsc(DahcModel::getCreateTime);
        // 没有条件，就查询全部
        PageHelper.startPage(modelQuery.getPageNum(), modelQuery.getPageSize());
        List<DahcModel> dahcModels = dahcModelMapper.selectList(lambda);

        MPJLambdaWrapper<DahcModel> lambda2 = new MPJLambdaWrapper<>();
        lambda2.select("t.*,s1.nick_name as createByName,s2.nick_name as updateByName");
        lambda2.eq(DahcModel::getArchiveTableLevel, "2");
        lambda2.leftJoin("sys_user s1 on  t.create_by =s1.user_id ");
        lambda2.leftJoin("sys_user s2 on  t.update_by =s2.user_id ");
        lambda2.orderByAsc(DahcModel::getCreateTime);
        List<DahcModel> dahcModelsAll = dahcModelMapper.selectList(lambda2);
        dahcModels.parallelStream().forEach(a -> {
            List<DahcModel> collect = dahcModelsAll.stream().filter(b -> b.getPid().equals(a.getId())).collect(Collectors.toList());
            a.setDahcModelLv(collect);
        });
        return dahcModels;
    }


    @Override
    public int deleteById(Long id) {
        LambdaQueryWrapper<DahcBusinessMapper> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DahcBusinessMapper::getModelId, String.valueOf(id));
        dahcMapperMapper.delete(wrapper);
        return super.deleteById(id);
    }


    @Override
    public List<DahcModel> selectMapperByModel(String modelId, String archivesId) {
        MPJLambdaWrapper<DahcModel> lambda = new MPJLambdaWrapper<>();
        lambda.selectAll(DahcModel.class);
        lambda.eq(StringUtils.hasText(modelId), DahcModel::getId, modelId);
        if (StringUtils.hasText(archivesId)) {
            lambda.eq(DahcArchiveType::getId, archivesId);
            lambda.rightJoin(DahcArchiveType.class, DahcArchiveType::getId, DahcModel::getArchiveTypeId);
        }
        lambda.selectCollection(DahcBusinessMapper.class, DahcModel::getDahcBusinessMapperList);
        lambda.leftJoin(DahcBusinessMapper.class, DahcBusinessMapper::getModelId, DahcModel::getId);
        return dahcModelMapper.selectJoinList(DahcModel.class, lambda);
    }

    @Override
    public int add(DahcModel model) {
        String modelName = model.getModelName();
        modelName += "0".equals(model.getPid()) ? "-案卷级" : "-文件级";
        model.setModelName(modelName);
        int i = dahcModelMapper.selectCountDuplicateChecking(model);
        AssertUtil.isTrueServiceInvoke(i == 0, CommonStatus.ERROR, "模板名重复,请重新添加");
        return super.add(model);
    }

    @Override
    public int update(DahcModel dahcModel) {
        int i = dahcModelMapper.selectCountDuplicateChecking(dahcModel);
        AssertUtil.isTrueServiceInvoke(i == 0, CommonStatus.ERROR, "重复的模板");
        return super.update(dahcModel);
    }
}
