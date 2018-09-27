package com.mebay.mapper;

import com.mebay.bean.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMapper {
    List<Device> getDeviceByUserId(@Param("userId") Long userId);

    List<Device> getDeviceByDepId(@Param("depId") Long depId);

    Device getDeviceById(@Param("id") Long id);

    Boolean updateDevice(@Param("id") Long id, Device device);

    Boolean addDevice(@Param("device") Device device);

    Boolean deleteDeviceById(@Param("id") Long id);
}
