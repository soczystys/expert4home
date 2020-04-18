package com.redteam.expert4home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

/**
 * General DTO for User entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO extends EntityModel<UserDTO> {
    private Long id;

    private String name;
    private String surname;
    private String login;
    private Boolean expertMode;
}
