package com.fudian.dahc.service.fileInfo;


import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.dto.DahcFilePhotoDto;
import com.fudian.dahc.pojo.dto.DataTemplateDto;
import com.fudian.dahc.pojo.entity.fileInfo.UploadFileChunkInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 文件块处理
 * @author 洋葱骑士
 *
 */
public interface ExprotService {

    CommonResult<Object> exportExcelAccordingToFileType(DataTemplateDto dto);

    CommonResult<?>  getTheFileInformationAndCompressIt(DahcFilePhotoDto dto);

    CommonResult getzipInformation(String id);
}
