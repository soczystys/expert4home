package com.redteam.expert4home.controllers;

import com.google.gson.Gson;
import com.redteam.expert4home.dao.JobOrderRepository;
import com.redteam.expert4home.dao.Translator;
import com.redteam.expert4home.dao.UserRepository;
import com.redteam.expert4home.dao.entity.User;
import com.redteam.expert4home.dto.UserDTO;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final JobOrderRepository jobOrderRepository;
    private final UserRepository userRepository;

    private final Translator dtoTranslator;
    private final Gson gson;

    @Autowired
    public ApiController(JobOrderRepository jobOrderRepository, UserRepository userRepository, Translator dtoTranslator) {
        this.jobOrderRepository = jobOrderRepository;
        this.userRepository = userRepository;
        this.dtoTranslator = dtoTranslator;
        this.gson = new Gson();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getSingeUser(@PathVariable Long id)
    {
        val user = userRepository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dtoTranslator.createUserDTO(user.get()));
    }

    @GetMapping("/expert")
    public ResponseEntity<?> getExpertsList() {

        List<User> experts = Arrays.asList(new User("John", "Travolta", "jt", "afaaf", false));

        return ResponseEntity.ok(gson.toJson(experts));
    }
}
