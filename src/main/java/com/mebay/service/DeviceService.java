package com.mebay.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.bean.*;
import com.mebay.common.UserUtils;
import com.mebay.common.Util;
import com.mebay.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(rollbackFor={Exception.class})
public class DeviceService {
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

    public PageView<Device> getDeviceByDepId(PageQuery pageQuery) {
        List<DeptTreeId> deptId = departmentService.getDeptIdTreeByUser();
        List<Long> ids = new LinkedList<>();
        deptId.forEach(d -> d.getIDs(ids));
        Page<Device> page = PageHelper.startPage(pageQuery);
        deviceMapper.getDeviceByDepId(ids, pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public int addDevice(Device device) {
        device.setDepId(UserUtils.getCurrentUser().getDepId());
        return deviceMapper.addDevice(device);
    }

    public int deleteDevice(Long id) {
        List<Device> deviceList = getEditDevices(null);
        for (Device d : deviceList) {
            if (d.getDepId().equals(id)) {
                return deviceMapper.deleteDeviceById(id);
            }
        }
        return -1;
    }

    public int updateDevice(Long id, Device device) {
        List<Device> deviceList = getEditDevices(null);
        for (Device d : deviceList) {
            if (d.getDepId().equals(id)) {
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
