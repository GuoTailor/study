package com.mebay.mapper;

import com.mebay.bean.Device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DeviceMapper {
    /**
     * 获取设备通过用户id
     *
     * @param userId 用户id
     * @return Device
     */
    List<Device> getDeviceByUserId(@Param("userId") Long userId);

    /**
     * 获取设备通过单位id
     *
     * @param depIds 单位id
     * @param search 分页查询参数 见{@link com.mebay.bean.PageQuery}.buildSubSql(); 可以为null
     * @return Device
     */
    List<Device> getDeviceByDepId(@Param("depIds") Collection<Long> depIds, @Param("search") String search);

    /**
     * 获取DTUid的个数
     * @param dtuId 要查询的id
     * @return 相同DTUId的个数
     */
    int isExistDeviceDTUId(@Param("DTUId") String dtuId);

    Long getDeviceByDTUId(@Param("dtuId") String dtuId);

    /**
     * 获取设备通过设备id
     *
     * @param id 设备的id
     * @return Device
     */
    Device getDeviceById(@Param("id") Long id);

    /**
     * 更新一个设备的信息
     *
     * @param id     要更新的设备id
     * @param device 新的设备信息
     * @return 受影响的行数 1：修改一行； 0：修改0行（修改失败）
     */
    int updateDevice(@Param("id") Long id, Device device);

    /**
     * 新增一个设备
     *
     * @param device 设备信息
     * @return 受影响的行数 1：插入一行； 0：插入0行（新增失败）
     */
    int addDevice(Device device);

    /**
     * 删除一个设备
     *
     * @param id 设备id
     * @return 受影响的行数 1：删除一行； 0：删除0行（删除失败）
     */
    int deleteDeviceById(@Param("id") Long id);
}
