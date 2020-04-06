package com.redteam.expert4home.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;
    private String surname;
    private String login;
    private String passwordHash;
    private Boolean expertMode;

    @OneToMany
    private List<JobOrder> placedOrders;

    public User(String name, String surname, String login, String passwordHash, Boolean expertMode) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.passwordHash = passwordHash;
        this.expertMode = expertMode;
        this.placedOrders = new LinkedList<>();
    }
}
