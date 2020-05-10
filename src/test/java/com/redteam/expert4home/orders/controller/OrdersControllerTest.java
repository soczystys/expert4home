package com.redteam.expert4home.orders.controller;

import com.redteam.expert4home.Expert4homeApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Expert4homeApplication.class)
class OrdersControllerTest {

    @Autowired
    private OrdersController ordersController;

    @Test
    void contextLoads() {
        assertNotNull(ordersController);
    }
}