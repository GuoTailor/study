package com.mebay.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.bean.Device;
import com.mebay.bean.PageQuery;
import com.mebay.bean.PageView;
import com.mebay.bean.RespBody;
import com.mebay.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/device")
@RestController()
@Api(tags = "设备")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/look")
    @ApiOperation(value = "获取当前用户能查看的设备")
    public RespBody<PageView<Device>> getLookDevices(PageQuery pageQuery) {
        return new RespBody<>(1, deviceService.getLookDevices(pageQuery));
    }

    @GetMapping("/edit")
    @ApiOperation(value = "获取用户能编辑的设备")
    public RespBody<PageView<Device>> getEditDevices(PageQuery pageQuery) {
        Page<Device> page = PageHelper.startPage(pageQuery);
        deviceService.getEditDevices(pageQuery);
        return new RespBody<>(1, PageView.build(page));
    }

    @PostMapping()
    @ApiOperation(value = "添加一个设备")
    public RespBody addDevice(Device device) {
        return new RespBody<>(deviceService.addDevice(device))
                .setData(deviceService.getDeviceByDTUId(device.getDTUId()))
                .put(1, "添加成功")
                .put(-1, "添加失败，权限不足")
                .put(-2, "存在相同的DTUId")
                .put(0, "添加失败，请联系管理员");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除一个设备")
    @ApiImplicitParam(paramType = "path", name = "id", value = "设备id", required = true, dataType = "Long")
    public RespBody<String> delete(@PathVariable(value = "id") Long id) {
        return new RespBody<String>(deviceService.deleteDevice(id))
                .put(1, "删除成功")
                .put(-1, "删除失败，权限不足")
                .put(0, "删除失败，请联系管理员");
    }

    @PutMapping("/{did}")
    public RespBody<String> update(Device device, @PathVariable Long did) {
        return new RespBody<String>(deviceService.updateDevice(did, device))
                .put(1, "更新成功")
                .put(-1, "更新失败，权限不足")
                .put(-2, "请至少更新一个属性")
                .put(0, "更新失败，请联系管理员");
    }



}
