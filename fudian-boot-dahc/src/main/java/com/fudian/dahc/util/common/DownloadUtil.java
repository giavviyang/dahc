package com.fudian.dahc.util.common;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 2023/3/17
 */
public class DownloadUtil {

    /**
     * @param byteArrayOutputStream 将文件内容写入ByteArrayOutputStream
     * @param response              HttpServletResponse  写入response
     * @param returnName            返回的文件名
     */
    public void download(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response, String returnName) throws IOException {
        response.setContentType("application/octet-stream;charset=utf-8");
        //保存的文件名,必须和页面编码一致,否则乱码
        returnName = URLEncoder.encode(returnName, "utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + returnName);
        response.setContentLength(byteArrayOutputStream.size());
        //取得输出流
        ServletOutputStream outputstream = response.getOutputStream();
        //写到输出流
        byteArrayOutputStream.writeTo(outputstream);
        byteArrayOutputStream.close();            //关闭
        outputstream.flush();              //刷数据
    }


    public void downloadExcel(String fileName, HttpServletRequest request, HttpServletResponse response) {
        String url = "D:\\fudian\\uploadPath\\upload\\img\\photo_2020-09-15_19-48-02.jpg";
        try {
            System.out.println("------------开始下载文件---------------");
            File file = new File(url);
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(url));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new
                    String(fileName.getBytes("UTF-8"),
                    "ISO8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            //清除session里的数据
            request.getSession().removeAttribute("XSSFWorkbook");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
