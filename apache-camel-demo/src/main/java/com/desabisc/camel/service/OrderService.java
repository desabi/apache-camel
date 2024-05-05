package com.desabisc.camel.service;

import com.desabisc.camel.dto.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private List<Order> list  = new ArrayList<>();

    @PostConstruct
    public void initDB() {
        list.add(new Order(1, "book1", 99.98));
        list.add(new Order(2, "book2", 99.98));
        list.add(new Order(3, "book3", 99.98));
        list.add(new Order(4, "book3", 99.98));
    }

    // POST
    public Order addOrder(Order order) {
        list.add(order);
        return order;
    }

    //GET
    public List<Order> getOrder() {
        return list;
    }
}
