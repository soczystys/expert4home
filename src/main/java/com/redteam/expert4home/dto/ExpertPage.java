package com.redteam.expert4home.dto;

import com.redteam.expert4home.dao.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@Data
@EqualsAndHashCode (callSuper = true)
public class ExpertPage extends EntityModel<ExpertPage> {
    private int pageSize;
    private int currentPageIndex;
    private int totalPageCount;
    private List<UserDTO> experts;
}
