package com.mebay.controller;

import com.mebay.bean.DeviceUpkeep;
import com.mebay.bean.PageQuery;
import com.mebay.bean.PageView;
import com.mebay.bean.RespBody;
import com.mebay.service.DeviceUpkeepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gyh on 2018/12/23.
 */
@RequestMapping("/deviceUpkeep")
@RestController()
@Api(tags = "设备保养")
public class DeviceUpkeepController {
    @Autowired
    private DeviceUpkeepService deviceUpkeepService;

    @ApiOperation(value = "通过保养记录id获取保养信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "保养记录id", required = true, dataType = "Long")
    @GetMapping("/id/{id}")
    public RespBody<PageView<DeviceUpkeep>> getRecordById(@PathVariable Long id, PageQuery pageQuery) {
        return new RespBody<>(1, deviceUpkeepService.getRecordById(id, pageQuery), "成功");
    }

    @ApiOperation(value = "通过设备DTUId获取该设备的全部保养信息")
    @ApiImplicitParam(paramType = "path", name = "DTUId", value = "设备DTUId", required = true, dataType = "Long")
    @GetMapping("/DTUId/{DTUId}")
    public RespBody<PageView<DeviceUpkeep>> getRecordByDTUId(@PathVariable String DTUId, PageQuery pageQuery) {
        return new RespBody<>(1, deviceUpkeepService.getRecordByDTUId(DTUId, pageQuery), "成功");
    }

    @ApiOperation(value = "添加一条保养记录")
    @PostMapping()
    public RespBody<String> addUpkeep(DeviceUpkeep deviceUpkeep) {
        return new RespBody<String>(deviceUpkeepService.addUpkeep(deviceUpkeep))
                .put(1, "添加成功")
                .put(0, "添加失败");
    }

    @ApiOperation(value = "通过设备的DTUId修改设备的保养记录")
    @PutMapping("/DTUId/{id}")
    public RespBody<String> updateByDTUId(DeviceUpkeep deviceUpkeep, @PathVariable String id) {
        return new RespBody<String>(deviceUpkeepService.updateByDTUId(deviceUpkeep, id))
                .put(-1, "请至少更新一个字段")
                .put(-2, "没有权限")
                .put(0, "更新失败")
                .put(1, "更新成功");
    }

    @ApiOperation(value = "通过保养记录id删除一条记录")
    @ApiImplicitParam(paramType = "path", name = "id", value = "保养记录id", required = true, dataType = "Long")
    @DeleteMapping("/id/{id}")
    public RespBody<String> deleteUpkeepById(@PathVariable Long id) {
        return new RespBody<String>(deviceUpkeepService.deleteUpkeepById(id))
                .put(1, "删除成功")
                .put(0, "删除失败");
    }

    @ApiOperation(value = "删除一个设备的所有保养记录")
    @ApiImplicitParam(paramType = "path", name = "id", value = "设备的DTUId", required = true, dataType = "Long")
    @DeleteMapping("/DTUId/{id}")
    public RespBody<String> deleteUpkeepByDTUId(@PathVariable String id) {
        return new RespBody<String>(deviceUpkeepService.deleteUpkeepByDTUId(id))
                .put(1, "删除成功")
                .put(0, "删除失败");
    }

    @ApiOperation(value = "获取当前单位所有设备的所有保养记录")
    @GetMapping()
    public RespBody<PageView<DeviceUpkeep>> getAll(PageQuery pageQuery) {
        return new RespBody<>(1, deviceUpkeepService.getAll(pageQuery), "成功");
    }

}
