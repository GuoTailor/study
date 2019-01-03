package com.mebay.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.bean.*;
import com.mebay.common.Util;
import com.mebay.mapper.DeviceMapper;
import com.mebay.mapper.DeviceRepairMapper;
import com.mebay.mapper.DeviceUpkeepMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gyh on 2018/12/30.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class DeviceRepairService {
    @Autowired
    private DeviceRepairMapper deviceRepairMapper;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DeviceMapper deviceMapper;

    public PageView<DeviceRepair> getRecordById(Long id, PageQuery pageQuery) {
        Page<DeviceRepair> page = PageHelper.startPage(pageQuery);
        deviceRepairMapper.getRecordById(id, pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public PageView<DeviceRepair> getRecordByDTUId(String DTUId, PageQuery pageQuery) {
        Page<DeviceRepair> page = PageHelper.startPage(pageQuery);
        deviceRepairMapper.getRecordByDTUId(DTUId, pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public int updateByDTUId(DeviceRepair deviceRepair, String DTUId) {
        if (Util.isEmpty(deviceRepair)) {
            return -1;
        }
        return deviceRepairMapper.updateByDTUId(deviceRepair, DTUId);
    }

    public int addRepair(DeviceRepair deviceRepair){
        return deviceRepairMapper.addRepair(deviceRepair);
    }

    public int deleteRepairById(Long id) {
        return deviceRepairMapper.deleteRepairById(id);
    }

    public int deleteRepairByDTUId(String DTUId) {
        return deviceRepairMapper.deleteRepairByDTUId(DTUId);
    }

    public PageView<DeviceRepair> getAll(PageQuery pageQuery) {
        List<IdTree> deptId = departmentService.getDeptIdTreeByUser();
        List<Long> ids = new LinkedList<>();
        deptId.forEach(d -> d.getIDs(ids));
        List<Device> devices = deviceMapper.getDeviceByDepId(ids, null);
        List<String> DTUIds = devices.stream().map(Device::getDTUId).collect(Collectors.toList());
        Page<DeviceRepair> page = PageHelper.startPage(pageQuery);
        deviceRepairMapper.getRecordByDTUIds(DTUIds, pageQuery.buildSubSql());
        return PageView.build(page);
    }
}
