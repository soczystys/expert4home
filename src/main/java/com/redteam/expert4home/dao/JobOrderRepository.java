package com.redteam.expert4home.dao;

import com.redteam.expert4home.dao.entity.JobOrder;
import org.springframework.data.repository.CrudRepository;

public interface JobOrderRepository extends CrudRepository<JobOrder, Long> {
}
