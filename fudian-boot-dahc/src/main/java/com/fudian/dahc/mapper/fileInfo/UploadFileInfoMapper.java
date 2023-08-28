package com.fudian.dahc.mapper.fileInfo;

import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.pojo.entity.fileInfo.QueryFileInfo;
import com.fudian.dahc.pojo.entity.fileInfo.UploadFileInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * wenbovo
 * 2023/3/24
 */
@Mapper
public interface UploadFileInfoMapper extends MyBaseMapper<UploadFileInfo> {

    int deleteByPrimaryKey(String id);


    int insertSelective(UploadFileInfo record);

    UploadFileInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UploadFileInfo record);

    int updateByPrimaryKey(UploadFileInfo record);

    List<UploadFileInfo> selectFileByParams(UploadFileInfo fileInfo);

    List<UploadFileInfo> selectFileList(QueryFileInfo query);

}