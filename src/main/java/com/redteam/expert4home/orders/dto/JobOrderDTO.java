package com.redteam.expert4home.orders.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobOrderDTO {
    private LocalDateTime creationDate;

    private LocalDateTime dueDate;

    private LocalDateTime acceptationDate;

    private LocalDateTime startDate;

    private Boolean done;

    private String description;

    private String state;

    private String contact;

    private String comment;
}
