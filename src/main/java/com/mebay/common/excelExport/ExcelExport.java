package com.mebay.common.excelExport;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * 把数据导入到Excel公用类
 */
@RestController
public class ExcelExport {
    //得到一个Log对象,用做日志记录
    private static Log log = LogFactory.getLog(ExcelExport.class);
    private String fileName;
    @Value("${resourcesPath}")
    private String resourcesPath;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;

    public boolean DB2Excel(ResultSet rs) {
        boolean flag = false;
        WritableWorkbook workbook = null;
        WritableSheet sheet = null;
        Label label = null;

        //创建Excel表
        try {
            //在指定路径创建指定文件名的excel文件
            workbook = Workbook.createWorkbook(new File(resourcesPath));
            //创建Excel表中的sheet,并指定sheet名
            sheet = workbook.createSheet("First Sheet", 0);

            //向Excel中添加数据
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String colName = null;
            int row = 0;
            //添加标题
            for (int i = 0; i < columnCount; i++) {
                colName = rsmd.getColumnName(i + 1);
                label = new Label(i, row, colName);
                //log.debug("标题:"+i+"---"+row +"---"+ colName);
                sheet.addCell(label);
            }
            row++;
            log.debug("写入标题成功");
            while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    label = new Label(i, row, rs.getString(i + 1));
                    log.debug("行:" + i + "---" + row + "---" + rs.getString(i + 1));
                    sheet.addCell(label);
                }
                //增加行
                row++;
            }
            log.debug("写入内容成功");
            //关闭文件
            workbook.write();
            workbook.close();
            log.info("数据成功写入Excel");
            flag = true;
        } catch (SQLException | WriteException | IOException e) {
            log.debug(e.getMessage());
        } finally {
            if (workbook != null)
                try {
                    workbook.close();
                } catch (Exception e) {
                }
        }
        return flag;
    }

    /**
     * 测试方法
     *
     * @param tableName 表名
     */
    @RequestMapping(method = RequestMethod.POST,value = "excelExport")
    public String returnExcel(String tableName) {
        String name = null;
        try {
            //连接数据库并指定库和表
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from" + tableName);
            if (DB2Excel(rs)) {
                rs.close();
                st.close();
                conn.close();
                name = resourcesPath.replaceFirst("\\.", "") + File.separator + fileName;
            } else
                log.info("数据写入失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
}

