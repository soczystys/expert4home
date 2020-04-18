package com.redteam.expert4home.controllers;

import com.google.gson.Gson;
import com.redteam.expert4home.dao.JobOrderRepository;
import com.redteam.expert4home.dao.Translator;
import com.redteam.expert4home.dao.UserRepository;
import com.redteam.expert4home.dao.entity.User;
import com.redteam.expert4home.dto.ExpertPage;
import com.redteam.expert4home.dto.UserDTO;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<UserDTO> getSingeUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(value -> ResponseEntity.ok(dtoTranslator.createUserDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/expert")
    public ResponseEntity<?> getExpertsList(
            @RequestParam(name = "pageSize") Optional<Integer> pageSizeOptional,
            @RequestParam(name = "currentPageIndex") Optional<Integer> currentPageIndexOptional) {

        if (!pageSizeOptional.isPresent() || !currentPageIndexOptional.isPresent()) {
            Iterable<User> iterator = userRepository.findAll();
            List<User> experts = StreamSupport
                    .stream(iterator.spliterator(), false)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(gson.toJson(experts));
        }

        int currentPageIndex = currentPageIndexOptional.get();
        int pageSize = pageSizeOptional.get();
        Pageable pageable = PageRequest.of(currentPageIndex, pageSize);
        Page<User> experts = userRepository.findAll(pageable);

//            TODO: create association to next page and previous
        ExpertPage expertPage = new ExpertPage();
        expertPage.setPageSize(pageSize);
        expertPage.setCurrentPageIndex(currentPageIndex);
        expertPage.setExperts(experts.get().map(dtoTranslator::createUserDTO).collect(Collectors.toList()));

        if (experts.hasNext()) {
            var linkToNex = linkTo(methodOn(ApiController.class).getExpertsList(pageSizeOptional, Optional.of(pageSize + 1)))
                    .withRel("nextPage");
            expertPage.add(linkToNex);
        }
        if (experts.hasPrevious()) {
            var linkToPrev = linkTo(methodOn(ApiController.class).getExpertsList(pageSizeOptional, Optional.of(pageSize - 1)))
                    .withRel("previousPage");
            expertPage.add(linkToPrev);
        }


        return ResponseEntity.ok(expertPage);

    }
}
