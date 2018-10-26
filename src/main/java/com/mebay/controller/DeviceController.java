package com.mebay.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.Constant;
import com.mebay.bean.*;
import com.mebay.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .put(1, device)
                .put(-1, "添加失败，权限不足")
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

    @PutMapping("/{id}")
    public RespBody update(Device device, @PathVariable Long id) {
        return new RespBody<>(deviceService.updateDevice(id, device))
                .put(1, device)
                .put(-1, "跟新失败，权限不足")
                .put(0, "跟新失败，请联系管理员");
    }

}
