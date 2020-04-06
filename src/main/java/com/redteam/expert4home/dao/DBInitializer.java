package com.redteam.expert4home.dao;

import com.redteam.expert4home.dao.entity.JobOrder;
import com.redteam.expert4home.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DBInitializer {

    private UserRepository userRepository;
    private JobOrderRepository jobOrderRepository;

    @Autowired
    public DBInitializer(UserRepository userRepository, JobOrderRepository jobOrderRepository) {
        this.userRepository = userRepository;
        this.jobOrderRepository = jobOrderRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    private void fillDatabaseWithInitialData() {
        System.out.println("Now data shall be added");

        User joe = new User("Joe", "Malinowski", "joeM23", "asdfaas", false);
        User tom = new User("Tom", "Scot", "coolguy220", "gjklj37784", false);
        User jerry = new User("Jerry", "Wise", "expertNumberOne", "aflkjnvvnx", true);

        userRepository.saveAll(Arrays.asList(joe, tom, jerry));

        LocalDateTime currentDate = LocalDateTime.now();
        JobOrder fixCar = new JobOrder(currentDate, null, currentDate.plusDays(7), null, false, "My carr is smoking", jerry);
        JobOrder changeTires = new JobOrder(currentDate, null, currentDate.plusDays(7), null, false, "Winter is coming so I need winter tires", jerry);

        jobOrderRepository.saveAll(Arrays.asList(fixCar, changeTires));
        joe.getPlacedOrders().addAll(Arrays.asList(fixCar, changeTires));
        userRepository.save(joe);
    }
}
