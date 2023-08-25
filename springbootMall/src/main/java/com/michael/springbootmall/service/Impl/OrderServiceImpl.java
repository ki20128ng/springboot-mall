package com.michael.springbootmall.service.Impl;


import com.michael.springbootmall.dao.OrderDao;
import com.michael.springbootmall.dao.ProductDao;
import com.michael.springbootmall.dao.UserDao;
import com.michael.springbootmall.dto.BuyItem;
import com.michael.springbootmall.dto.CreateOrderRequest;
import com.michael.springbootmall.dto.OrderQueryParms;
import com.michael.springbootmall.model.Order;
import com.michael.springbootmall.model.OrderItem;
import com.michael.springbootmall.model.Product;
import com.michael.springbootmall.model.User;
import com.michael.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        User user = userDao.getUserById(userId);
        if(user == null){
            log.warn("該userId {} 不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int TotalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem: createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            if(product == null){
                log.warn("商品 {} 不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if(product.getStock()<buyItem.getQuantity()){
                log.warn("商品 {} 庫存僅剩餘 {} ,無法購買",buyItem.getProductId(),product.getStock());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productDao.updateStock(product.getProductId(),product.getStock()-buyItem.getQuantity());

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

    @Override
    public Order getOrderById(Integer orderId) {
        System.out.println(orderId);
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemsById(orderId);
        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override
    public List<Order> getOrders(OrderQueryParms orderQueryParms) {
        List<Order> orderList = orderDao.getOrders(orderQueryParms);

        for (Order order : orderList){
            List<OrderItem> orderItemList = orderDao.getOrderItemsById(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParms orderQueryParms) {
        return orderDao.countOrder(orderQueryParms);
    }
}
