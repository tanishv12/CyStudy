package coms309roundTrip.test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    public class hello{

        @GetMapping("/")
        public String index() {
            return "Greetings from Spring Boot!";
        }

    }



