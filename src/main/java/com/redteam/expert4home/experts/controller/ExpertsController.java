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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class ExpertsController {

    private final UserRepository userRepository;
    private final Translator dtoTranslator;

    @Autowired
    public ExpertsController(UserRepository userRepository, Translator dtoTranslator) {
        this.userRepository = userRepository;
        this.dtoTranslator = dtoTranslator;
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(value -> ResponseEntity.ok(dtoTranslator.createUserDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("#login == authentication.name")
    @GetMapping(path = "/secure/{login}")
    public ResponseEntity<UserDTO> getSingleUserSecured(@PathVariable String login) {
        Optional<User> user = userRepository.findFirstByLogin(login);

        return user.map(value -> ResponseEntity.ok(dtoTranslator.createUserDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/mylogin/{login}")
    public ResponseEntity<UserDTO> getUserByLogin(@PathVariable String login) {
//        Optional<User> user = userRepository.findByLogin(login);
        Optional<User> userOptional = userRepository.findFirstByLogin(login);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println(user.getId() + "\n" + user.getLogin() + "\n" + user.getName());
        } else {
            System.out.println("YOUR USER IS NOONE");
        }

        return userOptional.map(value -> ResponseEntity.ok(dtoTranslator.createUserDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
//        return ResponseEntity.ok(dtoTranslator.createUserDTO(user));
    }

    @GetMapping("/current")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("credentials\n" + authentication.getCredentials());
        System.out.println("authorities\n" + authentication.getAuthorities());
        System.out.println("name\n" + authentication.getName());
        System.out.println("details\n" + authentication.getDetails());

        return authentication.getName();
    }

    @PreAuthorize("#userHandle == authentication.name")
    @PostMapping
    public ResponseEntity<UserDTO> postSingleUser(@RequestBody User user) {
        Optional<User> userOptional = userRepository.findFirstByLogin(user.getLogin());
        if (!userOptional.isPresent()) {
            userRepository.save(user);
            return ResponseEntity.ok(dtoTranslator.createUserDTO(user));
        } else {
            return ResponseEntity.notFound().build();
        }
//        return userOptional.map(value -> {
//
//        }).orElseGet(() -> ResponseEntity.notFound().build());
    }



    @PutMapping(path = "{id}")
    public ResponseEntity putSingleUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> oldUser = userRepository.findById(id);

        System.out.println(oldUser);
        System.out.println(user);
        System.out.println(oldUser.isPresent());
        if (user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (oldUser.isPresent()) {
            if (user.getName() != null) {
                if (!user.getName().isEmpty()) {
                    oldUser.get().setName(user.getName());
                }
            }
            if (user.getSurname() != null) {
                if (!user.getSurname().isEmpty()) {
                    oldUser.get().setSurname(user.getSurname());
                }
            }
            userRepository.save(oldUser.get());
            System.out.println(oldUser);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.IM_USED);
        }
    }

    @GetMapping
    public ResponseEntity<?> getExpertsList(
            @RequestParam(name = "pageSize") Optional<Integer> pageSizeOptional,
            @RequestParam(name = "pageIndex") Optional<Integer> currentPageIndexOptional) {

        if (!pageSizeOptional.isPresent() || !currentPageIndexOptional.isPresent()) {
            Iterable<User> iterator = userRepository.findAll();
            List<UserDTO> experts = StreamSupport
                    .stream(iterator.spliterator(), false)
                    .map(user -> dtoTranslator.createUserDTO(user))
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
