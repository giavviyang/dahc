package com.fudian.dahc.service.base;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 2023/1/28
 */
public interface MyBaseService<T> extends IService<T> {

    /**
     * 查询所有
     * @return List<T>
     */
    @Override
    List<T> list();

    /**
     * 通过id查询
     * @return T
     */
    T findById(Long id);

    /**
     * 添加
     * @return int
     */
    int add(T t);


    /**
     * 修改
     * @return int
     */
    int update(T t);

    /**
     * 删除
     * @return int
     */
    int deleteById(Long id);

    /**
     * 批量删除
     * @return int
     */
    int batchDeleteByIds(List<Long> ids);

}
