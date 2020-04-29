package com.redteam.expert4home.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;
    private String surname;
    private String login;
    private String passwordHash;
    private Boolean expertMode;
    private String profession;
    private String description;
    private int rank;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<JobOrder> placedOrders;

    public User(
            String name,
            String surname,
            String login,
            String passwordHash,
            Boolean expertMode,
            String profession,
            String description,
            int rank) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.passwordHash = passwordHash;
        this.expertMode = expertMode;
        this.placedOrders = new LinkedList<>();
        this.profession = profession;
        this.description = description;
        this.rank = rank;
    }
}
