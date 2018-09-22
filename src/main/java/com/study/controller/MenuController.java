package com.study.controller;


import com.study.bean.Menu;
import com.study.common.UserUtils;
import com.study.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
//@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    @GetMapping("/menu")
    public Menu getMenu(){
        return menuService.getMenusByUserId(UserUtils.getCurrentUser().getId());
    }
}
