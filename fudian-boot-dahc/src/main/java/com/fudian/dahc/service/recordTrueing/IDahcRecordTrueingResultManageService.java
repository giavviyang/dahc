package com.fudian.dahc.service.recordTrueing;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordTrueingResultManage;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;

import java.util.List;


/**
 * 【请填写功能名称】Service接口
 *
 * @author fudian
 * @date 2023-02-21
 */
public interface IDahcRecordTrueingResultManageService extends IService<DahcRecordTrueingResultManage>
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
     * 修改【请填写功能名称】
     *
     * @param dahcRecordTrueingResultManage 【请填写功能名称】
     * @return 结果
     */
    public int updateDahcRecordTrueingResultManage(DahcRecordTrueingResultManage dahcRecordTrueingResultManage);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteDahcRecordTrueingResultManageByIds(String[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDahcRecordTrueingResultManageById(String id);



}
