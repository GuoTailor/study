package com.mebay.service;

import com.mebay.bean.Device;
import com.mebay.bean.User;
import com.mebay.common.UserUtils;
import com.mebay.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Device> getDeviceByDepId() {
        User user = UserUtils.getCurrentUser();
        return deviceMapper.getDeviceByDepId(user.getDepId());
    }

    /*public List<Device> getDevice() {
        User user = UserUtils.getCurrentUser();

    }*/
}
