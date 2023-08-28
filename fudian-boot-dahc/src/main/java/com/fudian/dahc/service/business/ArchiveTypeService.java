package com.fudian.dahc.service.business;

import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndMetadataVo;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndModelVo;
import com.fudian.dahc.pojo.dto.DahcMetadataVo;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.query.ArchiveAndMetadataDto;
import com.fudian.dahc.pojo.query.ArchiveAndMetadataVO;
import com.fudian.dahc.pojo.query.ArchiveTypeQuery;
import com.fudian.dahc.service.base.MyBaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 2023/1/28
 */
public interface ArchiveTypeService extends MyBaseService<DahcArchiveType> {

    /**
     * 分页查询
     *
     * @return @return
     */
    List<DahcArchiveType> searchPage(ArchiveTypeQuery archiveTypeQuery);

    /**
     * 根据档案id和表等级查询档案及其元数据
     *
     * @return DahcArchiveTypeAndMetadataVo
     */
    List<DahcArchiveTypeAndMetadataVo> selectListAndMetadataById(@Param("level") Integer level, @Param("typeId") Long id);

    /**
     *批量删除
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/28 10:58
     */
    CommonResult batchDeleteByIdsArchiveType(List<Long> ids);

    @Transactional(rollbackFor = Exception.class)
    int insertMetadataList(ArchiveAndMetadataVO archiveAndMetadataVO);


    /**
     * 查询档案类型和其元数据
     *
     * @return CommonResult
     */
    CommonResult<List<ArchiveAndMetadataDto>> queryArchiveAndMetadata(ArchiveAndMetadataDto archiveAndMetadataDto);


    /**
     * 根据id查询模板
     *
     * @return CommonResult
     */
    CommonResult<List<DahcArchiveTypeAndModelVo>> selectModelByArchiveType(String id);

    /**
     * MCY
     *
     * @return CommonResult
     */
    CommonResult<List<Map<String, String>>> queryArchiveTransition();

    /**
     * 创建备份原始数据存储表
     */
    void originalDataStorageTable(String tableName);

    /**
     * 根据档案类型id查询字段
     *
     * @return CommonResult
     */
    CommonResult<Object> theFileNumberFieldIsThatDatabaseField(String archiveTypeId);
    CommonResult<Object> caseFileNumberAttr(String archiveTypeId);



    /**
     * 根据档案类型获取案卷表名
     *
     * @return java.util.List<com.fudian.dahc.pojo.dto.DahcMetadataVo>
     * @author MCY
     * @date 2023/3/21 16:21
     */
    String getsTheFileTableNameBasedOnTheFileType(String projectId);

    DahcArchiveType getOne(String archiveTypeId);


    /**
     * 查询档案默认字段绑定attr
     *
     * @return java.lang.String
     * @author MCY
     * @date 2023/3/22 11:44
     */
    String queryProfileDefaultFieldBindingAttr(String archiveTypeId, String archiveLevelName, String metadataName);

}
