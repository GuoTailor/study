package com.mebay.controller;

import com.mebay.bean.*;
import com.mebay.service.DeviceRepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gyh on 2018/12/30.
 */
@RequestMapping("/deviceRepair")
@RestController()
@Api(tags = "设备维修")
public class DeviceRepairController {
    @Autowired
    private DeviceRepairService deviceRepairController;

    @GetMapping("/id/{id}")
    @ApiImplicitParam(paramType = "path", name = "id", value = "维修记录id", required = true, dataType = "Long")
    public RespBody<PageView<DeviceRepair>> getRecordById(@PathVariable Long id, PageQuery pageQuery) {
        return new RespBody<>(1, deviceRepairController.getRecordById(id, pageQuery));
    }

    @ApiOperation(value = "通过设备DTUId获取该设备的全部维修信息")
    @ApiImplicitParam(paramType = "path", name = "DTUId", value = "设备DTUId", required = true, dataType = "Long")
    @GetMapping("/DTUId/{DTUId}")
    public RespBody<PageView<DeviceRepair>> getRecordByDTUId(@PathVariable String DTUId, PageQuery pageQuery) {
        return new RespBody<>(1, deviceRepairController.getRecordByDTUId(DTUId, pageQuery), "成功");
    }

    @ApiOperation(value = "添加一条维修记录")
    @PostMapping()
    public RespBody<String> addRepair(DeviceRepair deviceRepair) {
        return new RespBody<String>(deviceRepairController.addRepair(deviceRepair))
                .put(1, "添加成功")
                .put(0, "添加失败");
    }

    @ApiOperation(value = "通过设备的DTUId修改设备的维修记录")
    @PutMapping("/DTUId/{id}")
    public RespBody<String> updateByDTUId(DeviceRepair deviceRepair, @PathVariable String id) {
        return new RespBody<String>(deviceRepairController.updateByDTUId(deviceRepair, id))
                .put(-1, "请至少更新一个字段")
                .put(-2, "没有权限")
                .put(0, "更新失败")
                .put(1, "更新成功");
    }

    @ApiOperation(value = "通过维修记录id删除一条记录")
    @ApiImplicitParam(paramType = "path", name = "id", value = "保养记录id", required = true, dataType = "Long")
    @DeleteMapping("/id/{id}")
    public RespBody<String> deleteRepairById(@PathVariable Long id) {
        return new RespBody<String>(deviceRepairController.deleteRepairById(id))
                .put(1, "删除成功")
                .put(0, "删除失败");
    }

    @ApiOperation(value = "删除一个设备的所有维修记录")
    @ApiImplicitParam(paramType = "path", name = "id", value = "设备的DTUId", required = true, dataType = "Long")
    @DeleteMapping("/DTUId/{id}")
    public RespBody<String> deleteRepairByDTUId(@PathVariable String id) {
        return new RespBody<String>(deviceRepairController.deleteRepairByDTUId(id))
                .put(1, "删除成功")
                .put(0, "删除失败");
    }

    @ApiOperation(value = "获取当前单位所有设备的所有维修记录")
    @GetMapping()
    public RespBody<PageView<DeviceRepair>> getAll(PageQuery pageQuery) {
        return new RespBody<>(1, deviceRepairController.getAll(pageQuery), "成功");
    }
}
