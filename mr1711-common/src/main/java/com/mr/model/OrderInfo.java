package com.mr.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ydd on 2018/5/24.
 */
@Data
public class OrderInfo extends TbOrder implements Serializable{

    private TbOrderShipping orderShipping;

    private List<TbOrderItem>  orderItems;




}
