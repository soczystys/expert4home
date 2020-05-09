package com.redteam.expert4home.experts.dto;

import com.redteam.expert4home.orders.dto.JobOrderDTO;
import lombok.*;
import org.springframework.hateoas.EntityModel;

import java.util.List;

/**
 * General DTO for User entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO extends EntityModel<UserDTO> {
    private Long id;

    private String name;
    private String surname;
    private String login;
    private Boolean expertMode;
    private String profession;
    private String description;
    private int rank;
    private List<JobOrderDTO> jobOrderDTOList;
}
