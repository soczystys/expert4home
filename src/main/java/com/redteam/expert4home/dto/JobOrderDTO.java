package com.redteam.expert4home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
