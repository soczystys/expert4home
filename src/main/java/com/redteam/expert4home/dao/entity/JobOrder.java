package com.redteam.expert4home.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class JobOrder {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    private Boolean done;

    private String description;

    @OneToOne
    private User expert;
}
