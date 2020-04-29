package com.redteam.expert4home.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class JobOrder {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    public JobOrder(
            LocalDateTime creationDate,
            LocalDateTime dueDate,
            LocalDateTime acceptationDate,
            LocalDateTime startDate,
            Boolean done,
            String description,
            User expert,
            String contact,
            String state,
            String comment
    ) {
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.acceptationDate = acceptationDate;
        this.startDate = startDate;
        this.done = done;
        this.description = description;
        this.expert = expert;
        this.contact = contact;
        this.comment = comment;
        this.state = state;
    }

    private LocalDateTime creationDate;

    private LocalDateTime dueDate;

    private LocalDateTime acceptationDate;

    private LocalDateTime startDate;

    private Boolean done;

    private String description;

    private String state;

    private String contact;

    private String comment;

    @OneToOne
    private User expert;
}
