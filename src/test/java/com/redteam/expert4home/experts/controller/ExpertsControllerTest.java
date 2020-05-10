package com.redteam.expert4home.experts.controller;

import com.redteam.expert4home.Expert4homeApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Expert4homeApplication.class)
class ExpertsControllerTest {

    @Autowired
    private ExpertsController expertsController;

    @Test
    void contextLoads() {
        assertNotNull(expertsController);
    }
}