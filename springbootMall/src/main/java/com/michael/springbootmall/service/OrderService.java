package com.michael.springbootmall.service;

import com.michael.springbootmall.dto.CreateOrderRequest;
import com.michael.springbootmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
}
