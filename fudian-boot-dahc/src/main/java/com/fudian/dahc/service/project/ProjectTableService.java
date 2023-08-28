package com.fudian.dahc.service.project;


import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.project.DahcProjectTable;
import com.fudian.dahc.pojo.query.ProjectTableQuery;
import com.fudian.dahc.service.base.MyBaseService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * 2023/2/7
 */
public interface ProjectTableService extends MyBaseService<DahcProjectTable> {


    List<DahcProjectTable> searchPage(ProjectTableQuery projectTableQuery);

    /**
     * 用于项目下拉框
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author MCY
     * @date 2023/2/16 15:44
     */
    CommonResult queryProjectPullDown();

    /**
     *根据用户查询项目数据
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/24 10:15
     */
    CommonResult queryUserProject();

    /**批量删除
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/20 11:32
     */
    CommonResult batchDeleteByIdsProjectTable(List<Long> ids);
    /**
     *开始核查
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/20 11:47
     */
    CommonResult startInspect(DahcProjectTable dahcProjectTable);

    CommonResult endOfVerification(DahcProjectTable dahcProjectTable);


    /**
     *查询存疑tree树
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/10 10:32
     */
    CommonResult queryTheProjectOperationTree();
    CommonResult queryThePersonalManagementProjectTree();
    CommonResult queryThePersonalTaskTree();

    /**
     *查询项目批次 tree树
     * @return
     * @author MCY
     * @date 2023/3/30 12:25
     */
    CommonResult queryArchiveBatchData(DahcProjectTable dahcProjectTable);
}
