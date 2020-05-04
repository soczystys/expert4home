package com.redteam.expert4home.security;

import com.redteam.expert4home.dao.UserRepository;
import com.redteam.expert4home.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findFirstByLogin(login);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("Not found: " + login));
        return new MyUserDetails(userOptional.get());
    }
}
