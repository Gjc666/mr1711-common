package com.mr.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ydd on 2018/5/21.
 */
@Controller
public class PageController {

    @RequestMapping("/page/login")
    public String toLoginPage( String url , ModelMap map){
        System.out.println(url);
        map.put("redirect",url);
        return "login";
    }

    @RequestMapping("/page/register")
    public String toRegisterPage(){
        return "register";
    }
}
