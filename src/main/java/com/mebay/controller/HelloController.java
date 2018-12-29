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

import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "其他杂项接口", description = "只有login无需jwt验证")
public class HelloController {
    private final MenuService menuService;
    @Value("${resourcesPath}")
    private String resourcesPath;
    @Value("${resourcesTempPath}")
    private String resourcesTempPath;
    @Autowired
    public HelloController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "登陆", notes = "测试账号：test，密码：admin\n")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String", example = "test"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String", example = "admin"),
            @ApiImplicitParam(paramType = "query", name = "superiorName", value = "单位", required = true, dataType = "String", example = "重庆铭贝科技有限公司")
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

    @GetMapping("/menus")
    public RespBody<List<Menu>> getMenus() {
        return new RespBody<>(1, menuService.getMenus(), "成功");
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public RespBody<String> upload(@RequestParam(value = "file" ,required = false) MultipartFile file, @RequestParam(value = "name", required = false) String name) {
        String path = resourcesPath + (FileUtil.getFileType(file) != null ? "/img" : "/file");
        String filePath = FileUtil.saveFile(file, path, name);
        if (filePath == null) {
            return new RespBody<>(0, "文件保存失败!请联系管理员");
        }else
            return new RespBody<>(1, filePath.replaceFirst("\\.", ""), "文件上传成功");
    }

    @GetMapping("/fileInfo")
    @ApiOperation(value = "获取文件的信息")
    @ApiImplicitParam(paramType = "query", name = "files", required = true, value = "文件数组", dataType = "String")
    public RespBody<List<Map<String, Object>>> getFileInfo(@RequestParam List<String> files){
        List<Map<String, Object>> fileList = new ArrayList<>(files.size());
        for (String s : files) {
            BasicFileAttributes info = FileUtil.getFileInfo("." + s);
            if (info == null) {
                continue;
            }
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("type", s.substring(s.lastIndexOf(".") + 1));
            infoMap.put("size", info.size());
            infoMap.put("creationTime", info.creationTime().toString());
            infoMap.put("lastAccessTime", info.lastAccessTime().toString());
            infoMap.put("lastModifiedTime", info.lastModifiedTime().toString());
            fileList.add(infoMap);
        }
        return new RespBody<>(1, fileList, "成功");
    }

}
