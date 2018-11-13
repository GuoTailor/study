package com.mebay.common.excelImport.service;

import com.opencsv.CSVReader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;

public class Csv2xls {
    public File change(File filepath) throws IOException {
        int i=0;
        String filename = filepath.getPath();
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        //将后缀名转换成小写
        String suffixname = suffix.toLowerCase();
        filename = filename.replace(suffix, suffixname);
        String s[];
        //ArrayList list= new ArrayList<String>();
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("list1");
        Row row;
        Cell cell;

        try{
            CSVReader reader = new CSVReader(new FileReader(filename));
            while((s = reader.readNext()) !=null){
                row= sheet.createRow(i);
                for(int j=0;j<s.length;j++){
                    cell= row.createCell(j);
                    cell.setCellValue(s[j]);
                }
                i+=1;
            }
        }
        catch(FileNotFoundException e){
            System.out.println("FileNotFound!");}
        catch (IOException e){}

        filename = filename.replace(suffixname, "xls");
        FileOutputStream fout= new FileOutputStream(filename);
        wb.write(fout);
        fout.close();
        return new File(filename);
    }
}
