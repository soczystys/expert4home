package com.redteam.expert4home.dao;

import com.redteam.expert4home.dao.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findFirstByLogin(String login);
    List<User> findByName(String name);
}
