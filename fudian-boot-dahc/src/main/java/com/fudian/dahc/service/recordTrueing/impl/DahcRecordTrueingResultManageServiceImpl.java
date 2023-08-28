package com.fudian.dahc.service.recordTrueing.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.utils.uuid.IdUtils;
import com.fudian.dahc.mapper.recordTrueing.DahcRecordTrueingResultManageMapper;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordTrueingResultManage;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;
import com.fudian.dahc.service.recordTrueing.IDahcRecordTrueingResultManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fudian
 * @date 2023-02-21
 */
@Service
public class DahcRecordTrueingResultManageServiceImpl extends ServiceImpl<DahcRecordTrueingResultManageMapper,DahcRecordTrueingResultManage> implements IDahcRecordTrueingResultManageService {
    @Autowired
    private DahcRecordTrueingResultManageMapper dahcRecordTrueingResultManageMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public DahcRecordTrueingResultManage selectDahcRecordTrueingResultManageById(String id) {
        return dahcRecordTrueingResultManageMapper.selectDahcRecordTrueingResultManageById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcRecordTrueingResultManage 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DahcRecordTrueingResultManage> selectDahcRecordTrueingResultManageList(DahcRecordTrueingResultManage dahcRecordTrueingResultManage) {
        return dahcRecordTrueingResultManageMapper.selectDahcRecordTrueingResultManageList(dahcRecordTrueingResultManage);
    }


    /**
     * 修改【请填写功能名称】
     *
     * @param dahcRecordTrueingResultManage 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDahcRecordTrueingResultManage(DahcRecordTrueingResultManage dahcRecordTrueingResultManage) {
        return dahcRecordTrueingResultManageMapper.updateDahcRecordTrueingResultManage(dahcRecordTrueingResultManage);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDahcRecordTrueingResultManageByIds(String[] ids) {
        return dahcRecordTrueingResultManageMapper.deleteDahcRecordTrueingResultManageByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDahcRecordTrueingResultManageById(String id) {
        return dahcRecordTrueingResultManageMapper.deleteDahcRecordTrueingResultManageById(id);
    }


}
