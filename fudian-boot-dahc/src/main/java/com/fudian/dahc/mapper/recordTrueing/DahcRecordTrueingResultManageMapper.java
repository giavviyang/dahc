package com.fudian.dahc.mapper.recordTrueing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordTrueingResultManage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 【请填写功能名称】Mapper接口
 *
 * @author fudian
 * @date 2023-02-21
 */
@Mapper
public interface DahcRecordTrueingResultManageMapper extends BaseMapper<DahcRecordTrueingResultManage>
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DahcRecordTrueingResultManage selectDahcRecordTrueingResultManageById(String id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcRecordTrueingResultManage 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DahcRecordTrueingResultManage> selectDahcRecordTrueingResultManageList(DahcRecordTrueingResultManage dahcRecordTrueingResultManage);

    /**
     *批量新增
     * @return int
     * @author MCY
     * @date 2023/2/22 17:20
     */
    @Insert("<script>" +
            "INSERT INTO dahc_record_trueing_result_manage" +
            "        (id,archive_type_id,archive_type_name,archive_type_lv,file_id,page_num_file,record,number_of_cases,inspect_result_id)" +
            "        VALUES" +
            "        <foreach collection ='resultManages' item='item' separator =','>" +
            "            (REPLACE(UUID(),\"-\",\"\"), #{item.archiveTypeId}, #{item.archiveTypeName},#{item.archiveTypeLv},#{item.fileId},#{item.pageNumFile},#{item.record},#{item.numberOfCases},#{item.inspectResultId})" +
            "        </foreach >" +
            "</script>")
    public int insertDahcRecordTrueingResultManage(@Param("resultManages") List<DahcRecordTrueingResultManage> dahcRecordTrueingResultManages);

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcRecordTrueingResultManage 【请填写功能名称】
     * @return 结果
     */
    public int updateDahcRecordTrueingResultManage(DahcRecordTrueingResultManage dahcRecordTrueingResultManage);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDahcRecordTrueingResultManageById(String id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDahcRecordTrueingResultManageByIds(String[] ids);
}
