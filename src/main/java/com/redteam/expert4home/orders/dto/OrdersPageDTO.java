package com.redteam.expert4home.orders.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrdersPageDTO extends EntityModel<OrdersPageDTO> {
    private int pageSize;
    private int currentPageIndex;
    private int totalPageCount;
    private List<JobOrderDTO> orders;
}
