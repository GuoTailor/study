package com.mebay.mapper;

import com.mebay.bean.Device;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface DeviceMapper {
    List<Device> getDeviceByUserId(@Param("userId") Long userId);

    List<Device> getDeviceByDepId(@Param("depIds") Collection<Long> depIds, @Param("search") String search);

    Device getDeviceById(@Param("id") Long id);

    int updateDevice(@Param("id") Long id, Device device);

    int addDevice(@Param("device") Device device);

    int deleteDeviceById(@Param("id") Long id);
}
