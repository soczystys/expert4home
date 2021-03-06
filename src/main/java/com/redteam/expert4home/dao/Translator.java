package com.redteam.expert4home.dao;

import com.redteam.expert4home.experts.controller.ExpertsController;
import com.redteam.expert4home.dao.entity.JobOrder;
import com.redteam.expert4home.dao.entity.User;
import com.redteam.expert4home.orders.controller.OrdersController;
import com.redteam.expert4home.orders.dto.JobOrderDTO;
import com.redteam.expert4home.experts.dto.UserDTO;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
                .description(user.getDescription())
                .profession(user.getProfession())
                .rank(user.getRank())
                .jobOrderDTOList(user.getPlacedOrders().stream()
                    .map(this::createJobOrderDTO)
                    .collect(Collectors.toList()))
                .build();

        val selfLink = linkTo(methodOn(ExpertsController.class).getSingleUser(userDTO.getId())).withSelfRel();
        userDTO.add(selfLink);
//        TODO: Add links to orders

        return userDTO;
    }

    public JobOrderDTO createJobOrderDTO(JobOrder jobOrder) {
        val jobOrderDto = JobOrderDTO.builder()
                .id(jobOrder.getId())
                .creationDate(jobOrder.getCreationDate())
                .acceptationDate(jobOrder.getAcceptationDate())
                .description(jobOrder.getDescription())
                .done(jobOrder.getDone())
                .description(jobOrder.getDescription())
                .dueDate(jobOrder.getDueDate())
                .startDate(jobOrder.getStartDate())
                .comment(jobOrder.getComment())
                .state(jobOrder.getState())
                .contact(jobOrder.getContact())
                .build();

        val selfLink = linkTo(methodOn(OrdersController.class).getSingleOrder(jobOrderDto.getId())).withSelfRel();
        val expertLink = linkTo(methodOn(ExpertsController.class).getSingleUser(jobOrder.getExpert().getId())).withRel("expert");
        jobOrderDto.add(selfLink, expertLink);

        return jobOrderDto;
    }
}
