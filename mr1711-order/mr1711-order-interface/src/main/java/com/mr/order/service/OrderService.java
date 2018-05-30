package com.mr.order.service;

import com.mr.model.OrderInfo;
import com.mr.util.DataResult;

/**
 * Created by ydd on 2018/5/24.
 */
public interface OrderService {

    DataResult  create(OrderInfo orderInfo);

}
