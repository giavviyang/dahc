package com.fudian.dahc.mapper.project;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 2023/3/21
 */
@Mapper
public interface FilePathMapper {

    @Insert("replace into dahc_file_path(project_id,project_id,file_path) value (#{id},#{name},#{path})")
    int add(String id, String name, String path);


    @Select("<script>" +
            "select project_id,project_id,file_path from dahc_file_path " +
            "<where>" +
            "<if test = \" id != null and  id != '' \"> AND project_id = #{id} </if> " +
            "</where>" +
            "</script>")
    Map<String, String> select(String id);

}
