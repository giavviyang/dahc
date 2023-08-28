package com.fudian.dahc.util.common;


import com.fudian.common.config.RuoYiConfig;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.business.DahcFilePhoto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
public class FileUtil {

    private static String realPath = RuoYiConfig.getProfile();
    private static List<DahcFilePhoto> photos = new ArrayList<>();


    public static InputStream getResourcesFileInputStream(String fileName) {
        //得到当前的classpath的绝对路径的URI
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static String getImgPath() {
        return realPath + "/img/";
    }

    public static String getPath() {
        return realPath + "/";
    }


    public static File createNewFile(String pathName) {
        File file = new File(getPath() + pathName);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    public static File readFile(String pathName) {
        return new File(getPath() + pathName);
    }

    public static File readUserHomeFile(String pathName) {
        return new File(System.getProperty("user.home") + File.separator + pathName);
    }

    public static boolean readDirectory(String filepath) {
        File file = new File(filepath);
        if (!file.isDirectory()) {
            System.out.println("文件");
            System.out.println("path=" + file.getPath());
            System.out.println("absolutepath=" + file.getAbsolutePath());
            System.out.println("name=" + file.getName());

        } else if (file.isDirectory()) {
            System.out.println("文件夹");
            String[] fileList = file.list();
            if (fileList != null) {
                for (String s : fileList) {
                    File readFile = new File(filepath + "\\" + s);
                    if (!readFile.isDirectory()) {
                        System.out.println("path=" + readFile.getPath());
                        System.out.println("absolutepath="
                                + readFile.getAbsolutePath());
                        System.out.println("name=" + readFile.getName());
                    } else if (readFile.isDirectory()) {
                        readDirectory(filepath + "\\" + s);
                    }
                }
            }
        }
        return true;
    }


    public static File copeFileAndStorageFile(MultipartFile multipartFile) {
   /*     if (multipartFile.isEmpty()) {
            return null;
        }*/

        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        log.info("上传的文件名：" + fileName);
        //获取文件后缀名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("文件后缀名：" + suffixName);
        //获取运行路径
//        getCanonicalPath
        //设置文件存储路径
        String filePath = getPath();
        //加入时间戳防止重名
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date2 = dateFormat.format(System.currentTimeMillis());
        String path = filePath;
//        File file = new File(path);
        File dest = new File(path, date2 + fileName);
        //检测是否存在该目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        //写入文件
        try {
            log.info("文件存储路径：" + dest.getPath());
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), dest);
            return dest;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            dest.deleteOnExit();
        }
    }

