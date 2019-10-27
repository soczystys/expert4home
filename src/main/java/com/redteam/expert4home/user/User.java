package com.redteam.expert4home.user;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Size(max = 30)
    @Column(unique = true, name = "NICKNAME")
    private String nickname;

    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    @Size(max = 8)
    private Integer passwordHashed;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPasswordHashed() {
        return passwordHashed;
    }
}

