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
    public void fillDatabaseWithInitialData() {
        System.out.println("Now data shall be added");
        List<User> userList = saveUsers(20);
        saveOrders(userList, 60);
    }

    private void saveOrders(List<User> userList, int orderCount) {
        LocalDateTime currentDate = LocalDateTime.now();
        List<JobOrder> jobOrderList = new ArrayList<>();
//        userRepository.findAll().forEach(userList::add);
        for (int i = orderCount; i > 0; i--) {
            User user = userList.get(i % userList.size());
            String state;
            switch (i % 5) {
                default: state = "CREATED"; break;
                case 1: state = "ACCEPTED"; break;
                case 2: state = "REJECTED"; break;
                case 3: state = "COMPLETED"; break;
                case 4: state = "COMMENTED"; break;
            }
            JobOrder jobOrder = new JobOrder(
                    currentDate.minusDays(i),
                    currentDate.minusDays(i),
                    currentDate.plusDays(i),
                    (i % 5 < 3 ? currentDate.plusDays(i%8) : currentDate.minusDays(i%8)),
                    i % 2 == 0,
                    getOrderText(20 * (1 + i % 10)),
                    user,
                    "awesome.user" + (i % userList.size()) + "@gmail.com",
                    state,
                    (state.equals("COMMENTED") ? getOrderText(10 * (1 + i % 12)) : "")
            );
            jobOrderList.add(jobOrder);
            user.getPlacedOrders().add(jobOrder);
            userRepository.save(user);
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
                    i % 2 == 0,
                    "profession"+i,
                    "description",
                    i%5
            );
            userList.add(user);
            userRepository.save(user);
        }
        return userList;
    }

    private String getOrderText(int length) {
        return ("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc fermentum sit amet ipsum at porta. " +
                "Proin cursus id neque sed egestas. Maecenas odio justo, lobortis sit amet ultricies vel, ullamcorper " +
                "quis nisi. Cras risus elit, lacinia sed hendrerit at, hendrerit id justo. Etiam tincidunt odio vitae " +
                "sollicitudin gravida. Sed iaculis odio et ex tincidunt, vel iaculis odio volutpat. Nullam sit amet " +
                "tincidunt ex, quis viverra eros. Nunc a erat mollis, sagittis libero et, vehicula ante.").substring(0, length);
    }
}