    public static File checkAndStorageFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        log.info("上传的文件名：" + fileName);
        //获取文件后缀名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("文件后缀名：" + suffixName);
        //获取运行路径
//        getCanonicalPath
        //设置文件存储路径
        String filePath = getPath();
        //加入时间戳防止重名
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date2 = dateFormat.format(System.currentTimeMillis());
        String path = filePath;
//        File file = new File(path);
        File dest = new File(path, date2 + fileName);
        //检测是否存在该目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        //写入文件
        try {
            log.info("文件存储路径：" + dest.getPath());

            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), dest);
            return dest;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            dest.deleteOnExit();
        }
    }

    private void breakpointUpload() {
        String boundary = "----WebKitFormBoundaryT1HoybnYeFOGFlBR";
        String prefix = "--";
        String lineEnd = "\r\n";
        String charset = "UTF-8";
        int timeOut = 10 * 1000;
        String requestMethod = "POST";
        String contentType = "multipart/form-data; boundary=" + boundary;
        String urlStr = "http://localhost:8080/upload";
        String filePath = "C:\\Users\\薛文博\\cursor-tutor\\test.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        long fileSize = file.length();
        long uploadedSize = 0;
        int bufferSize = 1024 * 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            // 创建URL对象
            URL url = new URL(urlStr);
            // 打开连接
            conn = (HttpURLConnection) url.openConnection();
            // 设置读取超时时间
            conn.setReadTimeout(timeOut);
            // 设置连接超时时间
            conn.setConnectTimeout(timeOut);
            // 设置请求方法
            conn.setRequestMethod(requestMethod);
            // 允许输入流
            conn.setDoInput(true);
            // 允许输出流
            conn.setDoOutput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            // 设置编码格式
            conn.setRequestProperty("Charset", charset);
            // 设置连接方式
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置请求内容类型
            conn.setRequestProperty("Content-Type", contentType);
            // 设置请求内容长度
            conn.setRequestProperty("Content-Length", String.valueOf(fileSize));
            // 获取输出流
            os = conn.getOutputStream();
            // 文件头部信息
            String filePartHeader = prefix + boundary + lineEnd
                    + "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + lineEnd
                    + "Content-Type: application/octet-stream" + lineEnd + lineEnd;
            // 写入文件头部信息
            os.write(filePartHeader.getBytes());
            // 获取输入流
            is = new FileInputStream(file);
            // 读取文件并写入输出流
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
                uploadedSize += len;
                System.out.println("已上传：" + uploadedSize + "字节，总大小：" + fileSize + "字节");
            }
            // 写入文件尾部信息
            os.write(lineEnd.getBytes());
            String endPart = prefix + boundary + prefix + lineEnd;
            os.write(endPart.getBytes());
            os.flush();
            // 获取响应码
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("上传成功");
            } else {
                System.out.println("上传失败，错误码：" + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 多文件上传
     * 创建FileOutStream对象，构造方法中传递写入数据的目的地
     * 追加写/续写：使用两个参数的构造方法
     * public FileOutputStream(File file,boolean append):创建一个向指定 File 对象表示的文件中写入数据的文件输出流。
     * public FileOutputStream(String name,boolean append):创建一个向具有指定 name 的文件中写入数据的输出文件流。
     */

    public static Set<String> saveZip(MultipartHttpServletRequest request) {
        List<MultipartFile> files = request.getFiles("file");
        MultipartFile file = null;
        Set<String> set = new HashSet<>();
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            //保存路径
            String upPath = FileUtil.getPath() + file.getOriginalFilename();
            set.add(upPath);
            log.info("upPath:{}", upPath);
            if (!file.isEmpty()) {
                try {
                    File finalFile = new File(upPath);
                    //检测是否存在该目录
                    if (!finalFile.getParentFile().exists()) {
                        finalFile.getParentFile().mkdirs();
                    }
                    file.transferTo(finalFile);
                } catch (IOException e) {
                    throw new IllegalStateException("第" + i + "个文件上传失败：" + e.getMessage());
                }
            } else {
                throw new IllegalStateException("第" + i + "个文件上传失败因为文件为空");
            }
        }
        return set;
    }

    public static CommonResult<Object> batchSavePictures(MultipartHttpServletRequest request) {
        List<MultipartFile> files = request.getFiles("file");
        //获取实际路径
        String realPath = request.getSession().getServletContext().getRealPath("/");
        log.info("realPath:{}", realPath);
        MultipartFile file = null;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            //保存路径
            String uppath = getImgPath() + file.getOriginalFilename();
            log.info("uppath:{}", uppath);
            if (!file.isEmpty()) {
                try {
//                    File finalFile = new File(realPath + uppath);
                    File finalFile = new File(uppath);
                    //检测是否存在该目录
                    if (!finalFile.getParentFile().exists()) {
                        finalFile.getParentFile().mkdirs();
                    }
                    file.transferTo(finalFile);
                } catch (IOException e) {
                    return CommonResult.error("第" + i + "个文件上传失败：" + e.getMessage());
                }
            } else {
                return CommonResult.error("第" + i + "个文件上传失败因为文件为空");
            }
        }
        return CommonResult.success();
    }

    /**
     * 保存图片
     */
    public static void picturesSaving(MultipartHttpServletRequest request) {
        //获取上传文件
        MultipartFile file = request.getFile("file");
        //获取实际路径 request.getSession().getServletContext()是获取的servlet容器对象，相当于tomcat容器了。getRealPath("/") 获取实际路径，项目发布时，在容器中的实际路径
        String realPath = request.getSession().getServletContext().getRealPath("/");
        //获取上传图片的文件名
        String fileFullname = file.getOriginalFilename();
        //图片保存路径
        String uppath = "img/" + fileFullname;
        //新建文件
        File distFile = new File(realPath + uppath);
        try {
            //转换文件
            file.transferTo(distFile);
        } catch (IllegalStateException | IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 校验是否是图片
     *
     * @param fileName 文件完整名 例 xxxx.jpg
     */
    public static boolean checkWhetherItIsPicture(String fileName) {
        //获取图片上传的时间，以时间作为图片的名字可以防止图片重名.Now.ToString("yyyyMMddhhmmss");
        String dataName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
        //获取图片的文件名（不含扩展名）
        String name = fileName.substring(fileName.lastIndexOf("/") + 1);
        //获取图片扩展名
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        //获取图片判断是否为要求的格式
        return "bmp".equals(type) || "jpg".equals(type) || "jpeg".equals(type) || "gif".equals(type) || "JPG".equals(type) || "JPEG".equals(type) || "BMP".equals(type) || "GIF".equals(type);
    }


    /**
     * 文件下载
     */
    public static String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        // 文件名
        String fileName = "boss.jpg";
        if (fileName != null) {
            //设置文件路径
            File file = new File("f:/upload/boss.jpg");
            //File file = new File(realPath , fileName);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }
}
