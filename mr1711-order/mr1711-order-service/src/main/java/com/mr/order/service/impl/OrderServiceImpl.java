package com.mr.order.service.impl;

import com.mr.mapper.TbOrderItemMapper;
import com.mr.mapper.TbOrderMapper;
import com.mr.mapper.TbOrderShippingMapper;
import com.mr.model.OrderInfo;
import com.mr.model.TbOrderItem;
import com.mr.model.TbOrderShipping;
import com.mr.order.redis.JedisClient;
import com.mr.order.service.OrderService;
import com.mr.util.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by ydd on 2018/5/24.
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Transactional
    @Override
    public DataResult  create(OrderInfo orderInfo) {
        //1：增加订单
        //通过reids创建订单id
        if(!jedisClient.exists("orderId")){//没有
            jedisClient.set("orderId","10000");
        }
        //订单id
        String orderId = jedisClient.incr("orderId").toString();

        orderInfo.setOrderId(orderId);
        orderInfo.setPostFee("0.00");
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());

        orderMapper.insertSelective(orderInfo);

        //2：增加订单详情
        List<TbOrderItem> items = orderInfo.getOrderItems();
        for (TbOrderItem item : items) {
            //通过reids创建订单详情id
            if(!jedisClient.exists("orderItemId")){//没有
                jedisClient.set("orderItemId","10000");
            }
            //订单id
            String orderItemId = jedisClient.incr("orderItemId").toString();
            item.setId(orderItemId);
            item.setOrderId(orderId);

            orderItemMapper.insertSelective(item);
        }

        //3：增加订单的收货地址
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());

        orderShippingMapper.insertSelective(orderShipping);

        return DataResult.ok(orderId);
    }
}
