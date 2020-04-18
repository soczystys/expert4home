package com.redteam.expert4home.dao;

import com.redteam.expert4home.controllers.ApiController;
import com.redteam.expert4home.dao.entity.User;
import com.redteam.expert4home.dto.UserDTO;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class for mapping DB entities to DTO
 */
@Service
public class Translator {

    public UserDTO createUserDTO(User user)
    {
        val userDTO = UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .surname(user.getSurname())
                .expertMode(user.getExpertMode())
                .build();

        val selfLink = linkTo(methodOn(ApiController.class).getSingeUser(userDTO.getId())).withSelfRel();
        userDTO.add(selfLink);
//        TODO: Add links to orders

        return userDTO;
    }
}
