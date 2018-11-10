package com.mebay.controller.system;

import com.mebay.bean.Department;
import com.mebay.bean.PageQuery;
import com.mebay.bean.PageView;
import com.mebay.bean.RespBody;
import com.mebay.common.FileUtil;
import com.mebay.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@RestController
@RequestMapping("/system")
@Api(tags = "单位")
public class DepController {
    final private DepartmentService departmentService;

    @Autowired
    public DepController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping(value = "/dep")
    @ApiOperation(value = "添加一个单位到本单位下")
    @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "用户id", dataType = "Long")
    public RespBody addDep(@Valid Department department, @RequestParam Long id) {
        //department.setCreationUid(UserUtils.getCurrentUser().getId());
        if (!StringUtils.isEmpty(department.getCreationTime())) {
            department.setTime(Date.valueOf(department.getCreationTime()));
        }
        RespBody<Object> respBody = new RespBody<>(departmentService.creationDep(department));
        respBody.put(1, department)
                .put(0, "添加失败!")
                .put(-1, "添加失败!没有该节点权限或父节点不存在");
        String finalFilePath = department.getLogo();
        respBody.processing((code, msg) -> {
            if (code <= 0) {
                if (finalFilePath != null) {
                    FileUtil.deleteFile("." + finalFilePath);
                }
            }
        });
        return respBody;
    }

    @PutMapping(value = "/dep/{putId}")
    @ApiOperation(value = "更新单位信息")
    @ApiImplicitParam(paramType = "path", name = "putId", required = true, value = "单位id", dataType = "Long")
    public RespBody updateDep(Department department, @PathVariable Long putId) {
        RespBody<Object> respBody = new RespBody<>(departmentService.updateDep(department, putId));
        respBody.put(1, department)
                .put(0, "更新失败!")
                .put(-1, "更新失败!没有该节点权限或父节点不存在")
                .put(-2, "更新失败!请至少更新一个属性（字段）");
        respBody.processing((code, msg) -> {
            if (code <= 0) {
                if (department.getLogo() != null) {
                    FileUtil.deleteFile("." + department.getLogo());
                }
            }
        });
        return respBody;
    }

    @DeleteMapping(value = "/dep/{id}")
    @ApiOperation(value = "删除单位", notes = "删除前请确保不存在子单位且单位下不存在用户")
    @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "单位id", dataType = "Long")
    public RespBody<String> deleteDep(@PathVariable Long id) {
        return new RespBody<String>(departmentService.deleteDep(id))
                .put(1, "删除成功!")
                .put(0, "删除失败!单位未找到或没有该单位操作权限")
                .put(-1, "删除失败!单位未找到")
                .put(-2, "删除失败!存在子节点或存在用户");
    }

    @GetMapping(value = "/dep")
    @ApiOperation(value = "获取该用户的所在单位及子单位")
    public RespBody<PageView<Department>> getDeps(PageQuery pageQuery) {
        return new RespBody<>(1, departmentService.getDeptByUid(pageQuery));
    }

}
