package com.redteam.expert4home.controllers;

import com.redteam.expert4home.dao.JobOrderRepository;
import com.redteam.expert4home.dao.Translator;
import com.redteam.expert4home.dao.entity.JobOrder;
import com.redteam.expert4home.dto.JobOrderDTO;
import com.redteam.expert4home.dto.OrdersPageDTO;
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
public class OrdersController {

    private final JobOrderRepository jobOrderRepository;
    private final Translator dtoTranslator;

    @Autowired
    public OrdersController(JobOrderRepository jobOrderRepository, Translator dtoTranslator) {
        this.jobOrderRepository = jobOrderRepository;
        this.dtoTranslator = dtoTranslator;
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
            Iterable<JobOrder> iterator = jobOrderRepository.findAll();
            List<JobOrder> orders = StreamSupport
                    .stream(iterator.spliterator(), false)
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
