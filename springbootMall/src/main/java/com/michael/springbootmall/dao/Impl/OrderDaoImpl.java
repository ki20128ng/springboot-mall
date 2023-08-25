package com.michael.springbootmall.dao.Impl;

import com.michael.springbootmall.dao.OrderDao;
import com.michael.springbootmall.dto.BuyItem;
import com.michael.springbootmall.dto.OrderQueryParms;
import com.michael.springbootmall.dto.ProductQueryParams;
import com.michael.springbootmall.model.Order;
import com.michael.springbootmall.model.OrderItem;
import com.michael.springbootmall.rowmapper.OrderItemRowMapper;
import com.michael.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate NP;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date)" +
                " VALUES (:user_id, :total_amount, :created_date, :last_modified_date)";

        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        map.put("total_amount",totalAmount);

        Date now = new Date();
        map.put("created_date",now);
        map.put("last_modified_date",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        NP.update(sql,new MapSqlParameterSource(map),keyHolder);
        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount) " +
                "VALUES (:order_id, :product_id, :quantity, :amount);";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0;i<orderItemList.size();i++){

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("order_id",orderId);
            parameterSources[i].addValue("product_id",orderItemList.get(i).getProductId());
            parameterSources[i].addValue("quantity",orderItemList.get(i).getQuantity());
            parameterSources[i].addValue("amount",orderItemList.get(i).getAmount());
        }
        NP.batchUpdate(sql,parameterSources);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "select order_id,user_id, total_amount, created_date, last_modified_date " +
                "from `order` where order_id = :orderId";

        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);

        List<Order> orderList = NP.query(sql,map,new OrderRowMapper());

        System.out.println(orderList.get(0));
        if(orderList.size() > 0){
            return orderList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsById(Integer orderId) {
        String sql = "select oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                "from order_item as oi " +
                "LEFT JOIN product as p on oi.product_id = p.product_id " +
                "where order_id = :orderId";

        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);

        List<OrderItem> orderItemList = NP.query(sql,map,new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public List<Order> getOrders(OrderQueryParms orderQueryParms) {
        String sql = "select order_id,user_id, total_amount, created_date, last_modified_date " +
                "from `order` where 1=1";

        Map<String,Object> map = new HashMap<>();

        sql = addFilteringSql(sql,map,orderQueryParms);

        //排序
        sql = sql + " Order by created_date desc";
        //分頁
        sql = sql + " Limit :limit offset :offset";
        map.put("limit",orderQueryParms.getLimit());
        map.put("offset",orderQueryParms.getOffset());

        List<Order> orderList = NP.query(sql,map,new OrderRowMapper());
        return orderList;

    }

    @Override
    public Integer countOrder(OrderQueryParms orderQueryParms) {
        String sql = "select count(*) from `order` where 1=1";
        Map<String,Object> map = new HashMap<>();

        sql = addFilteringSql(sql,map,orderQueryParms);

        Integer total = NP.queryForObject(sql,map, Integer.class);
        return total;

    }

    private String addFilteringSql(String sql, Map<String,Object> map, OrderQueryParms orderQueryParms){
        if(orderQueryParms.getUserId() != null){
            sql = sql + " and user_id = :userId";
            map.put("userId",orderQueryParms.getUserId());
        }

        return sql;
    }
}
