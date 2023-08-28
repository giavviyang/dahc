package com.fudian.dahc.service.business;

import com.fudian.dahc.pojo.entity.business.DahcModel;
import com.fudian.dahc.pojo.query.ModelQuery;
import com.fudian.dahc.service.base.MyBaseService;

import java.util.List;

/**
 * 2023/2/7
 */
public interface ModelService extends MyBaseService<DahcModel> {

    /**
     * 分页查询
     * @param modelQuery
     * @return
     */
    List<DahcModel> searchPage(ModelQuery modelQuery);

    /**
     * 查询模板及其映射关系
     * @param id 模板id
     * @return
     */
    List<DahcModel> selectMapperByModel(String id,String archivesId);

}
