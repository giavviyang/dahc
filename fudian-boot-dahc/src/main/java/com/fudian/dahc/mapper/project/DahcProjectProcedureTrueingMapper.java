package com.fudian.dahc.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedureTrueing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 【请填写功能名称】Mapper接口
 *
 * @author fudian
 * @date 2023-02-13
 */
@Mapper
public interface DahcProjectProcedureTrueingMapper extends BaseMapper<DahcProjectProcedureTrueing> {

    int procedureInspectUpAndDown(@Param("id1") String id1, @Param("id2") String id2,@Param("procedureId") String procedureId);

}
