package com.mr.controller;

import com.mr.model.TbItem;
import com.mr.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ydd on 2018/5/21.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("item/{id}")
    public String getItemById(@PathVariable  long id, ModelMap map){
        TbItem item = itemService.getItemById(id);
        map.put("item",item);
        return "item";
    }
}
