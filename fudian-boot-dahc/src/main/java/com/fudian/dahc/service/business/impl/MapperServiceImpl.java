package com.fudian.dahc.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fudian.dahc.mapper.business.DahcMapperMapper;
import com.fudian.dahc.pojo.dto.DahcModelAndMetadataDto;
import com.fudian.dahc.pojo.entity.business.DahcBusinessMapper;
import com.fudian.dahc.pojo.entity.business.DahcModel;
import com.fudian.dahc.pojo.query.MapperQuery;
import com.fudian.dahc.service.base.impl.MyBaseServiceImpl;
import com.fudian.dahc.service.business.MapperService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 2023/2/7
 */
@Service
public class MapperServiceImpl extends MyBaseServiceImpl<DahcBusinessMapper> implements MapperService {

    @Autowired
    private DahcMapperMapper dahcMapperMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List<DahcBusinessMapper> searchPage(MapperQuery mapperQuery) {
        LambdaQueryWrapper<DahcBusinessMapper> lambda = new LambdaQueryWrapper<>();
        // 如果有条件，就按照条件查询
        if (!StringUtils.isEmpty(mapperQuery.getMapperName())) {
            lambda.like(DahcBusinessMapper::getMetadataName, mapperQuery.getMapperName());
        }
        if (!StringUtils.isEmpty(mapperQuery.getModelId())) {
            lambda.eq(DahcBusinessMapper::getModelId, mapperQuery.getModelId());
        }

        if (Objects.nonNull(mapperQuery.getStartTime())) {
            lambda.between(DahcBusinessMapper::getCreateTime, mapperQuery.getStartTime(), mapperQuery.getEndTime());
        }

        // 排序(倒序)
        //lambda.orderByDesc(DahcBusinessMapper::getId);

        // 没有条件，就查询全部
        return dahcMapperMapper.selectList(lambda);
    }

    @Override
    public int add(DahcBusinessMapper dahcBusinessMapper) {
        int i = dahcMapperMapper.selectCountDuplicateChecking(dahcBusinessMapper);
        if (i > 0) {
            return -1;
        }
        return super.add(dahcBusinessMapper);
    }

    @Override
    public int update(DahcBusinessMapper dahcBusinessMapper) {
        int i = dahcMapperMapper.selectCountDuplicateChecking(dahcBusinessMapper);
        if (i > 0) {
            return -1;
        }
        return super.update(dahcBusinessMapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int bulkAdditions(DahcModelAndMetadataDto andMetadataDto) {
        dahcMapperMapper.deleteByModelId(andMetadataDto.getId());
        andMetadataDto.getDahcBusinessMapperList().forEach(a -> a.setId(IdWorker.getIdStr()));
        return dahcMapperMapper.bulkAdditions(andMetadataDto);
    }

}
