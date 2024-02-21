package coms309.roundtrip.test.controllers;

import coms309.roundtrip.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;


    @GetMapping("user/all")
    List
}

