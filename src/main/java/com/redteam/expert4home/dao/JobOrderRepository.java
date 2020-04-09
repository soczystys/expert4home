package com.redteam.expert4home.dao;

import com.redteam.expert4home.dao.entity.JobOrder;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobOrderRepository extends PagingAndSortingRepository<JobOrder, Long> {
}
