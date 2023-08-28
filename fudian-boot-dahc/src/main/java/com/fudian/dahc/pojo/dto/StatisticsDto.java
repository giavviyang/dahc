package com.fudian.dahc.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 2023/3/15
 */
@Data
public class StatisticsDto {


    @ApiModelProperty(value = "项目名称", required = true)
    private String projectName;

    @ApiModelProperty(value = "核查状态", required = true)
    private String checkTheStatus;

    @ApiModelProperty(value = "核查天数", required = true)
    private Integer numberOfDaysToVerify;

    @ApiModelProperty(value = "核查人数", required = true)
    private Integer numberOfPeopleVerified;

    @ApiModelProperty(value = "案卷数", required = true)
    private Integer numberOfFiles;

    @ApiModelProperty(value = "卷内文件数", required = true)
    private Integer numberOfFilesInTheVolume;

    @ApiModelProperty(value = "卷内图像数", required = true)
    private Integer numberOfImagesInTheVolume;

    @ApiModelProperty(value = "月数", required = true)
    private String months;

    @ApiModelProperty(value = "年度", required = true)
    private String year;

    @ApiModelProperty(value = "工序名称", required = true)
    private String procedureName;


    @ApiModelProperty(value = "核查中数据（0-核查中 1-全部数据）", required = true)
    private Integer dataUnderVerification;

    @ApiModelProperty(value = "实体合格数量", required = true)
    private Integer entityQualifiedCount;

    @ApiModelProperty(value = "实体不合格数量", required = true)
    private Integer entityUnqualifiedCount;

    @ApiModelProperty(value = "实体存疑数量", required = true)
    private Integer entityImpeachCount;

    @ApiModelProperty(value = "电子合格数量", required = true)
    private Integer electronQualifiedCount;

    @ApiModelProperty(value = "电子不合格数量", required = true)
    private Integer electronUnqualifiedCount;

    @ApiModelProperty(value = "电子存疑数量", required = true)
    private Integer electronImpeachCount;

    @ApiModelProperty(value = "电子对照实体合格数量", required = true)
    private Integer electronicQualifiedCount;

    @ApiModelProperty(value = "电子对照实体不合格数量", required = true)
    private Integer electronicUnqualifiedCount;

    @ApiModelProperty(value = "电子对照实体存疑数量", required = true)
    private Integer electronicImpeachCount;

    public StatisticsDto(String projectName, String checkTheStatus, Integer numberOfDaysToVerify, Integer numberOfFiles,String procedureName,
                         Integer entityQualifiedCount, Integer entityUnqualifiedCount, Integer entityImpeachCount,
                         Integer electronQualifiedCount,Integer electronUnqualifiedCount,Integer electronImpeachCount,
                         Integer electronicQualifiedCount,Integer electronicUnqualifiedCount,Integer electronicImpeachCount
                         ) {
        this.projectName = projectName;
        this.checkTheStatus = checkTheStatus;
        this.numberOfDaysToVerify = numberOfDaysToVerify; //天数
        this.numberOfFiles = numberOfFiles; //案卷数
        this.procedureName = procedureName; //案卷数
        this.entityQualifiedCount = entityQualifiedCount; //案卷数
        this.entityUnqualifiedCount = entityUnqualifiedCount; //案卷数
        this.entityImpeachCount = entityImpeachCount; //案卷数
        this.electronQualifiedCount = electronQualifiedCount; //案卷数
        this.electronUnqualifiedCount = electronUnqualifiedCount; //案卷数
        this.electronImpeachCount = electronImpeachCount; //案卷数
        this.electronicQualifiedCount = electronicQualifiedCount; //案卷数
        this.electronicUnqualifiedCount = electronicUnqualifiedCount; //案卷数
        this.electronicImpeachCount = electronicImpeachCount; //案卷数

    }

    public StatisticsDto(String projectName, String checkTheStatus, Integer numberOfDaysToVerify, Integer numberOfPeopleVerified, Integer numberOfFiles,
                         Integer numberOfFilesInTheVolume, Integer numberOfImagesInTheVolume) {
        this.projectName = projectName;
        this.checkTheStatus = checkTheStatus;
        this.numberOfDaysToVerify = numberOfDaysToVerify;
        this.numberOfPeopleVerified = numberOfPeopleVerified;
        this.numberOfFiles = numberOfFiles;
        this.numberOfFilesInTheVolume = numberOfFilesInTheVolume;
        this.numberOfImagesInTheVolume = numberOfImagesInTheVolume;
    }

    public StatisticsDto(String projectName, String checkTheStatus, Integer numberOfPeopleVerified, Integer numberOfFiles,
                         Integer numberOfFilesInTheVolume, Integer numberOfImagesInTheVolume, String year, String months) {
        this.projectName = projectName;
        this.checkTheStatus = checkTheStatus;
        this.numberOfPeopleVerified = numberOfPeopleVerified; //人数
        this.numberOfFiles = numberOfFiles; //案卷数
        this.numberOfFilesInTheVolume = numberOfFilesInTheVolume; //文件数
        this.numberOfImagesInTheVolume = numberOfImagesInTheVolume;
        this.year = year;
        this.months = months;
    }
}
