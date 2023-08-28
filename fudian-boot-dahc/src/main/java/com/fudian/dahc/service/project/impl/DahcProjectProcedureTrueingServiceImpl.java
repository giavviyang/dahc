package com.fudian.dahc.service.project.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.dahc.mapper.project.DahcProjectProcedureTrueingMapper;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedureTrueing;
import com.fudian.dahc.service.project.IDahcProjectProcedureTrueingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fudian
 * @date 2023-02-13
 */
@Service
public class DahcProjectProcedureTrueingServiceImpl extends ServiceImpl<DahcProjectProcedureTrueingMapper,DahcProjectProcedureTrueing> implements IDahcProjectProcedureTrueingService
{
    @Autowired
    private DahcProjectProcedureTrueingMapper dahcProjectProcedureTrueingMapper;

    /**
     *添加工序关联核查项
     * @return java.lang.Boolean
     * @author MCY
     * @date 2023/2/13 18:02
     */
    @Override
    @Transactional
    public Boolean addProcedureTrueing(List<String> trueingIds, String procedureId) {
        List<DahcProjectProcedureTrueing> trueingArrayList = new ArrayList<>();
        for (String trueingId : trueingIds) {
            DahcProjectProcedureTrueing trueing = new DahcProjectProcedureTrueing();
            trueing.setTrueingId(trueingId);
            trueing.setProcedureId(procedureId);
            trueingArrayList.add(trueing);
            IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
            trueing.setSort(identifierGenerator.nextId(new Object()).longValue());
        }
        return this.saveBatch(trueingArrayList);
    }

    /**
     *根据id查询关联数据
     * @return java.util.List<java.lang.String>
     * @author MCY
     * @date 2023/2/13 18:23
     */
    @Override
    public List<String> queryProcedureTrueing(String procedureId) {
        List<DahcProjectProcedureTrueing> list = this.list(Wrappers.<DahcProjectProcedureTrueing>lambdaQuery().eq(DahcProjectProcedureTrueing::getProcedureId, procedureId));
        List<String> strings = new ArrayList<>();
        for (DahcProjectProcedureTrueing dahcProjectProcedureTrueing : list) {
            strings.add(dahcProjectProcedureTrueing.getTrueingId());
        }
        return strings;
    }

    /**
     *根据工序id删除数据
     * @return java.lang.Boolean
     * @author MCY
     * @date 2023/2/14 10:44
     */
    @Override
    @Transactional
    public Boolean deleteProcedureTrueing(String procedureId) {
        if (StringUtils.isNotEmpty(procedureId)) {
            return this.remove(Wrappers.<DahcProjectProcedureTrueing>lambdaQuery().eq(DahcProjectProcedureTrueing::getProcedureId, procedureId));
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteProcedureTrueingIds(List<String> procedureIds) {
       return this.remove(Wrappers.<DahcProjectProcedureTrueing>lambdaQuery().in(DahcProjectProcedureTrueing::getProcedureId, procedureIds));
    }
}
