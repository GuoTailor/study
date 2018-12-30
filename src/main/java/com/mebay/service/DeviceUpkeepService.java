package com.mebay.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.bean.*;
import com.mebay.common.Util;
import com.mebay.mapper.DeviceMapper;
import com.mebay.mapper.DeviceUpkeepMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by gyh on 2018/12/23.
 */
//TODO ----------------------=========该类存在权限问题==========-------------------
@Service
@Transactional(rollbackFor = {Exception.class})
public class DeviceUpkeepService {
    @Autowired
    private DeviceUpkeepMapper deviceUpkeepMapper;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DeviceMapper deviceMapper;

    public PageView<DeviceUpkeep> getRecordById(Long id, PageQuery pageQuery) {
        Page<DeviceUpkeep> page = PageHelper.startPage(pageQuery);
        deviceUpkeepMapper.getRecordById(id, pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public PageView<DeviceUpkeep> getRecordByDTUId(String DTUId, PageQuery pageQuery) {
        Page<DeviceUpkeep> page = PageHelper.startPage(pageQuery);
        deviceUpkeepMapper.getRecordByDTUId(DTUId, pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public int updateByDTUId(DeviceUpkeep deviceUpkeep, String DTUId) {
        if (Util.isEmpty(deviceUpkeep)) {
            return -1;
        }
        return deviceUpkeepMapper.updateByDTUId(deviceUpkeep, DTUId);
    }

    public int addUpkeep(DeviceUpkeep deviceUpkeep){
        return deviceUpkeepMapper.addUpkeep(deviceUpkeep);
    }

    public int deleteUpkeepById(Long id) {
        return deviceUpkeepMapper.deleteUpkeepById(id);
    }

    public int deleteUpkeepByDTUId(String DTUId) {
        return deviceUpkeepMapper.deleteUpkeepByDTUId(DTUId);
    }

    public PageView<DeviceUpkeep> getAll(PageQuery pageQuery) {
        List<IdTree> deptId = departmentService.getDeptIdTreeByUser();
        List<Long> ids = new LinkedList<>();
        deptId.forEach(d -> d.getIDs(ids));
        List<Device> devices = deviceMapper.getDeviceByDepId(ids, null);
        List<String> DTUIds = devices.stream().map(Device::getDTUId).collect(Collectors.toList());
        Page<DeviceUpkeep> page = PageHelper.startPage(pageQuery);
        deviceUpkeepMapper.getRecordByDTUIds(DTUIds, pageQuery.buildSubSql());
        return PageView.build(page);
    }

}
