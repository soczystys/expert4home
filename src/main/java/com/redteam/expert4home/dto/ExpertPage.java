package com.redteam.expert4home.dto;

import com.redteam.expert4home.dao.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class ExpertPage {
    private int pageSize;
    private int currentPageIndex;
    private int totalPageCount;
    private String nextPage;
    private String previousPage;
    private List<User> experts;
}
