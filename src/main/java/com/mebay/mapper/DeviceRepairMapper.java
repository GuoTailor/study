package com.mebay.mapper;

import com.mebay.bean.DeviceRepair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gyh on 2018/12/27.
 */
public interface DeviceRepairMapper {
    DeviceRepair getRecordById(@Param("id")Long id, @Param("search") String search);

    List<DeviceRepair> getRecordByDTUId(@Param("DTUId") String DTUId, @Param("search") String search);

    List<DeviceRepair> getRecordByDTUIds(@Param("DTUIds") List<String> DTUIds, @Param("search") String search);
}
