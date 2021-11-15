package com.ilhak.musicstudio.controller;

import java.util.List;

import com.ilhak.musicstudio.model.User;
import com.ilhak.musicstudio.repository.UserRepository;
import com.ilhak.musicstudio.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    
    @GetMapping("/users")
    public List<User> all() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{id}")
    public User edit(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
            .map(user -> {
                //user.setUsername(newUser.getUsername());
                user.setBoards(newUser.getBoards());
                return userRepository.save(user);
            })
            .orElseGet(()->{

                newUser.setId(id);
                return userRepository.save(newUser);
            });
    }

}
