package com.mebay.controller;


import com.mebay.bean.Menu;
import com.mebay.common.UserUtils;
import com.mebay.service.MenuService;
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
