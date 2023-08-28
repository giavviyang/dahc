package com.fudian.dahc.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.service.base.MyBaseService;
import com.fudian.dahc.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 2023/1/28
 */
public class MyBaseServiceImpl<T> extends ServiceImpl<MyBaseMapper<T>, T> implements MyBaseService<T> {

    @Autowired(required = false)
    private MyBaseMapper<T> myBaseMapper;

    @Override
    public List<T> list() {
        return myBaseMapper.selectList(null);
    }


    @Override
    public T findById(Long id) {
        return myBaseMapper.selectById(id);
    }

    @Override
    public int add(T t) {
        ReflectionUtils.invokeMethod(t, "setData", null, null);
        return myBaseMapper.insert(t);
    }

    @Override
    public int update(T t) {
        ReflectionUtils.invokeMethod(t, "setData", null, null);
        return myBaseMapper.updateById(t);
    }

    @Override
    public int deleteById(Long id) {

        return myBaseMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDeleteByIds(List<Long> ids) {
        return myBaseMapper.deleteBatchIds(ids);
    }

}
