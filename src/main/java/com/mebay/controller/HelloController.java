package com.mebay.controller;

import com.mebay.bean.Menu;
import com.mebay.bean.RespBody;
import com.mebay.common.FileUtil;
import com.mebay.config.UrlFilterInvocationSecurityMetadataSource;
import com.mebay.service.MenuService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "其他杂项接口", description = "只有login无需jwt验证")
public class HelloController {
    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    private final MenuService menuService;
    @Value("${resourcesPath}")
    private String resourcesPath;
    @Autowired
    public HelloController(MenuService menuService, UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource) {
        this.menuService = menuService;
        this.urlFilterInvocationSecurityMetadataSource = urlFilterInvocationSecurityMetadataSource;
    }

    @ApiOperation(value = "登陆", notes = "测试账号：test，密码：admin\n")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "superiorName", value = "单位", required = true, dataType = "String")
    })
    @ApiResponse(code = 200, message = "成功")
    @PostMapping("/login")
    public Object login() {
        System.out.println("nmak");
        return "系统错乱";
    }

    @GetMapping("/menu")
    @ApiOperation(value = "获取该用户能访问的菜单", notes = "如果该用户未登陆就返回null")
    public RespBody<Menu> getMenu(){
        return new RespBody<>(1, menuService.getMenusByUserId());
    }

    @GetMapping("/update")
    public void update() {
        urlFilterInvocationSecurityMetadataSource.update();
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public RespBody<String> upload(@RequestParam(value = "file" ,required = false) MultipartFile file) {
        String path = resourcesPath + (FileUtil.getFileType(file) != null ? "/img" : "/file");
        String filePath = FileUtil.saveFile(file, path);
        if (filePath == null) {
            return new RespBody<>(0, "文件保存失败!请联系管理员");
        }else
            return new RespBody<>(1, filePath.replaceFirst("\\.", ""));

    }

}
