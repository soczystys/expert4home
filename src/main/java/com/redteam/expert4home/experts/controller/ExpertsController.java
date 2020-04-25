package com.redteam.expert4home.experts.controller;

import com.redteam.expert4home.dao.Translator;
import com.redteam.expert4home.dao.UserRepository;
import com.redteam.expert4home.dao.entity.User;
import com.redteam.expert4home.experts.dto.ExpertsPageDTO;
import com.redteam.expert4home.experts.dto.UserDTO;
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
public class ExpertsController {

    private final UserRepository userRepository;
    private final Translator dtoTranslator;

    @Autowired
    public ExpertsController(UserRepository userRepository, Translator dtoTranslator) {
        this.userRepository = userRepository;
        this.dtoTranslator = dtoTranslator;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(value -> ResponseEntity.ok(dtoTranslator.createUserDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/experts")
    public ResponseEntity<?> getExpertsList(
            @RequestParam(name = "pageSize") Optional<Integer> pageSizeOptional,
            @RequestParam(name = "pageIndex") Optional<Integer> currentPageIndexOptional) {

        if (!pageSizeOptional.isPresent() || !currentPageIndexOptional.isPresent()) {
            Iterable<User> iterator = userRepository.findAll();
            List<User> experts = StreamSupport
                    .stream(iterator.spliterator(), false)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(experts);
        }

        int currentPageIndex = currentPageIndexOptional.get();
        int pageSize = pageSizeOptional.get();
        Pageable pageable = PageRequest.of(currentPageIndex, pageSize);
        Page<User> experts = userRepository.findAll(pageable);

        ExpertsPageDTO expertsPageDTO = setupPageDto(currentPageIndex, pageSize, experts);

        addLinks(pageSizeOptional, currentPageIndex, experts, expertsPageDTO);

        return ResponseEntity.ok(expertsPageDTO);
    }

    private ExpertsPageDTO setupPageDto(int currentPageIndex, int pageSize, Page<User> experts) {
        ExpertsPageDTO expertsPageDTO = new ExpertsPageDTO();
        expertsPageDTO.setPageSize(pageSize);
        expertsPageDTO.setCurrentPageIndex(currentPageIndex);
        expertsPageDTO.setExperts(experts.get().map(dtoTranslator::createUserDTO).collect(Collectors.toList()));
        return expertsPageDTO;
    }

    private void addLinks(@RequestParam(name = "pageSize") Optional<Integer> pageSizeOptional, int currentPageIndex, Page<User> experts, ExpertsPageDTO expertsPageDTO) {
        if (experts.hasNext()) {
            var linkToNex = linkTo(methodOn(ExpertsController.class).getExpertsList(pageSizeOptional, Optional.of(currentPageIndex + 1)))
                    .withRel("nextPage");
            expertsPageDTO.add(linkToNex);
        }
        if (experts.hasPrevious()) {
            var linkToPrev = linkTo(methodOn(ExpertsController.class).getExpertsList(pageSizeOptional, Optional.of(currentPageIndex - 1)))
                    .withRel("previousPage");
            expertsPageDTO.add(linkToPrev);
        }
        var linkToFirst = linkTo(methodOn(ExpertsController.class).getExpertsList(pageSizeOptional, Optional.of(0)))
                .withRel("firstPage");
        expertsPageDTO.add(linkToFirst);
        var linkToLast = linkTo(methodOn(ExpertsController.class).getExpertsList(pageSizeOptional, Optional.of(experts.getTotalPages() - 1)))
                .withRel("lastPage");
        expertsPageDTO.add(linkToLast);
    }
}
