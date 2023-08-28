package com.fudian.dahc.util.common;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fudian.dahc.pojo.entity.business.DahcBusinessMapper;
import com.fudian.dahc.pojo.entity.business.DahcDataTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 2023/2/24
 */
@Slf4j
public class ExcelUtil {

    public static List<DahcDataTemplate> readExcel(File file, List<DahcBusinessMapper> mapperList) {
        Map<String, DahcBusinessMapper> map = mapperList.stream().collect(
                Collectors.toMap(DahcBusinessMapper::getSourceName, Function.identity()));
        //读取工作簿对象
        Workbook workbook = selectExcel(file.getPath());
        //总sheet页
        int numberOfSheets = workbook.getNumberOfSheets();
        List<DahcDataTemplate> lists = new ArrayList<>();
        for (int k = 0; k < numberOfSheets; k++) {
            // 通过工作簿找到对应的sheet
            Sheet sheet = workbook.getSheetAt(k);
            // 获取一共有多少行
            int maxRow = sheet.getLastRowNum();
            //获取表头
            if (maxRow == 0) {
                continue;
            }
            Row rowOne = sheet.getRow(0);
            for (int i = 1; i <= maxRow; i++) {
                // 获取具体的每一行
                Row row = sheet.getRow(i);
                // 获取这行有多少个单元格
                short lastCellNum = row.getLastCellNum();
                Map<String, String> templateMap = new HashMap();
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cellOne = rowOne.getCell(j);
                    cellOne.setCellType(CellType.STRING);
                    String one = cellOne.getStringCellValue();
                    if (map.containsKey(one)) {
                        DahcBusinessMapper entity = map.get(one);
                        //判断该字段是否在模板中
                        Cell cell = row.getCell(j);
                        cell.setCellType(CellType.STRING);
                        String stringCellValue = cell.getStringCellValue();
                        if (StringUtils.hasText(stringCellValue)) {
                            String attr = "attr" + entity.getAttrOrdinal();
//                            log.info("标准长度{},读取的长度{}", entity.getMetadataLong().length(), stringCellValue.length());
                            templateMap.put(attr, stringCellValue);
                        }
                    }
                }
                if (!templateMap.isEmpty()) {
                    log.info("解析完成一条:" + templateMap + "\n");
                    templateMap.put("id", IdWorker.getIdStr());
                    DahcDataTemplate dahcDataTemplate = JSON.parseObject(JSON.toJSONString(templateMap), DahcDataTemplate.class);
                    lists.add(dahcDataTemplate);
                } else {
                    log.info("解析失败一条:EXCEL文件{}:中{},第{}行未匹配成功", file.getName(), sheet.getSheetName(), row.getRowNum());
                }
            }
        }
        return lists;
    }

    public static List<DahcDataTemplate> readExcelFile(File file, List<DahcBusinessMapper> mapperList, Map<String, String> fileInformation, Map<String, Integer> objectObjectHashMap) {
        Map<String, DahcBusinessMapper> map = mapperList.stream().collect(
                Collectors.toMap(DahcBusinessMapper::getSourceName, Function.identity()));
        //读取工作簿对象
        Workbook workbook = selectExcel(file.getPath());
        //总sheet页
        int numberOfSheets = workbook.getNumberOfSheets();
        List<DahcDataTemplate> lists = new ArrayList<>();
        try {
            for (int k = 0; k < numberOfSheets; k++) {
                // 通过工作簿找到对应的sheet
                Sheet sheet = workbook.getSheetAt(k);
                // 获取一共有多少行
                int maxRow = sheet.getLastRowNum();
                //获取表头
                if (maxRow == 0) {
                    continue;
                }
                Row rowOne = sheet.getRow(0);
                for (int i = 1; i < maxRow; i++) {
                    // 获取具体的每一行
                    Row row = sheet.getRow(i);
                    // 获取这行有多少个单元格
                    short lastCellNum = row.getLastCellNum();
                    Map<String, String> templateMap = new HashMap();
                    //存储规则数据
                    Map<Integer, String> integers = new TreeMap<>();
                    for (int j = 0; j < lastCellNum; j++) {
                        Cell cellOne = rowOne.getCell(j);
                        cellOne.setCellType(CellType.STRING);
                        String one = cellOne.getStringCellValue();
                        if (map.containsKey(one)) {
                            DahcBusinessMapper entity = map.get(one);
                            Cell cell = row.getCell(j);
                            cell.setCellType(CellType.STRING);
                            String stringCellValue = cell.getStringCellValue();
                            if (StringUtils.hasText(stringCellValue)) {
                                String attr = "attr" + entity.getAttrOrdinal();
//                                log.info("标准长度{},读取的长度{}", entity.getMetadataLong().length(), stringCellValue.length());
                                if (objectObjectHashMap.containsKey(entity.getMetadataName())) {
                                    Integer integer = objectObjectHashMap.get(entity.getMetadataName());
                                    integers.put(integer, stringCellValue);

                                }
                                templateMap.put(attr, stringCellValue);
                            }
                        }
                    }
                    if (integers.keySet().size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (Integer integer : integers.keySet()) {
                            sb.append(integers.get(integer));
                            sb.append("-");
                        }
                        log.info("匹配关键字:{}", sb.deleteCharAt(sb.length() - 1));
                        if (fileInformation.containsKey(sb.toString())) {
                            String s = fileInformation.get(sb.toString());
                            templateMap.put("pid", s);
                            if (!templateMap.isEmpty()) {
                                log.info("解析完成一条:" + templateMap + "\n");
                                templateMap.put("id", IdWorker.getIdStr());
                                DahcDataTemplate dahcDataTemplate = JSON.parseObject(JSON.toJSONString(templateMap), DahcDataTemplate.class);
                                lists.add(dahcDataTemplate);
                            }
                        } else {
                            log.info("解析失败一条:EXCEL文件{}:中{},第{}行未匹配成功", file.getName(), sheet.getSheetName(), row.getRowNum());
                        }
                    } else {
                        log.warn("匹配规则不正确或未找到:EXCEL文件{}:中{},第{}行未匹配成功", file.getName(), sheet.getSheetName(), row.getRowNum());
                    }
                }
            }
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 数据分批
     *
     * @param list
     * @param size
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> split(List<T> list, int size) {
        if (list == null || list.size() == 0) {
            return null;
        }
        // 获得数据总量
        int count = list.size();
        // 计算出要分成几个批次
        int pageCount = (count / size) + (count % size == 0 ? 0 : 1);
        List<List<T>> temp = new ArrayList<>(pageCount);
        for (int i = 0, from = 0, to = 0; i < pageCount; i++) {
            from = i * size;
            to = from + size;
            // 如果超过总数量，则取到最后一个数的位置
            to = Math.min(to, count);
            // 对list 进行拆分
            List<T> list1 = list.subList(from, to);
            // 将拆分后的list放入大List返回
            temp.add(list1);
            // 也可以改造本方法，直接在此处做操作
        }
        return temp;
    }

    /**
     * xls/xlsx都使用的Workbook
     */
    public static Workbook selectExcel(String fileName) {
        Workbook wb = null;
        if (fileName == null) {
            return null;
        }
        String extString = fileName.substring(fileName.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
            if (".xls".equals(extString)) {
                return new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static CellStyle setStyleOfCell(XSSFWorkbook wb) {
        //7.创建单元格样式对象
        CellStyle style = wb.createCellStyle();
        //8.创建字体对象
        Font font = wb.createFont();
        //9.设置字体和其大小及效果
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);
        //加粗
        font.setBold(true);
        //10.设置样式
        style.setFont(font);
        //横向居中
        style.setAlignment(HorizontalAlignment.CENTER);
        //纵向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //上细线
        style.setBorderTop(BorderStyle.THIN);
        //下细线
        style.setBorderBottom(BorderStyle.THIN);
        //左细线
        style.setBorderLeft(BorderStyle.THIN);
        //右细线
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static void exportToExcel(Map<Long, String> metadataNameList, String fileName, Integer level, String projectName) {
        //1.创建Excel对象
        XSSFWorkbook wb = new XSSFWorkbook();
        //2.创建Sheet对象
        Sheet sheet = null;
        if (level == 1) {
            sheet = wb.createSheet("案卷级条目");
        } else {
            sheet = wb.createSheet("文件级条目");
        }

        //3.创建行对象(索引从0开始)
        Row nRow = sheet.createRow(0);
        //4.设置行高和列宽
        nRow.setHeightInPoints(26.25f);
        //(列的索引,列宽*256(理解为固定写法))

        //5.创建单元格对象(索引从0开始)
        int size = metadataNameList.size();
        CellStyle cellStyle = setStyleOfCell(wb);
        // 表头样式
        XSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        // 字体样式
        XSSFFont fontStyle = wb.createFont();
        fontStyle.setFontName("宋体");
        fontStyle.setFontHeightInPoints((short) 12);
        style.setFont(fontStyle);
        for (int i = 0; i < size; i++) {
            Cell cell = nRow.createCell(i);
            sheet.autoSizeColumn(i);
            //6.设置单元格内容
            if (metadataNameList.containsKey((long) i)) {
                String s = metadataNameList.get((long) i);
                sheet.setColumnWidth(i, 30 * 256);
                cell.setCellValue(s);
                //11.为单元格应用样式
                cell.setCellStyle(style);
            }
        }
        // 使用FileOutputStream对象将Workbook对象输出到指定的文件路径。
        String path = FileUtil.getPath();
        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(path + "\\" + projectName + "\\" + fileName);

            wb.write(outFile);
            outFile.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*多sheet页*/
    public static void exportToExcelMultiSheetPages(Map<Long, String> metadataNameList, String fileName, Integer level, ArrayList<Map<String, String>> collect, String attr) {
        //1.创建Excel对象
        XSSFWorkbook wb = new XSSFWorkbook();
        //2.创建Sheet对象
        for (Map<String, String> map : collect) {
            String fileNumber = map.get("attr" + attr);
            fileNumber = fileNumber.substring(0, fileNumber.lastIndexOf('-'));
            Sheet sheet = wb.createSheet(fileNumber);
            //3.创建行对象(索引从0开始)
            Row nRow = sheet.createRow(0);
            //4.设置行高和列宽
            nRow.setHeightInPoints(26.25f);
            //(列的索引,列宽*256(理解为固定写法))
            sheet.setColumnWidth(1, 26 * 256);
            //5.创建单元格对象(索引从0开始)
            int size = metadataNameList.size();
            CellStyle cellStyle = setStyleOfCell(wb);
            // 表头样式
            XSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            // 字体样式
            XSSFFont fontStyle = wb.createFont();
            fontStyle.setFontName("宋体");
            fontStyle.setFontHeightInPoints((short) 12);
            style.setFont(fontStyle);
            for (int i = 0; i < size; i++) {
                Cell cell = nRow.createCell(i);
                //6.设置单元格内容
                if (metadataNameList.containsKey((long) i)) {
                    String s = metadataNameList.get((long) i);
                    cell.setCellValue(s);
                    //11.为单元格应用样式
                    cell.setCellStyle(style);
                }
            }
        }
        // 使用FileOutputStream对象将Workbook对象输出到指定的文件路径。
        String path = FileUtil.getPath();
        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(path + fileName);

            wb.write(outFile);
            outFile.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addContentToExcel2(LinkedList<Map<String, String>> linkedList,
                                          Map<Long, String> metadataNameList,
                                          List<Map<String, String>> list,
                                          String attr, String projectName) throws IOException {
        /*遍历分组数据数据*/
        for (Map<String, String> map : linkedList) {
            /*档号*/
            String fileNumber = map.get("attr" + attr);
            String substring = fileNumber.substring(0, fileNumber.lastIndexOf("-"));
            /*一卷下的案卷数据*/
            List<Map<String, String>> maps = list.stream().filter(m -> m.get("attr" + attr).contains(substring)).collect(Collectors.toList());
            //1.创建Excel对象
            XSSFWorkbook wb = new XSSFWorkbook();
            /*sheet页*/
            Sheet sheet = wb.createSheet(substring);
            //3.创建行对象(索引从0开始)
            Row nRow = sheet.createRow(0);
            //4.设置行高和列宽
            nRow.setHeightInPoints(26.25f);
            //(列的索引,列宽*256(理解为固定写法))

            //5.创建单元格对象(索引从0开始)
            int size = metadataNameList.size();
            CellStyle cellStyle = setStyleOfCell(wb);
            Cell cell = null;
            XSSFCellStyle style = wb.createCellStyle();
            //style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            style.setAlignment(HorizontalAlignment.CENTER);// 左右居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
            // 字体样式
            XSSFFont fontStyle = wb.createFont();
            fontStyle.setFontName("宋体");
            fontStyle.setFontHeightInPoints((short) 12);
            style.setFont(fontStyle);
            for (int i = 0; i < size; i++) {
                cell = nRow.createCell(i);
                sheet.setColumnWidth(i, 30 * 256);
                //6.设置单元格内容
                if (metadataNameList.containsKey((long) i)) {
                    String s = metadataNameList.get((long) i);
                    cell.setCellValue(s);
                    //11.为单元格应用样式
                    cell.setCellStyle(style);
                }
            }
            ///*获取案件数据写入文件*/
            try {
                for (int i = 0; i < maps.size(); i++) {
                    Map<String, String> map1 = maps.get(i);
                    /*创建数据行*/
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < 40; j++) {
                        String s = "attr" + j;
                        /*判断数据的attr是否对应*/
                        if (map1.containsKey(s)) {
                            cell = row.createCell(j);
                            cell.setCellValue(map1.get(s));
                            cell.setCellStyle(style);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 使用FileOutputStream对象将Workbook对象输出到指定的文件路径。
            String path = FileUtil.getPath();
            FileOutputStream outFile = null;
            try {
                //outFile = new FileOutputStream(path + fileName);
                outFile = new FileOutputStream(path + "\\" + projectName + "\\" + substring + ".xlsx");
                wb.write(outFile);
                outFile.close();
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addContentToExcel(LinkedList<Map<String, String>> linkedList, String fileName, String projectName) throws IOException {
        String path = FileUtil.getPath();
        FileInputStream fileExcel = new FileInputStream(path  + projectName + "\\" + fileName);
        XSSFWorkbook wb = new XSSFWorkbook(fileExcel);
        int sheetIndex = wb.getNumberOfSheets();
        Sheet sheet = wb.getSheetAt(sheetIndex - 1);
        Row nRow = null;
        Cell nCell = null;
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        // 字体样式
        XSSFFont fontStyle = wb.createFont();
        fontStyle.setFontName("宋体");
        fontStyle.setFontHeightInPoints((short) 12);
        cellStyle.setFont(fontStyle);
        int lastRowNum = sheet.getLastRowNum() + 1;
        for (Map<String, String> a : linkedList) {
            //13.创建数据行  在现有行号后追加数据
            nRow = sheet.createRow((short) (lastRowNum));
            for (int i = 0; i < 40; i++) {
                String s = "attr" + i;
                if (a.containsKey(s)) {
                    nCell = nRow.createCell(i);
                    nCell.setCellValue(a.get(s));
                    nCell.setCellStyle(cellStyle);
                }
            }
            lastRowNum++;
        }
        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(path + "\\" + projectName + "\\案卷数据.xlsx");
            wb.write(outFile);
            outFile.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载新增表
     *
     * @param inputDate 格式为:2019-01
     */
    public void printExcel(String inputDate, HttpServletResponse response) throws Exception {
        //1.用servletContext对象获取excel模板的真实路径
//        String templatePath = session.getServletContext().getRealPath("/dintalk/xlsprint/dtUSER.xlsx");
        String path = FileUtil.getPath();
        String fileName = "导出条目信息.xlsx";
        //2.读取excel模板,创建excel对象
        XSSFWorkbook workbook = new XSSFWorkbook(path + fileName);
        workbook.cloneSheet(0);
        workbook.setSheetName(1, "新Sheet页1");
        workbook.removeSheetAt(0);
        SXSSFWorkbook wb = new SXSSFWorkbook(workbook);
        //3.读取sheet对象
        Sheet sheet = wb.getSheetAt(0);

        //4.定义一些可复用的对象
        //行的索引
        int rowIndex = 0;
        //单元格的索引
        int cellIndex = 1;
        Row nRow = null;
        Cell nCell = null;
        //5.读取大标题行   使用后 +1
        nRow = sheet.getRow(rowIndex++);
        //6.读取大标题的单元格
        nCell = nRow.getCell(cellIndex);
        //7.设置大标题的内容
        String bigTitle = inputDate.replace("-0", "-").replace("-", "年") + "月份新增用户表";
        nCell.setCellValue(bigTitle);
        //8.跳过第二行(模板的小标题,我们要用)
        rowIndex++;
        //9.读取第三行,获取它的样式
        nRow = sheet.getRow(rowIndex);
        //读取行高
        float lineHeight = nRow.getHeightInPoints();
        //10.获取第三行的5个单元格中的样式
        CellStyle cs1 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle cs2 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle cs3 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle cs4 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle cs5 = nRow.getCell(cellIndex++).getCellStyle();
        //12.遍历数据
//        for (User user : newUserList) {
//            //13.创建数据行
//            nRow = sheet.createRow(rowIndex++);
//        row=sheet.createRow((short)(sheet.getLastRowNum()+1)); //在现有行号后追加数据
//            //16.设置数据行高
//            nRow.setHeightInPoints(lineHeight);
//            //17.重置cellIndex,从第一列开始写数据
//            cellIndex = 1;
//            //18.创建数据单元格，设置单元格内容和样式
//            //用户名
//            nCell = nRow.createCell(cellIndex++);
//            nCell.setCellStyle(cs1);
//            nCell.setCellValue(user.getUserName());

//        }

        //最后，下载新增表，字节数组的输出流，它可存可取，带缓冲区
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将工作簿写到输出流中
        wb.write(bos);
        new DownloadUtil().download(bos, response, bigTitle + ".xlsx");
        bos.close();
        wb.close();
    }


}

