package com.redteam.expert4home.dao;

import com.redteam.expert4home.dao.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
