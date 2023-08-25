package com.michael.springbootmall.controller;

import com.michael.springbootmall.dao.UserDao;
import com.michael.springbootmall.dto.CreateOrderRequest;
import com.michael.springbootmall.dto.OrderQueryParms;
import com.michael.springbootmall.model.Order;
import com.michael.springbootmall.model.User;
import com.michael.springbootmall.service.Impl.UserServiceImpl;
import com.michael.springbootmall.service.OrderService;
import com.michael.springbootmall.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(10) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){
        OrderQueryParms orderQueryParms = new OrderQueryParms();
        orderQueryParms.setUserId(userId);
        orderQueryParms.setLimit(limit);
        orderQueryParms.setOffset(offset);

        //取得訂單List
        List<Order> orderList = orderService.getOrders(orderQueryParms);
        //取得訂單總數
        Integer count = orderService.countOrder(orderQueryParms);

        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }


    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){

        Integer orderId = orderService.createOrder(userId,createOrderRequest);

        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }
}
