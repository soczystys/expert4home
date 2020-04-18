package com.redteam.expert4home.dao;

import com.redteam.expert4home.controllers.ApiController;
import com.redteam.expert4home.dao.entity.JobOrder;
import com.redteam.expert4home.dao.entity.User;
import com.redteam.expert4home.dto.JobOrderDTO;
import com.redteam.expert4home.dto.UserDTO;
import lombok.val;
import org.springframework.stereotype.Service;

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

        val selfLink = linkTo(methodOn(ApiController.class).getSingleUser(userDTO.getId())).withSelfRel();
        userDTO.add(selfLink);
//        TODO: Add links to orders

        return userDTO;
    }

    public JobOrderDTO createJobOrderDTO(JobOrder jobOrder) {
        val jobOrderDto = JobOrderDTO.builder()
                .creationDate(jobOrder.getCreationDate())
                .acceptationDate(jobOrder.getAcceptationDate())
                .description(jobOrder.getDescription())
                .done(jobOrder.getDone())
                .description(jobOrder.getDescription())
                .dueDate(jobOrder.getDueDate())
                .startDate(jobOrder.getStartDate())
                .build();

//        val selfLink = linkTo(methodOn(ApiController.class).getSingeUser(jobOrderDto.getId())).withSelfRel();
//        userDTO.add(selfLink);
//        TODO: Add links to orders

        return jobOrderDto;
    }
}
