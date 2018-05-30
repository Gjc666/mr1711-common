package com.mr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ydd on 2018/5/14.
 */
@Controller
public class PageController {

    @RequestMapping("/page/toMainPage")
    public String toMainPage(){

        return "main";
    }

    @RequestMapping("/page/toItemListPage")
    public String toItemListPage(){
        return "item-list";
    }

}
