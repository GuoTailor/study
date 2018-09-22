package com.mebay.controller.system;

import com.mebay.Constant;
import com.mebay.bean.Department;
import com.mebay.bean.RespBean;
import com.mebay.common.FileUtil;
import com.mebay.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/system")
public class SystemDepController {
    final private DepartmentService departmentService;

    @Autowired
    public SystemDepController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping(value = "/dep")
    public RespBean addDep(@Valid Department department, @RequestParam("file") MultipartFile file) {
        RespBean respBean = new RespBean();
        String filePath = null;
        if (FileUtil.getFileType(file) != null) {
            try {
                filePath = FileUtil.saveFile(file, ResourceUtils.getURL("classpath:").getPath() + "static/images/logo");
                if (filePath == null) {
                    respBean.setStatus(Constant.NotModified);
                    respBean.setMessage("图片保存失败!请联系管理员");
                    return respBean;
                } else {
                    department.setLogoPath(filePath);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        int i = departmentService.creationDep(department);
        if (i == 1) {
            respBean.setStatus(200);
            respBean.setMessage(department);
            return respBean;
        }
        else if (i == 0) {
            if (filePath != null) {
                FileUtil.deleteFile(filePath);
            }
            return new RespBean(Constant.NotModified, "添加失败!");
        } else {
            if (filePath != null) {
                FileUtil.deleteFile(filePath);
            }
            return new RespBean(Constant.RESCODE_EXCEPTION_DATA, "添加失败!没有该节点权限或父节点不存在");
        }
    }

    @PutMapping(value = "/dep/{did}")
    public RespBean updateDep(Department department, @PathVariable Long did, @RequestParam("file") MultipartFile file) {
        String filePath = null;
        if (FileUtil.getFileType(file) != null) {
            try {
                filePath = FileUtil.saveFile(file, ResourceUtils.getURL("classpath:").getPath() + "static/images/logo");
                if (filePath == null) {
                    return new RespBean(Constant.NotModified, "图片保存失败!请联系管理员");
                } else {
                    department.setLogoPath(filePath);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        int i = departmentService.updateDep(department, did);
        if (i == 1)
            return new RespBean(200, department);
        else if (i == 0) {
            if (filePath != null) {
                FileUtil.deleteFile(filePath);
            }
            return new RespBean(Constant.NotModified, "更新失败!");
        } else {
            if (filePath != null) {
                FileUtil.deleteFile(filePath);
            }
            return new RespBean(Constant.RESCODE_EXCEPTION_DATA, "更新失败!没有该节点权限或父节点不存在");
        }
    }

    @DeleteMapping(value = "/dep/{did}")
    public RespBean deleteDep(@PathVariable Long did) {
        if (departmentService.deleteDep(did) == 1) {
            return new RespBean("200", "删除成功!");
        }
        return new RespBean(Constant.NotModified, "删除失败!资源未找到");
    }

    @GetMapping(value = "/dep")
    public Department getDeps() {
        return departmentService.getDepByUid();
    }
}
