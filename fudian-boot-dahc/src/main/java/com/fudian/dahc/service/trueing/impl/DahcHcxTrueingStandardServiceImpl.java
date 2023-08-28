package com.fudian.dahc.service.trueing.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.utils.uuid.IdUtils;
import com.fudian.dahc.mapper.trueingManage.DahcHcxTrueingStandardMapper;
import com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingStandard;
import com.fudian.dahc.service.trueing.IDahcHcxTrueingStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fudian
 * @date 2023-02-09
 */
@Service
public class DahcHcxTrueingStandardServiceImpl extends ServiceImpl<DahcHcxTrueingStandardMapper,DahcHcxTrueingStandard> implements IDahcHcxTrueingStandardService
{
    @Autowired
    private DahcHcxTrueingStandardMapper dahcHcxTrueingStandardMapper;

    /**
     *批量添加核查标准
     * @return java.lang.Boolean
     * @author MCY
     * @date 2023/2/9 15:16
     */
    @Override
    public Boolean addTrueingStandard(List<String> list, String trueingId) {
        Boolean b = false;
        for (String s : list) {
            DahcHcxTrueingStandard dahcHcxTrueingStandard = new DahcHcxTrueingStandard();
            dahcHcxTrueingStandard.setTrueingId(IdUtils.fastSimpleUUID());
            dahcHcxTrueingStandard.setTrueingId(trueingId);
            dahcHcxTrueingStandard.setTrueingStandard(s);
            b = this.save(dahcHcxTrueingStandard);
        }
        return b;
    }

    @Override
    public String queryById(String trueingId) {
        String trueingStandard = "";
        List<DahcHcxTrueingStandard> list = this.list(Wrappers.<DahcHcxTrueingStandard>lambdaQuery().eq(DahcHcxTrueingStandard::getTrueingId, trueingId));
        for (DahcHcxTrueingStandard dahcHcxTrueingStandard : list) {
            trueingStandard = trueingStandard + dahcHcxTrueingStandard.getTrueingStandard() + "/";
        }
        return trueingStandard;
    }

    @Override
    public Boolean deleteById(String trueingId) {
        return this.remove(Wrappers.<DahcHcxTrueingStandard>lambdaQuery().eq(DahcHcxTrueingStandard::getTrueingId, trueingId));
    }
}
