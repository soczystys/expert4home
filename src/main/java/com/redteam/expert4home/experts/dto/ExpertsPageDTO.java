package com.redteam.expert4home.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@Data
@EqualsAndHashCode (callSuper = true)
public class ExpertsPageDTO extends EntityModel<ExpertsPageDTO> {
    private int pageSize;
    private int currentPageIndex;
    private int totalPageCount;
    private List<UserDTO> experts;
}
