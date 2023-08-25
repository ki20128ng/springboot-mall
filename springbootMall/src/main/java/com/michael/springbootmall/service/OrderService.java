package com.michael.springbootmall.service;

import com.michael.springbootmall.dto.CreateOrderRequest;
import com.michael.springbootmall.dto.OrderQueryParms;
import com.michael.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParms orderQueryParms);
    Integer countOrder(OrderQueryParms orderQueryParms);
}
