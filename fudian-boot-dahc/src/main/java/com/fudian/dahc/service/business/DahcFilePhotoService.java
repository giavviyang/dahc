package com.fudian.dahc.service.business;


import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.dto.DahcFilePhotoDto;
import com.fudian.dahc.pojo.dto.QueryFileImgDto;
import com.fudian.dahc.pojo.entity.business.DahcFilePhoto;
import com.fudian.dahc.service.base.MyBaseService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 2023/3/9
 */

public interface DahcFilePhotoService extends MyBaseService<DahcFilePhoto> {


    /**
     * 批量查询
     */
    List<DahcFilePhoto> bulkQueryByIds(List<String> lists);

    /**
     * 批量插入
     */
    Integer bulkInsert(List<DahcFilePhoto> dahcFilePhotos);
    Integer bulkInsertCheck(List<DahcFilePhoto> dahcFilePhotos);

    /**
     * 批量修改
     */
    boolean bulkUpdate(List<DahcFilePhoto> dahcFilePhotos);

    List<QueryFileImgDto> getPucter(QueryFileImgDto dto, HttpServletRequest request, HttpServletResponse respones);

    CommonResult thePictureMovesUpAndDown(DahcFilePhotoDto dto);

    /**
     * 图片替换
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/4/4 16:57
     */
    CommonResult imageReplacement(MultipartFile uploadFile, DahcFilePhotoDto dto);

    CommonResult imagePlusPage(MultipartFile[] uploadFile, DahcFilePhotoDto dto);
    CommonResult imagePlusPageTwo(MultipartFile[] uploadFile, DahcFilePhotoDto dto);

    CommonResult imageMinus(DahcFilePhotoDto dto);
    CommonResult modifyTheNumberOfPages(DahcFilePhotoDto dto);

    Integer queryTheMaximumNumberOfPages(String projectId,String pid);

}
