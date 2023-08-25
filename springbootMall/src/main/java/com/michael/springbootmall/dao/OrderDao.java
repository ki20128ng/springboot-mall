package com.michael.springbootmall.dao;

import com.michael.springbootmall.dto.CreateOrderRequest;
import com.michael.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId,Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
