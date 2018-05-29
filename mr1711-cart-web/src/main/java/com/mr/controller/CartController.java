package com.mr.controller;

import com.mr.model.TbItem;
import com.mr.service.ItemService;
import com.mr.util.CookieUtils;
import com.mr.util.DataResult;
import com.mr.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ydd on 2018/5/22.
 */
@Controller
public class CartController {

    @Autowired
    private ItemService itemService;


    @Value("${CARTCOOKIENAME}")
    private String CARTCOOKIENAME;
    @Value("${COOKIEMAXAGE}")
    private Integer COOKIEMAXAGE;

    ///cart/add/${item.id}
    @RequestMapping("/cart/add/{itemId}/{num}")
    public String add(@PathVariable long itemId, @PathVariable Integer num,
                      HttpServletRequest request , HttpServletResponse response){
        //1:从cookie中获取购物车中集合数据
        String json = CookieUtils.getCookieValue(request, CARTCOOKIENAME, true);
        boolean falg = false;
        List<TbItem> list = new ArrayList<TbItem>();
        if (StringUtils.isNotBlank(json)) {
            list = JsonUtils.jsonToList(json, TbItem.class);
            //2:判断商品在购物车中是否存在
            for (TbItem item : list) {
                if(item.getId() == itemId){//说明存在
                    //3：如果存在，则添加数量+n
                    item.setNum(item.getNum()+num);
                    falg = true;
                }
            }
        }
        if(!falg){//不存在
            //4：如果不存在，需要查询数据，通过id查询
            TbItem itemById = itemService.getItemById(itemId);
            //修改数量
            itemById.setNum(num);

            //5：将数据存放在list中。
            list.add(itemById);
        }


        //6：将list集合存放在cookie中
        CookieUtils.setCookie(request,response,CARTCOOKIENAME,JsonUtils.objectToJson(list),COOKIEMAXAGE,true);

        //7：返回成功页面
        return "cartSuccess";
    }

    //cart/cart.html
    @RequestMapping("/cart/cart")
    public String cartListPage(ModelMap map , HttpServletRequest request) {

        String json = CookieUtils.getCookieValue(request, CARTCOOKIENAME, true);

        map.put("cartList",StringUtils.isBlank(json)? null : JsonUtils.jsonToList(json , TbItem.class));

        return "cart";
    }

    ///cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".html
    @ResponseBody
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    public DataResult updateNum(@PathVariable long itemId , @PathVariable Integer num,
                                HttpServletRequest request , HttpServletResponse response){
        //1:从cookie中获取购物车中集合数据
        String json = CookieUtils.getCookieValue(request, CARTCOOKIENAME, true);
        List<TbItem>  list = JsonUtils.jsonToList(json, TbItem.class);
        //2:判断商品在购物车中是否存在
        for (TbItem item : list) {
            if(item.getId() == itemId){//说明存在
                //3：如果存在，则添加数量+n
                item.setNum(num);
                break;
            }
        }
        //6：将list集合存放在cookie中
        CookieUtils.setCookie(request,response,CARTCOOKIENAME,JsonUtils.objectToJson(list),COOKIEMAXAGE,true);
        return DataResult.ok();
    }


    @RequestMapping("/cart/delete/{itemId}")
    public String delete(@PathVariable long itemId,HttpServletRequest request , HttpServletResponse response){
        //1:从cookie中获取购物车中集合数据
        String json = CookieUtils.getCookieValue(request, CARTCOOKIENAME, true);
        List<TbItem>  list = JsonUtils.jsonToList(json, TbItem.class);
        //2:判断商品在购物车中是否存在
        for (TbItem item : list) {
            if(item.getId() == itemId){//说明存在
                //3：如果存在，则添加数量+n
                list.remove(item);
                break;
            }
        }
        //6：将list集合存放在cookie中
        CookieUtils.setCookie(request,response,CARTCOOKIENAME,JsonUtils.objectToJson(list),COOKIEMAXAGE,true);
        return "redirect:/cart/cart.html";
    }



}
