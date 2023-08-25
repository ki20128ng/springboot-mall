package com.michael.springbootmall.service.Impl;


import com.michael.springbootmall.dao.OrderDao;
import com.michael.springbootmall.dao.ProductDao;
import com.michael.springbootmall.dto.BuyItem;
import com.michael.springbootmall.dto.CreateOrderRequest;
import com.michael.springbootmall.model.OrderItem;
import com.michael.springbootmall.model.Product;
import com.michael.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int TotalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem: createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            //計算總價錢
            int amount = buyItem.getQuantity()*product.getPrice();
            TotalAmount = TotalAmount + amount;

            //轉換BuyItem to orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setAmount(amount);
            orderItem.setQuantity(buyItem.getQuantity());

            orderItemList.add(orderItem);

        }

        Integer orderId = orderDao.createOrder(userId,TotalAmount);

        orderDao.createOrderItems(orderId,orderItemList);
        return orderId;
    }
}
