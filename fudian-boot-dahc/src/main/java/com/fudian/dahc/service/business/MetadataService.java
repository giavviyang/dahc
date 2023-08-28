package com.fudian.dahc.service.business;

import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.business.DahcMetadata;
import com.fudian.dahc.pojo.query.MetadataQuery;
import com.fudian.dahc.service.base.MyBaseService;

import java.util.List;
import java.util.Map;

/**
 * 2023/2/7
 * @author fudian
 */
public interface MetadataService extends MyBaseService<DahcMetadata> {

    /**
     * 分页查询
     * @param metadataQuery
     * @return
     */
    List<DahcMetadata> searchPage(MetadataQuery metadataQuery);

    /**
     *下拉框类型转换
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @author MCY
     * @date 2023/2/15 10:52
     */
    List<Map<String, Object>> queryMetadataSelect();

    CommonResult deleteMetadataInBulk(List<Long> ids);


    /**
     *修改元数据
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/28 15:25
     */
    CommonResult updateMetadata(DahcMetadata dahcMetadata);
}
