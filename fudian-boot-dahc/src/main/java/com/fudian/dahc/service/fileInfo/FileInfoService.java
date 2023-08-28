package com.fudian.dahc.service.fileInfo;

import com.fudian.dahc.pojo.entity.fileInfo.QueryFileInfo;
import com.fudian.dahc.pojo.entity.fileInfo.UploadFileInfo;

import java.util.List;

public interface FileInfoService {
	
	int addFileInfo(UploadFileInfo fileInfo);
	
	List<UploadFileInfo> selectFileByParams(UploadFileInfo fileInfo);
	
	 /**
     * 查询
     *
     * @param query 查询条件
     * @return List
     */
    List<UploadFileInfo> selectFileList(QueryFileInfo query);
                    
    
    /**
     * 删除
     * @return
     */
    int deleteFile(UploadFileInfo tFileInfo);
}
