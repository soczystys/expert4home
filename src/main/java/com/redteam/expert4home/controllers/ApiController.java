package com.redteam.expert4home.controllers;

import com.google.gson.Gson;
import com.redteam.expert4home.dao.JobOrderRepository;
import com.redteam.expert4home.dao.Translator;
import com.redteam.expert4home.dao.UserRepository;
import com.redteam.expert4home.dao.entity.JobOrder;
import com.redteam.expert4home.dao.entity.User;
import com.redteam.expert4home.dto.ExpertsPageDTO;
import com.redteam.expert4home.dto.JobOrderDTO;
import com.redteam.expert4home.dto.OrdersPageDTO;
import com.redteam.expert4home.dto.UserDTO;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final JobOrderRepository jobOrderRepository;
    private final UserRepository userRepository;

    private final Translator dtoTranslator;
    private final Gson gson;

    @Autowired
    public ApiController(JobOrderRepository jobOrderRepository, UserRepository userRepository, Translator dtoTranslator) {
        this.jobOrderRepository = jobOrderRepository;
        this.userRepository = userRepository;
        this.dtoTranslator = dtoTranslator;
        this.gson = new Gson();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(value -> ResponseEntity.ok(dtoTranslator.createUserDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/order/{id}")
    public ResponseEntity<JobOrderDTO> getSingleOrder(@PathVariable Long id) {
        Optional<JobOrder> jobOrder = jobOrderRepository.findById(id);

        return jobOrder.map(value -> ResponseEntity.ok(dtoTranslator.createJobOrderDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrdersList(
            @RequestParam(name = "pageSize") Optional<Integer> pageSizeOptional,
            @RequestParam(name = "pageIndex") Optional<Integer> currentPageIndexOptional) {

        if (!pageSizeOptional.isPresent() || !currentPageIndexOptional.isPresent()) {
            Iterable<User> iterator = userRepository.findAll();
            List<User> experts = StreamSupport
                    .stream(iterator.spliterator(), false)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(gson.toJson(experts));

        }

        int currentPageIndex = currentPageIndexOptional.get();
        int pageSize = pageSizeOptional.get();
        Pageable pageable = PageRequest.of(currentPageIndex, pageSize);
        Page<JobOrder> jobOrders = jobOrderRepository.findAll(pageable);

        OrdersPageDTO ordersPageDTO = new OrdersPageDTO();
        ordersPageDTO.setPageSize(pageSize);
        ordersPageDTO.setCurrentPageIndex(currentPageIndex);
        ordersPageDTO.setOrders(jobOrders.get().map(dtoTranslator::createJobOrderDTO).collect(Collectors.toList()));

        if (jobOrders.hasNext()) {
            var linkToNex = linkTo(methodOn(ApiController.class).getExpertsList(pageSizeOptional, Optional.of(currentPageIndex + 1)))
                    .withRel("nextPage");
            ordersPageDTO.add(linkToNex);
        }
        if (jobOrders.hasPrevious()) {
            var linkToPrev = linkTo(methodOn(ApiController.class).getExpertsList(pageSizeOptional, Optional.of(currentPageIndex - 1)))
                    .withRel("previousPage");
            ordersPageDTO.add(linkToPrev);
        }
        var linkToFirst = linkTo(methodOn(ApiController.class).getExpertsList(pageSizeOptional, Optional.of(0)))
                .withRel("firstPage");
        ordersPageDTO.add(linkToFirst);
        var linkToLast = linkTo(methodOn(ApiController.class).getExpertsList(pageSizeOptional, Optional.of(jobOrders.getTotalPages() - 1)))
                .withRel("lastPage");
        ordersPageDTO.add(linkToLast);

        return ResponseEntity.ok(ordersPageDTO);

    }


    @GetMapping("/experts")
    public ResponseEntity<?> getExpertsList(
            @RequestParam(name = "pageSize") Optional<Integer> pageSizeOptional,
            @RequestParam(name = "pageIndex") Optional<Integer> currentPageIndexOptional) {

        if (!pageSizeOptional.isPresent() || !currentPageIndexOptional.isPresent()) {
            Iterable<User> iterator = userRepository.findAll();
            List<User> experts = StreamSupport
                    .stream(iterator.spliterator(), false)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(gson.toJson(experts));
        }

        int currentPageIndex = currentPageIndexOptional.get();
        int pageSize = pageSizeOptional.get();
        Pageable pageable = PageRequest.of(currentPageIndex, pageSize);
        Page<User> experts = userRepository.findAll(pageable);

        ExpertsPageDTO expertsPageDTO = new ExpertsPageDTO();
        expertsPageDTO.setPageSize(pageSize);
        expertsPageDTO.setCurrentPageIndex(currentPageIndex);
        expertsPageDTO.setExperts(experts.get().map(dtoTranslator::createUserDTO).collect(Collectors.toList()));

        if (experts.hasNext()) {
            var linkToNex = linkTo(methodOn(ApiController.class).getExpertsList(pageSizeOptional, Optional.of(currentPageIndex + 1)))
                    .withRel("nextPage");
            expertsPageDTO.add(linkToNex);
        }
        if (experts.hasPrevious()) {
            var linkToPrev = linkTo(methodOn(ApiController.class).getExpertsList(pageSizeOptional, Optional.of(currentPageIndex - 1)))
                    .withRel("previousPage");
            expertsPageDTO.add(linkToPrev);
        }
        var linkToFirst = linkTo(methodOn(ApiController.class).getExpertsList(pageSizeOptional, Optional.of(0)))
                .withRel("firstPage");
        expertsPageDTO.add(linkToFirst);
        var linkToLast = linkTo(methodOn(ApiController.class).getExpertsList(pageSizeOptional, Optional.of(experts.getTotalPages() - 1)))
                .withRel("lastPage");
        expertsPageDTO.add(linkToLast);

        return ResponseEntity.ok(expertsPageDTO);

    }
}
