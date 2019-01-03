package com.mebay.mapper;

import com.mebay.bean.DeviceRepair;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gyh on 2018/12/27.
 */
@Repository
public interface DeviceRepairMapper {
    DeviceRepair getRecordById(@Param("id")Long id, @Param("search") String search);

    List<DeviceRepair> getRecordByDTUId(@Param("DTUId") String DTUId, @Param("search") String search);

    List<DeviceRepair> getRecordByDTUIds(@Param("DTUIds") List<String> DTUIds, @Param("search") String search);

    int updateByDTUId(@Param("deviceRepair") DeviceRepair deviceRepair, @Param("DTUId") String DTUId);

    int addRepair(DeviceRepair deviceRepair);

    int deleteRepairById(@Param("id") Long id);

    int deleteRepairByDTUId(@Param("DTUId") String DTUId);
}
