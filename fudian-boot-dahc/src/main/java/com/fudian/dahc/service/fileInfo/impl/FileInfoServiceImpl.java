package com.fudian.dahc.service.fileInfo.impl;

import com.fudian.dahc.mapper.fileInfo.UploadFileInfoMapper;
import com.fudian.dahc.pojo.entity.fileInfo.QueryFileInfo;
import com.fudian.dahc.pojo.entity.fileInfo.UploadFileInfo;
import com.fudian.dahc.service.fileInfo.FileInfoService;
import com.fudian.dahc.util.fileUpload.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件处理类
 *
 * @author 洋葱骑士
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {

    @Resource
    UploadFileInfoMapper tFileInfoMapper;

    @Override
    public int addFileInfo(UploadFileInfo fileInfo) {
        fileInfo.setId(SnowflakeIdWorker.getUUID() + SnowflakeIdWorker.getUUID());
        return tFileInfoMapper.insertSelective(fileInfo);
    }

    @Override
    public List<UploadFileInfo> selectFileByParams(UploadFileInfo fileInfo) {
        return tFileInfoMapper.selectFileByParams(fileInfo);
    }

    @Override
    public List<UploadFileInfo> selectFileList(QueryFileInfo query) {
        return tFileInfoMapper.selectFileList(query);
    }
    @Override
    public int deleteFile(UploadFileInfo tFileInfo) {
        UploadFileInfo t = new UploadFileInfo();
        t.setId(tFileInfo.getId());
        t.setDelFlag("1");
        return tFileInfoMapper.updateByPrimaryKeySelective(t);
    }
}
