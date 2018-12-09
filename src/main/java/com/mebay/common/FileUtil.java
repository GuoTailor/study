package com.mebay.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件工具
 */
public class FileUtil {
    public static final HashMap<String, String> mFileTypes = new HashMap<>();

    static {
        // images
        mFileTypes.put("FFD8FF", ".jpg");
        mFileTypes.put("89504E47", ".png");
        mFileTypes.put("47494638", ".gif");
        mFileTypes.put("49492A00", ".tif");
        mFileTypes.put("424D", ".bmp");
    }

    /**
     * 获取文件的类型<p>
     * 注意：目前只支持几种图片的识别
     *
     * @param file MultipartFile
     * @return 文件类型
     */
    public static String getFileType(MultipartFile file) {
        if (file == null)
            return null;
        byte[] b = new byte[4];
        try (InputStream is = file.getInputStream()) {
            is.read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        String hv;
        for (byte aB : b) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(aB & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        System.out.println(builder.toString());
        for (Map.Entry<String, String> entry : mFileTypes.entrySet()) {
            if (builder.toString().startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 转存文件
     *
     * @param file MultipartFile对象
     * @param path 要保存的路径
     * @return 保存之后的文件路径
     */
    public static String saveFile(MultipartFile file, String path, String name) {
        if (file != null) {
            // 文件名
            String fileName = file.getOriginalFilename();
            // 文件后缀
            String suffixName = "";
            if (fileName != null)
                suffixName = fileName.substring(fileName.lastIndexOf("."));

            String type = getFileType(file);
            System.out.println("原文件后缀名： " + suffixName + "\n新文件后缀名：" + type);
            String newFileName = file.getOriginalFilename();    //TODO 文件类型
            //如果类型不等于空说明是图片类文件
            if (type != null) {
                // 重新生成唯一文件名，用于存储数据库
                newFileName = UUID.randomUUID().toString() + "-" + System.currentTimeMillis() + type;
            }
            else if (name != null) {
                newFileName = name;
            }
            System.out.println("新的文件名： " + newFileName);
            File f = new File(path);
            if (!f.exists() && !f.mkdirs())
                return null;
            System.out.println(path + " >> " + f.getAbsolutePath());
            try {
                file.transferTo(new File(f.getCanonicalPath(), newFileName));
                if (path.charAt(path.length() - 1) != '\\' || path.charAt(path.length() - 1) != '/')
                    path += "/";
                return path + newFileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除文件
     *2
     * @param path 文件路径
     * @return true：删除成功，false：删除失败
     */
    public static boolean deleteFile(String path) {
        System.out.println(path);
        return new File(path).delete();
    }

    public static BasicFileAttributes getFileInfo(String filePath) {
        Path path= Paths.get(filePath);
        BasicFileAttributeView basicview= Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS );
        BasicFileAttributes attr = null;
        try {
            attr = basicview.readAttributes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attr;
    }

}
