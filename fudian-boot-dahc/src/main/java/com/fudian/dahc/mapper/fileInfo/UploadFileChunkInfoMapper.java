package com.fudian.dahc.mapper.fileInfo;

import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.pojo.entity.fileInfo.UploadFileChunkInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * wenbovo
 * 2023/3/24
 */
@Mapper
public interface UploadFileChunkInfoMapper extends MyBaseMapper<UploadFileChunkInfo> {

    int deleteByPrimaryKey(String id);

    int insertSelective(UploadFileChunkInfo record);

    UploadFileChunkInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UploadFileChunkInfo record);

    int updateByPrimaryKey(UploadFileChunkInfo record);

    ArrayList<Integer> selectChunkNumbers(UploadFileChunkInfo record);
}
