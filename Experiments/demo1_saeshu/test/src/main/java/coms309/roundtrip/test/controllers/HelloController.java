package coms309.roundtrip.test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }

    @GetMapping("/project")
    public String group() {
        return "Hello! This is our project CyStudy where we help you find the perfect study group!!! ";
    }

}
