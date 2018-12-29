package com.mebay.mapper;

import com.mebay.bean.DeviceUpkeep;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gyh on 2018/12/23.
 */
@Repository
public interface DeviceUpkeepMapper {

    /**
     * 获取保养记录通过id
     * @param id 保养记录的id
     * @return 一个保养记录
     */
    DeviceUpkeep getRecordById(@Param("id") Long id, @Param("search") String search);

    /**
     * 获取一个设备的所有保养记录
     * @param DTUId 设备的DTUId
     * @return 设备的所有保养记录
     */
    List<DeviceUpkeep> getRecordByDTUId(@Param("DTUId") Long DTUId, @Param("search") String search);

    int updateByDTUId(@Param("deviceUpkeep") DeviceUpkeep deviceUpkeep, @Param("DTUId") Long DTUId);

    List<DeviceUpkeep> getRecordByDTUIds(@Param("DTUIds") List<Long> DTUIds, @Param("search") String search);

    int addUpkeep(DeviceUpkeep deviceUpkeep);

    /**
     * 删除一条保养记录
     * @param id 保养记录的id
     * @return 受影响的行数 1：删除一行； 0：删除0行（删除失败）
     */
    int deleteUpkeepById(@Param("id") Long id);

    /**
     * 删除一个设备的所有保养记录
     * @param DTUId 设备DTUId
     * @return 受影响的行数 n：删除n行； 0：删除0行（删除失败）
     */
    int deleteUpkeepByDTUId(@Param("DTUId") Long DTUId);
}
