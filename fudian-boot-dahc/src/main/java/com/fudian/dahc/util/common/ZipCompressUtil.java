package com.fudian.dahc.util.common;

/**
 * 2023/3/2
 */

import com.fudian.dahc.pojo.entity.business.DahcFilePhoto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class ZipCompressUtil {



    /**
     * 生成zip压缩文件
     *
     * @param filePaths
     * @param zipFilePath
     * @param keepDirStructure
     */
    public static void compressByPath(List<Map<String, String>> filePaths, String zipFilePath, Boolean keepDirStructure) {
        byte[] buf = new byte[1024];
        File zipFile = new File(zipFilePath);
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < filePaths.size(); i++) {
                String relativePath = filePaths.get(i).get("filePath");
                String relativeName = filePaths.get(i).get("fileName");
                if (StringUtils.isEmpty(relativePath)) {
                    continue;
                }
                File sourceFile = new File(relativePath);
                if (sourceFile == null || !sourceFile.exists()) {
                    continue;
                }
                FileInputStream fis = new FileInputStream(sourceFile);
                if (keepDirStructure != null && keepDirStructure) {
                    zos.putNextEntry(new ZipEntry(relativePath));
                } else {
                    zos.putNextEntry(new ZipEntry(i + "_" + relativeName));
                }
                int len;
                while ((len = fis.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                // zos.close();
            }
            zos.close();
            if (!zipFile.exists()) {
                zipFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载zip
     *
     * @param response
     * @param zipName  浏览器header中zip名称
     * @param zipFile  zipFile文件
     */
    public static void downloadZip(HttpServletResponse response, String zipName, File zipFile) {
        //下载文件
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment;FileName=" + zipName);
            ServletOutputStream out = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(zipFile);
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
                out.flush();
            }
            out.close();
            fis.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里path指的是读取的zip文件路径
     */
    public static void getFileName(String path) {
        List<String> fileNames = new ArrayList<>();
        try {
            //这里一定要带入格式，不是在读取zip文件的时候会存在问题
            ZipFile zipFile = new ZipFile(path, Charset.forName("gbk"));
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                String fileName = entries.nextElement().getName();
                fileNames.add(fileName);
                System.out.println("文件名称： " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<DahcFilePhoto> readFile(String path) {

        List<DahcFilePhoto> photos = new ArrayList<>();
        String[] suffixImg = {"jpg", "png", "JPG", "PNG", "tif", "jpeg", "JPEG"};
        String[] suffixExcel = {"xlsx", "xls"};
        String[] suffixPdf = {"pdf", "PDF"};
        try {
            //必须指明读取的各式，不是会存在问题***
            ZipFile zipFile = new ZipFile(path, Charset.forName("gbk"));
            //按流的方式读取文件，输入到管道中
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            //字节流转换为压缩文件输入流，通常用来读取压缩文件
            ZipInputStream zp = new ZipInputStream(in);
            ZipEntry ze;//定义文件条目
            Enumeration zipEnum = zipFile.entries();
            //判断是否还有元素
            while (zipEnum.hasMoreElements()) {
                //返回下一对象
                ze = (ZipEntry) zipEnum.nextElement();
                if (ze.isDirectory()) {
                } else {
                    String name = ze.getName();
                    long size = ze.getSize();
                    if (size > 0) {
                        String suffixName = name.substring(name.lastIndexOf(".") + 1);
                        if (Arrays.asList(suffixImg).contains(suffixName)) {
                            System.out.println("图片:" + name + " : " + ze.getSize() + " bytes");
                            //读取文件内容
                            BufferedImage image = ImageIO.read(zipFile.getInputStream(ze));
                            Double width = (double) image.getWidth();
                            Double height = (double) image.getHeight();
                            Double pixelSize = (double) image.getColorModel().getPixelSize();
                            String keyName = name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf("."));
                            String pageNum = keyName.substring(keyName.lastIndexOf("-") + 1);
                            photos.add(new DahcFilePhoto(
                                    keyName, null,null, name, suffixName, "img/" + name, width, height, pixelSize,
                                    Long.valueOf(pageNum), size, null, 0));
                        } else if (Arrays.asList(suffixExcel).contains(suffixName)) {
                            System.out.println("一个excel:" + name);
                        } else if (Arrays.asList(suffixPdf).contains(suffixName)) {
                            System.out.println("一个pdf:" + name);
                        }
                    }
                }
            }
            zp.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photos;
    }


    public static void unzip(String zippath, String resourcepath) {
        //判断生成目录是否生成，如果没有就创建
        File pathFile = new File(resourcepath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zp = null;
        try {
            //指定编码，否则压缩包里面不能有中文目录
            zp = new ZipFile(zippath, Charset.forName("gbk"));
            //遍历里面的文件及文件夹
            Enumeration entries = zp.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zp.getInputStream(entry);
                String outpath = (resourcepath + zipEntryName).replace("/", File.separator);
                //判断路径是否存在，不存在则创建文件路径
                File file = new File(outpath.substring(0, outpath.lastIndexOf(File.separator)));
                if (!file.exists()) {
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是,不需要解压
                if (new File(outpath).isDirectory()) {
                    continue;
                }
                OutputStream out = new FileOutputStream(outpath);
                byte[] bf = new byte[2048];
                int len;
                while ((len = in.read(bf)) > 0) {
                    out.write(bf, 0, len);
                }
                in.close();
                out.close();
            }
            zp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 压缩文件
     */
    public static void zipPark(String inputFile, String outputFile) throws IOException {
        //创建zip输出流
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFile));
        //创建缓存输出流
        BufferedOutputStream bos = new BufferedOutputStream(out);
        File input = new File(inputFile);
        compressByStream(out, bos, input, null);
        bos.close();
        out.close();//要注意关闭流，不是会导致最终结果出现问题
    }

    /**
     * 压缩文件成zip文件
     */
    public static void compressByStream(ZipOutputStream out, BufferedOutputStream bos, File input, String name) throws IOException {
        if (name == null) {
            name = input.getName();
        }
        //如果输入的文件名称为文件夹,需要遍历里面的文件及文件夹下文件遍历;如果是文件就只需要将该文件进行压缩
        if (input.isDirectory()) {
            File[] files = input.listFiles();
            //当该文件夹为空时,只需要将该目录存入压缩文件中即可
            if (Objects.requireNonNull(files).length == 0) {
                out.putNextEntry(new ZipEntry(name + "/"));
            } else {
                for (int i = 0; i < files.length; i++) {
                    compressByStream(out, bos, files[i], name + "/" + files[i].getName());
                }
            }
        } else {
            out.putNextEntry(new ZipEntry(name));
            FileInputStream fos = new FileInputStream(input);
            BufferedInputStream bis = new BufferedInputStream(fos);
            int len;
            byte[] bf = new byte[1024];
            while ((len = bis.read(bf)) != -1) {
                bos.write(bf, 0, len);
                bos.flush();
            }
            bis.close();
            fos.close();
        }
    }


}