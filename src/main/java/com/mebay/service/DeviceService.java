package com.mebay.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.bean.*;
import com.mebay.common.FileUtil;
import com.mebay.common.UserUtils;
import com.mebay.common.Util;
import com.mebay.mapper.DeviceMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 设备表
 */
@Service
@Transactional(rollbackFor={Exception.class})
public class DeviceService {
    private static Logger logger = Logger.getLogger(DeviceService.class.getSimpleName());
    private final DeviceMapper deviceMapper;
    private final DepartmentService departmentService;

    @Autowired
    public DeviceService(DeviceMapper deviceMapper, DepartmentService departmentService) {
        this.deviceMapper = deviceMapper;
        this.departmentService = departmentService;
    }

    public List<Device> getDeviceByUserId() {
        return deviceMapper.getDeviceByUserId(UserUtils.getCurrentUser().getId());
    }

    /**
     * 获取该单位下的所有设备
     * @param pageQuery 分页参数
     */
    public PageView<Device> getDeviceByDepId(PageQuery pageQuery) {
        List<IdTree> deptId = departmentService.getDeptIdTreeByUser();
        List<Long> ids = new LinkedList<>();
        deptId.forEach(d -> d.getIDs(ids));
        Page<Device> page = PageHelper.startPage(pageQuery);
        deviceMapper.getDeviceByDepId(ids, pageQuery.buildSubSql());
        return PageView.build(page);
    }

    /**
     * 添加设备
     * @param device 设备信息
     */
    public int addDevice(Device device) {
        device.setDepId(UserUtils.getCurrentUser().getDepId());
        if(deviceMapper.isExistDeviceDTUId(device.getDTUId()) > 0) {
            return -2;
        }
        return deviceMapper.addDevice(device);
    }

    /**
     * 删除设备，只能删除自己能访问的设备
     * @param id 设备id
     */
    public int deleteDevice(Long id) {
        List<Device> deviceList = getEditDevices(null);
        for (Device d : deviceList) {
            if (d.getDepId().equals(id)) {
                return deviceMapper.deleteDeviceById(id);
            }
        }
        return -1;
    }

    /**
     * 更新设备，只能更新自己能访问的设备
     * @param id 设备id
     * @param device 要跟新的信息
     */
    public int updateDevice(Long id, Device device) {
        if (Util.isEmpty(device)) {
            return -2;
        }
        List<Device> deviceList = getEditDevices(null);
        for (Device d : deviceList) {
            if (d.getId().equals(id)) {
                String logoPath = d.getUploadPhoto();
                int i = deviceMapper.updateDevice(id, device);
                logger.info(logoPath + " >> " + device.getUploadPhoto());
                if (i == 1 && logoPath != null && StringUtils.isEmpty(device.getUploadPhoto()) && !logoPath.equals(device.getUploadPhoto())) {
                    FileUtil.deleteFile("." + logoPath);
                    logger.info("删除》》》》》");
                }
                return deviceMapper.updateDevice(id, device);
            }
        }
        return -1;
    }

    /**
     * 获取可查看的设备
     * @return 设备列表
     */
    public PageView<Device> getLookDevices(PageQuery pageQuery) {
        User user = UserUtils.getCurrentUser();
        if (Util.hasAny(Role::equalsRole, user.getRole(), "ROLE_HIGH_GRADE_ADMIN", "ROLE_DEALER_ADMIN", "ROLE_ASSEMBLY_MACHINE_ADMIN", "ROLE_ENGINEER", "ROLE_SURVEY")){
            return getDeviceByDepId(pageQuery);
        }else if (Util.hasAny(Role::equalsRole, user.getRole(), "ROLE_OPERATOR")) {
            Page<Device> page = PageHelper.startPage(pageQuery);
            getDeviceByUserId();
            return PageView.build(page);
        }
        Page<Device> page = PageHelper.startPage(pageQuery);
        deviceMapper.getDeviceByDepId(Collections.singleton(user.getDepId()), pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public Long getDeviceByDTUId(String dtuid) {
        return deviceMapper.getDeviceByDTUId(dtuid);
    }

    /**
     * 获取可编辑的设备
     * @return 设备列表
     */
    public List<Device> getEditDevices(PageQuery pageQuery) {
        User user = UserUtils.getCurrentUser();
        if (user.getRole().contains(new Role("ROLE_SURVEY"))) {
            return null;
        }
        String sql = null;
        if (pageQuery != null)
            sql = pageQuery.buildSubSql();
        return deviceMapper.getDeviceByDepId(Collections.singleton(user.getDepId()), sql);
    }

}
