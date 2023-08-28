package com.fudian.dahc.service.business;

import com.fudian.dahc.pojo.dto.DahcModelAndMetadataDto;
import com.fudian.dahc.pojo.entity.business.DahcBusinessMapper;
import com.fudian.dahc.pojo.entity.business.DahcModel;
import com.fudian.dahc.pojo.query.MapperQuery;
import com.fudian.dahc.service.base.MyBaseService;

import java.util.List;

/**
 * 2023/2/7
 */
public interface MapperService extends MyBaseService<DahcBusinessMapper> {

    /**
     * 分页查询
     * @param mapperQuery
     * @return
     */
    List<DahcBusinessMapper> searchPage(MapperQuery mapperQuery);

    /**
     * 批量新增
     * @return
     */
    int bulkAdditions(DahcModelAndMetadataDto andMetadataDto);
}
