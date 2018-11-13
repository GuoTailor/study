package com.mebay.common.excelImport.service;

import com.mebay.common.excelImport.mapper.DatabaseMapper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@Service
public class DataImportService {
    @Autowired
    private DatabaseMapper databaseMapper;
    private List<String> titleList;

    public void importDatabase(File file, String tableName) throws IOException, InvalidFormatException {
        tableName = "my";
        //得到文件名
        String fileName = file.getPath();
        //得到文件后缀名
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //将后缀名转换成小写
        String suffixname = suffix.toLowerCase();
        //System.out.println(file.getPath());
        file = new File(fileName.replace(suffix, suffixname));
        //System.out.println(file.getAbsolutePath());
        if(file.getName().contains(".xls")||file.getName().contains(".xlsx")) {
            System.out.println("开始对xml文件进行解析");
            try {
                Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
                //获取Excel中的某一个数据表..也可以通过Sheet名称来获取
                Sheet sheet1 = workbook.getSheetAt(0);
                //获取表头字段
                Row header = sheet1.getRow(0);
                titleList = new ArrayList<>();
                for(int i=0;i<header.getLastCellNum();i++) {
                    titleList.add(header.getCell(i).getStringCellValue());
                }

                int lastRowNum = sheet1.getLastRowNum();
                ArrayList<String> arrs;
                for (int i = 1; i < lastRowNum ; i++) {
                    Row row = sheet1.getRow(i);
                    arrs = new ArrayList<>();
                    for (int j = 0; j <row.getLastCellNum() ; j++) {
                        Cell cell = row.getCell(j);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String str = cell.getStringCellValue();
                        arrs.add(str);
                    }
                    insertData(titleList,arrs,tableName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } /*else if(file.getName().contains(".csv")){
            List<String[]> arrs = new ArrayList<String[]>();
            // IO流读取文件
            InputStream input = null;
            CSVReader csvReader = null;
            try {
                input = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(input);
                BufferedReader br = new BufferedReader(isr);
                BufferedReader br1 = new BufferedReader(isr);
                csvReader = new CSVReader(br);
                CsvReader s = new CsvReader(br1);
                // 获取表头字段
                String[] head = s.getHeaders();
                System.out.println(head == null);
                titleList = new ArrayList(Arrays.asList(head));
                String[] rowList;
                int rowNum = 1;
                while ((rowList = csvReader.readNext()) != null) {
                    if(rowNum > 1){
                        arrs.add(rowList);
                    }
                    rowNum ++;
                }
                insertData(titleList,arrs,tableName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        else {
            System.out.println("上传文件不符合类型或格式不符合要求");
        }
    }

    public void insertData(List headList,List dataList,String tableName){
        databaseMapper.insertData(headList,dataList,tableName);
    }

}
