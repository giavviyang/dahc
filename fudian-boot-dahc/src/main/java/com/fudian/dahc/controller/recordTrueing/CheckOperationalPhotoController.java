package com.fudian.dahc.controller.recordTrueing;

import com.alibaba.fastjson2.JSON;
import com.fudian.common.annotation.Anonymous;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.dto.DahcFilePhotoDto;
import com.fudian.dahc.pojo.dto.QueryFileImgDto;
import com.fudian.dahc.service.business.DahcFilePhotoService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * wenbovo
 * 2023/4/11
 * FD-dahc-project
 */

@Api(tags = "档案核查-图片数据管理")
@ApiSupport(order = 27)
@RestController
@RequestMapping("/photoCheck")
@Anonymous
public class CheckOperationalPhotoController extends MyBaseController {


    @Autowired
    private DahcFilePhotoService dahcFilePhotoService;

    /**
     * 查询图片信息
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/22 17:35
     */
    @ApiOperation("查询图片信息")
    @PostMapping("/getPucter")
    public CommonResult getPucter(@RequestBody QueryFileImgDto dto, HttpServletRequest request, HttpServletResponse respones) {
        List<QueryFileImgDto> str = dahcFilePhotoService.getPucter(dto, request, respones);
        return CommonResult.success(str);
    }


    /**
     * 图片上下移动
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/4/4 15:37
     */
    @ApiOperation("图片上下移动")
    @PostMapping("/thePictureMovesUpAndDown")
    public CommonResult thePictureMovesUpAndDown(@RequestBody DahcFilePhotoDto dto) {
        return dahcFilePhotoService.thePictureMovesUpAndDown(dto);
    }


    /**
     * 图片替换
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/4/4 16:57
     */
    @ApiOperation("图片替换")
    @PostMapping("/imageReplacement")
    public CommonResult imageReplacement(@RequestParam("file") MultipartFile uploadFile, @RequestParam("dto") String dto) {
        DahcFilePhotoDto dahcFilePhoto = JSON.parseObject(dto, DahcFilePhotoDto.class);
        return dahcFilePhotoService.imageReplacement(uploadFile, dahcFilePhoto);
    }


    /**
     * 图片加页
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/4/6 11:40
     */
    @ApiOperation("图片加页")
    @PostMapping("/imagePlusPage")
    public CommonResult imagePlusPage(@RequestParam("file") MultipartFile[] uploadFile, @RequestParam("dto") String dto) {
        DahcFilePhotoDto dahcFilePhoto = JSON.parseObject(dto, DahcFilePhotoDto.class);
        return dahcFilePhotoService.imagePlusPage(uploadFile, dahcFilePhoto);
    }

    /**
     *案件没有图片时加页
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/4/14 17:05
     */
    @ApiOperation("图片加页2")
    @PostMapping("/imagePlusPageTwo")
    public CommonResult imagePlusPageTwo(@RequestParam("file") MultipartFile[] uploadFile, @RequestParam("dto") String dto) {
        DahcFilePhotoDto dahcFilePhoto = JSON.parseObject(dto, DahcFilePhotoDto.class);
        return dahcFilePhotoService.imagePlusPageTwo(uploadFile, dahcFilePhoto);
    }

    /**
     * 图片减页
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/4/7 10:31
     */
    @ApiOperation("图片减页")
    @PostMapping("/imageMinus")
    public CommonResult imageMinus(@RequestBody DahcFilePhotoDto dto) {
        return dahcFilePhotoService.imageMinus(dto);
    }

    /**
     *修改页数
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/5/11 14:19
     */
    @ApiOperation("修改页数-重新排序")
    @PostMapping("/modifyTheNumberOfPages")
    public CommonResult modifyTheNumberOfPages(@RequestBody DahcFilePhotoDto dto) {
        return dahcFilePhotoService.modifyTheNumberOfPages(dto);
    }
}
