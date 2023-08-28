package com.fudian.dahc.util.common;

import lombok.Data;

import java.util.List;

/**
 * @Description: 导出对象
 * @Author: 杨永卓
 * @Date: 2022/9/5 10:34
 */
@Data
public  class ExcelExp {
    private String fileName;// sheet的名称
    private String[] handers;// sheet里的标题
    private List<String[]> dataset;// sheet里的数据集
    private String tableName;

    public ExcelExp(String fileName, String[] handers, List<String[]> dataset, String tableName) {
        this.fileName = fileName;
        this.handers = handers;
        this.dataset = dataset;
        this.tableName = tableName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getHanders() {
        return handers;
    }

    public void setHanders(String[] handers) {
        this.handers = handers;
    }

    public List<String[]> getDataset() {
        return dataset;
    }

    public void setDataset(List<String[]> dataset) {
        this.dataset = dataset;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

