package com.redteam.expert4home.controllers;

import com.redteam.expert4home.user.User;
import com.redteam.expert4home.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebController {

    @Autowired
    private UserRepository userRepository;

    @Value("${message.text}")
    public String message;

    @GetMapping("/home")
    public ModelAndView showSignUpForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("messageFinally", message);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("users")
    public ModelAndView showUsers() {
        System.out.println("elements: " + userRepository.count());
        userRepository.findAll().forEach(System.out::println);
        Iterable<User> users = userRepository.findAll();
        List<User> list = new ArrayList<>();
        if (users != null) {
            users.forEach(list::add);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userslist", users);
        modelAndView.setViewName("users");
        return modelAndView;
    }

    @GetMapping("login")
    public ModelAndView showLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("register")
    public ModelAndView showRegister() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames( "classpath:/application.properties");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(5);
        return messageSource;
    }
}
