package com.redteam.expert4home.controllers;

import com.google.gson.Gson;
import com.redteam.expert4home.dao.entity.User;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private Gson gson = new Gson();

    @GetMapping("/expert")
    public ResponseEntity  getExpertsList() {

        List<User> experts = Arrays.asList(new User("John", "Travolta", "jt", "afaaf", false));

        return ResponseEntity.ok(gson.toJson(experts));
    }
}
