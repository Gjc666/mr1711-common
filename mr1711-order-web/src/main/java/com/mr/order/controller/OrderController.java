package com.mr.order.controller;

import com.mr.model.TbItem;
import com.mr.model.OrderInfo;
import com.mr.order.service.OrderService;
import com.mr.util.CookieUtils;
import com.mr.util.DataResult;
import com.mr.util.JsonUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by ydd on 2018/5/24.
 */
@Controller
public class OrderController {

    @Value("${CARTCOOKIENAME}")
    private String CARTCOOKIENAME;

    @Autowired
    private OrderService orderService;

    //http://localhost:8091/order/order-cart.html
    @RequestMapping("/order/order-cart")
    public String orderCart(ModelMap map , HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request, CARTCOOKIENAME, true);
        map.put("cartList", JsonUtils.jsonToList(json , TbItem.class));
        return "order-cart";
    }

    ///order/create.html
    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public String create(OrderInfo orderInfo,ModelMap map){
        DataResult result = orderService.create(orderInfo);

        map.put("orderId",result.getData());
        map.put("payment","0.00");
        DateTime now = new DateTime();
        DateTime tomorrow = now.plusDays(1);
        map.put("date",tomorrow);
        return "success";
    }





}
