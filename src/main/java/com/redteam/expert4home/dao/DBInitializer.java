package com.redteam.expert4home.dao;

import com.redteam.expert4home.dao.entity.JobOrder;
import com.redteam.expert4home.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        List<User> userList = saveUsers(20);
        saveOrders(userList, 60);
    }

    private void saveOrders(List<User> userList, int orderCount) {
        LocalDateTime currentDate = LocalDateTime.now();
        List<JobOrder> jobOrderList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        for (int i = orderCount; i > 0; i--) {
            User user = userList.get(i % userList.size());
            JobOrder jobOrder = new JobOrder(
                    currentDate.minusDays(i),
                    currentDate.minusDays(i),
                    currentDate.plusDays(i),
                    currentDate.plusDays(i),
                    i % 2 == 0,
                    "description" + i,
                    user,
                    "contact"+i+"gmail.com",
                    "CREATED",
                    "comment"+i
            );
            jobOrderList.add(jobOrder);
            user.getPlacedOrders().add(jobOrder);
        }
        jobOrderRepository.saveAll(jobOrderList);
    }

    private List<User> saveUsers(int userCount) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            User user = new User(
                    "user" + i,
                    "surname" + i,
                    "login" + i,
                    "passwordHash" + i,
                    i % 2 == 0
            );
            userList.add(user);
            userRepository.save(user);
        }
        return userList;
    }
}
