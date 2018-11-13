package com.mebay.common.excelImport.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mebay.common.excelImport.service.Csv2xls;
import com.mebay.common.excelImport.service.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class UploadFileController {

    //上传文件路径
    private static String path ="D:\\upload";

    @Autowired
    private DataImportService dataImportService;
    private Csv2xls csv2xls = new Csv2xls();

    @RequestMapping(method = RequestMethod.POST,value = "upload")
    public String uploadFile(MultipartFile file, String tablename) throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {

        //如果文件不为空，写入上传路径
        if(!file.isEmpty()) {
            //上传文件名
            String filename = file.getOriginalFilename();
            File filepath = new File(path,filename);
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            try {
                file.transferTo(new File(path+"/"+filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ((suffix.equals("csv"))||(suffix.equals("CSV"))){
                dataImportService.importDatabase(csv2xls.change(filepath),tablename);
            }else {
                //调用导入数据服务
                dataImportService.importDatabase(filepath,tablename);
            }
            return "success";
        } else {
            System.out.println("上传文件失败，请重新尝试！");
            return "error";
        }
    }


}
