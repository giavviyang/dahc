package com.fudian.dahc.service.log;

import com.fudian.dahc.pojo.entity.log.DahcCheckRecordLog;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author fudian
 * @date 2023-05-24
 */
public interface IDahcCheckRecordLogService
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DahcCheckRecordLog selectDahcCheckRecordLogById(String id);

    public DahcCheckRecordLog queryCommonData(String projectId,String procedureId,boolean checkStatus);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcCheckRecordLog 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DahcCheckRecordLog> selectDahcCheckRecordLogList(DahcCheckRecordLog dahcCheckRecordLog);

    /**
     * 新增【请填写功能名称】
     *
     * @param dahcCheckRecordLog 【请填写功能名称】
     * @return 结果
     */
    public int insertDahcCheckRecordLog(DahcCheckRecordLog dahcCheckRecordLog);

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcCheckRecordLog 【请填写功能名称】
     * @return 结果
     */
    public int updateDahcCheckRecordLog(DahcCheckRecordLog dahcCheckRecordLog);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteDahcCheckRecordLogByIds(String[] ids);
    public int removeAll();

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDahcCheckRecordLogById(String id);
}
