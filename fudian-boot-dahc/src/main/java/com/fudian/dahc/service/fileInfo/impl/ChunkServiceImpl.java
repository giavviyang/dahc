package com.fudian.dahc.service.fileInfo.impl;


import com.fudian.dahc.mapper.fileInfo.UploadFileChunkInfoMapper;
import com.fudian.dahc.pojo.entity.fileInfo.UploadFileChunkInfo;
import com.fudian.dahc.service.fileInfo.ChunkService;
import com.fudian.dahc.util.fileUpload.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class ChunkServiceImpl implements ChunkService {

    @Resource
    UploadFileChunkInfoMapper tChunkInfoMapper;

    @Override
    public int saveChunk(UploadFileChunkInfo chunk) {
        chunk.setId(SnowflakeIdWorker.getUUID() + SnowflakeIdWorker.getUUID());
        return tChunkInfoMapper.insertSelective(chunk);
    }

    @Override
    public ArrayList<Integer> checkChunk(UploadFileChunkInfo chunk) {
        return tChunkInfoMapper.selectChunkNumbers(chunk);
    }

    @Override
    public boolean checkChunk(String identifier, Integer chunkNumber) {
        return false;
    }

}
