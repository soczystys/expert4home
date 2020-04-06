package com.redteam.expert4home.dao;

import com.redteam.expert4home.dao.entity.User;
import lombok.val;
import lombok.var;
import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class DBInitializer {

    private UserRepository userRepository;

    @Autowired
    public DBInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    private void fillDatabaseWithInitialData() {
        System.out.println("Now data shall be added");

        User joe = new User("Joe", "Malinowski", "joeM23", "asdfaas", false);
        User tom = new User("Tom", "Scot", "coolguy220", "gjklj37784", false);
        User jerry = new User("Jerry", "Wise", "expertNumberOne", "aflkjnvvnx", true);

        userRepository.saveAll(Arrays.asList(joe, tom, jerry));
    }
}
