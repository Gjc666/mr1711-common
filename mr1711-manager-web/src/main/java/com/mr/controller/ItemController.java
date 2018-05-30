package com.mr.controller;

import com.mr.model.TbItem;
import com.mr.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by ydd on 2018/5/14.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * @PathVariable 从路径中获取参数
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/item/getItemById/{id}",method = RequestMethod.GET)
    public TbItem getItem(@PathVariable  Long id){
        TbItem item = itemService.getItemById(id);
        return item;
    }

    /**
     * 查询商品列表信息
     * @param page
     * @param rows
     */
    @ResponseBody
    @RequestMapping(value = "/item/list", method = RequestMethod.POST)
    public Map<String, Object> getList(Integer page , Integer rows){
        Map<String, Object> map = itemService.getList(page, rows);
        return map;
    }
}
