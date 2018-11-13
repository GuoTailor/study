package com.mebay.common.excelImport.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DatabaseMapper {
    void insertData(@Param("headList") List headList, @Param("dataList") List dataList, @Param("tableName") String tableName);
}
