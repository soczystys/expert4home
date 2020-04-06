package com.redteam.expert4home.dao;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DBInitializer {

    @EventListener(ApplicationReadyEvent.class)
    private void fillDatabaseWithInitialData() {
        System.out.println("Now data shall be added");
    }
}
