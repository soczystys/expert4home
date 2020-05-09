package com.redteam.expert4home.orders.controller;

import com.redteam.expert4home.dao.JobOrderRepository;
import com.redteam.expert4home.dao.Translator;
import com.redteam.expert4home.dao.UserRepository;
import com.redteam.expert4home.dao.entity.JobOrder;
import com.redteam.expert4home.orders.dto.JobOrderDTO;
import com.redteam.expert4home.orders.dto.OrdersPageDTO;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class OrdersController {

    private final JobOrderRepository jobOrderRepository;
    private final UserRepository userRepository;
    private final Translator dtoTranslator;

    @Autowired
    public OrdersController(JobOrderRepository jobOrderRepository, UserRepository userRepository, Translator dtoTranslator) {
        this.jobOrderRepository = jobOrderRepository;
        this.userRepository = userRepository;
        this.dtoTranslator = dtoTranslator;
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getSingleOrder(@PathVariable Long id) {
        Optional<JobOrder> jobOrder = jobOrderRepository.findById(id);

        return jobOrder.map(value -> ResponseEntity.ok(dtoTranslator.createJobOrderDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody JobOrderDTO jobOrderDTO, @RequestParam Long expertId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        val login = authentication.getName();
        val user = userRepository.findFirstByLogin(login);
        val expert = userRepository.findById(expertId);

        if (!user.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with provided id was not found");
        if (!expert.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expert with provided id was not found");


        val jobOrder = new JobOrder(
                jobOrderDTO.getCreationDate(),
                jobOrderDTO.getDueDate(),
                jobOrderDTO.getAcceptationDate(),
                jobOrderDTO.getStartDate(),
                jobOrderDTO.getDone(),
                jobOrderDTO.getDescription(),
                expert.get(),
                jobOrderDTO.getContact(),
                jobOrderDTO.getState(),
                jobOrderDTO.getComment()
        );
        jobOrderRepository.save(jobOrder);
        user.get().getPlacedOrders().add(jobOrder);
        userRepository.save(user.get());

        val createdOrderDto = dtoTranslator.createJobOrderDTO(jobOrder);
        return ResponseEntity.created(createdOrderDto.getRequiredLink("self").toUri()).body(createdOrderDto);
    }

    @PutMapping("/order/{id}")
    ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody JobOrderDTO jobOrderDTO) {
        val jobOrderOptional = jobOrderRepository.findById(id);
        if (!jobOrderOptional.isPresent())
            return ResponseEntity.notFound().build();

        val jobOrder = jobOrderOptional.get();
        if (jobOrderDTO.getAcceptationDate() != null) {
            jobOrder.setAcceptationDate(jobOrderDTO.getAcceptationDate());
        }
        if (jobOrderDTO.getComment() != null) {
            jobOrder.setComment(jobOrderDTO.getComment());
        }
        if (jobOrderDTO.getContact() != null) {
            jobOrder.setContact(jobOrderDTO.getContact());
        }
        if (jobOrderDTO.getCreationDate() != null) {
            jobOrder.setCreationDate(jobOrderDTO.getCreationDate());
        }
        if (jobOrderDTO.getDescription() != null) {
            jobOrder.setDescription(jobOrderDTO.getDescription());
        }
        if (jobOrderDTO.getDone() != null) {
            jobOrder.setDone(jobOrderDTO.getDone());
        }
        if (jobOrderDTO.getDueDate() != null) {
            jobOrder.setDueDate(jobOrderDTO.getDueDate());
        }
        if (jobOrderDTO.getStartDate() != null) {
            jobOrder.setStartDate(jobOrderDTO.getStartDate());
        }
        if (jobOrderDTO.getState() != null) {
            jobOrder.setState(jobOrderDTO.getState());
        }

        jobOrderRepository.save(jobOrder);
        return ResponseEntity.ok(dtoTranslator.createJobOrderDTO(jobOrder));
    }

    @PostMapping("/order/test")
    public ResponseEntity<?> createOrderTest(@RequestBody JobOrderDTO jobOrderDTO, @RequestParam Long expertId) {
        val login = "login1";
        val user = userRepository.findFirstByLogin(login);
        val expert = userRepository.findById(expertId);

        if (!user.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with provided id was not found");
        if (!expert.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expert with provided id was not found");


        val jobOrder = new JobOrder(
                jobOrderDTO.getCreationDate(),
                jobOrderDTO.getDueDate(),
                jobOrderDTO.getAcceptationDate(),
                jobOrderDTO.getStartDate(),
                jobOrderDTO.getDone(),
                jobOrderDTO.getDescription(),
                expert.get(),
                jobOrderDTO.getContact(),
                jobOrderDTO.getState(),
                jobOrderDTO.getComment()
        );
        jobOrderRepository.save(jobOrder);
        user.get().getPlacedOrders().add(jobOrder);
        userRepository.save(user.get());

        val createdOrderDto = dtoTranslator.createJobOrderDTO(jobOrder);
        return ResponseEntity.created(createdOrderDto.getRequiredLink("self").toUri()).body(createdOrderDto);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrdersList(
            @RequestParam(name = "pageSize") Optional<Integer> pageSizeOptional,
            @RequestParam(name = "pageIndex") Optional<Integer> currentPageIndexOptional) {

        if (!pageSizeOptional.isPresent() || !currentPageIndexOptional.isPresent()) {
            Iterable<JobOrder> iterator = jobOrderRepository.findAll();
            List<JobOrderDTO> orders = StreamSupport
                    .stream(iterator.spliterator(), false)
                    .map(dtoTranslator::createJobOrderDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(orders);
        }

        int currentPageIndex = currentPageIndexOptional.get();
        int pageSize = pageSizeOptional.get();
        Pageable pageable = PageRequest.of(currentPageIndex, pageSize);
        Page<JobOrder> jobOrders = jobOrderRepository.findAll(pageable);

        OrdersPageDTO ordersPageDTO = setupPageDto(currentPageIndex, pageSize, jobOrders);

        addLinks(pageSizeOptional, currentPageIndex, jobOrders, ordersPageDTO);

        return ResponseEntity.ok(ordersPageDTO);
    }

    private OrdersPageDTO setupPageDto(int currentPageIndex, int pageSize, Page<JobOrder> jobOrders) {
        OrdersPageDTO ordersPageDTO = new OrdersPageDTO();
        ordersPageDTO.setPageSize(pageSize);
        ordersPageDTO.setCurrentPageIndex(currentPageIndex);
        ordersPageDTO.setOrders(jobOrders.get().map(dtoTranslator::createJobOrderDTO).collect(Collectors.toList()));
        return ordersPageDTO;
    }

    private void addLinks(@RequestParam(name = "pageSize") Optional<Integer> pageSizeOptional, int currentPageIndex, Page<JobOrder> jobOrders, OrdersPageDTO ordersPageDTO) {
        if (jobOrders.hasNext()) {
            var linkToNext = linkTo(methodOn(OrdersController.class).getOrdersList(pageSizeOptional, Optional.of(currentPageIndex + 1)))
                    .withRel("nextPage");
            ordersPageDTO.add(linkToNext);
        }
        if (jobOrders.hasPrevious()) {
            var linkToPrev = linkTo(methodOn(OrdersController.class).getOrdersList(pageSizeOptional, Optional.of(currentPageIndex - 1)))
                    .withRel("previousPage");
            ordersPageDTO.add(linkToPrev);
        }
        var linkToFirst = linkTo(methodOn(OrdersController.class).getOrdersList(pageSizeOptional, Optional.of(0)))
                .withRel("firstPage");
        ordersPageDTO.add(linkToFirst);
        var linkToLast = linkTo(methodOn(OrdersController.class).getOrdersList(pageSizeOptional, Optional.of(jobOrders.getTotalPages() - 1)))
                .withRel("lastPage");
        ordersPageDTO.add(linkToLast);
    }
}
