package com.fudian.dahc.service.business;

import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.dto.*;
import com.fudian.dahc.pojo.entity.business.DahcDataTemplate;
import com.fudian.dahc.pojo.entity.business.DahcModel;
import com.fudian.dahc.pojo.query.DataTemplateQuery;
import com.fudian.dahc.service.base.MyBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 2023/2/7
 */
public interface DataTemplateService extends MyBaseService<DahcDataTemplate> {

    /**
     * 根据档案类型id和表级别对表数据进行分页查询
     */
    CommonGridResult selectPage(DataTemplateQuery dataTemplateQuery);

    List<DahcDataTemplate> queryTheAmountOfFileDataBasedOnTheProject(String projectId, String archiveLevelName);
    /**
     * 根据条件分页返回数据
     *
     */
    CommonResult<List<Map<String, Object>>> readMetadata(DataTemplateDto dto);

    CommonResult increaseCases(DahcDataTemplateDto dahcDataTemplate);
    CommonResult updateModel(DahcDataTemplateDto dahcDataTemplate);
    CommonResult deleteByIdModel(DahcDataTemplateDto dahcDataTemplate);
    /**
     * 获取数据库数据表头
     *
     * @param dataTemplateQuery
     * @return
     */
    CommonResult<List<DahcArchiveTypeAndMetadataVo>> getHeader(DataTemplateQuery dataTemplateQuery);

    /**
     * 获取数据库数据详细数据
     *
     * @param dataTemplateQuery
     * @return
     */
    CommonGridResult getBody(DataTemplateQuery dataTemplateQuery);

    /**
     * 导入案卷
     */
    @Transactional
    CommonResult importDataVolume(String id, String projectId, MultipartFile[] multipartFile);

    CommonResult getInformation(String id);

    /**
     * 导入文件
     */
    @Transactional
    CommonResult<?> importDataPiece(String id, String projectId,List<String> batchNo, MultipartFile[] multipartFile,Map<String, Integer> objectObjectHashMap) throws NoSuchFieldException;


    CommonResult<?> readPicture(String archivesId, String filePath, String projectId);

    /*电子核查-图片级 上传文件*/
    CommonResult<?> readPictureCheck(String archivesId, String filePath, String projectId,String caseFileNumber,String pid,String procedureId,String keyName,boolean checkStatus);

    void download(HttpServletResponse response, String url, String fileName);


    /**
     * 查询表头
     *
     * @param uploadFile
     * @param num
     * @return
     */
    CommonResult<Object> queryTemplateRelevanceAccordingToExcel(MultipartFile uploadFile, Integer num, String archivesId,Integer lv);


    /**
     * 上载Excel以读取标题和序列号
     *
     * @param uploadFile 文件
     * @param num        sheet页数
     * @return
     */
    CommonResult<Object> uploadExcelToReadTheHeaderAndSerialNumber(MultipartFile uploadFile, Integer num);

    /**
     * 查询尚未检查的数据 为检查状态1 以开始检查2
     *
     * @param tableName 表名
     * @param attr      主键名
     * @return
     */
    List<Map<String, String>> queryDataThatHasNotBeenChecked(String tableName, String attr,String projectId);

    List<Map<String, String>> queryDataThatHasNotBeenCheckedNoStatus(String tableName, String attr);

    List<Map<String, String>> queryDataThatHasNotBeenBulk(String tableName, String attr, String key);

    /**
     * 根据表名修改此表中数据的备份和核查状态
     */
    int modifyStatusValue(String tableName, Integer newStatus, Integer oldStatus);

    /**
     * @param tableName
     * @param newStatus
     * @param oldStatus
     * @param list
     * @return
     */
    int modifyStatusValueInList(String tableName, Integer newStatus, Integer oldStatus, List<String> list);

    CommonResult<Object> exportExcelAccordingToFileType(DataTemplateDto dto, HttpServletResponse response);
    CommonResult<Object> exportTheAuditResults(DataTemplateDto dto, HttpServletResponse response);

    /*根据主键id数据*/
    List<DahcDataTemplate> queryBasedOnPrimaryKeyIDData(String profileDataTableName, List<String> ids,Integer pageSize,Integer pageNum, String attrOrder,String caseNum);

    CommonResult<Object> uploadFile(MultipartHttpServletRequest uploadFile);

    CommonResult uploadFileOfZip(MultipartHttpServletRequest uploadFile);


    CommonResult<?> modifyTheSort(String id1, String id2, String archiveTypeId, DataTemplateUpAndDownDto dto);

    /**
     * 根据批次查询案卷数据
     *
     * @return 根据批次查询案卷数据
     * @author MCY
     * @date 2023/3/30 14:58
     */
    List<Map<String, Object>> queryCaseFileDataBasedOnBatches(QueryElectronicArchivesDossierLevelDto dossierLevelDto);

    CommonGridResult accessPersonalVerificationData(QueryElectronicArchivesDossierLevelDto dossierLevelDto);

    List<Map<String, Object>> queryPersonalDoubtfulData(QueryElectronicArchivesDossierLevelDto dossierLevelDto);
}
