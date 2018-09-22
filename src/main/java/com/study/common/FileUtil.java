package com.study.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public static String getFileType(MultipartFile file) {
        byte[] b = new byte[4];
        try (InputStream is = file.getInputStream()){
            is.read(b);
        }catch (IOException e) {
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
        for(Map.Entry<String, String> entry : mFileTypes.entrySet()) {
            if(builder.toString().startsWith(entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }

    public static String saveFile(MultipartFile file, String path) {
        if (file != null) {
            // 文件名
            String fileName = file.getOriginalFilename();
            // 文件后缀
            String suffixName = "";
            if (fileName != null)
                suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("原文件后缀名： " + suffixName + "\n新文件后缀名：" + FileUtil.getFileType(file));

            // 重新生成唯一文件名，用于存储数据库
            String type = getFileType(file);
            String newFileName = UUID.randomUUID().toString() + "-" + System.currentTimeMillis() + (type == null ? suffixName : type);
            System.out.println("新的文件名： " + newFileName);
            File f = new File(path);
            if (!f.exists() && !f.mkdirs())
                return null;
            if (path.charAt(path.length() - 1) != '\\' || path.charAt(path.length() - 1) != '/')
                path += '/';
            System.out.println(path);
            try {
                file.transferTo(new File(path, newFileName));
                return path + newFileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }
}
