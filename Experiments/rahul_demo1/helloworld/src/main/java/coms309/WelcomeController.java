package coms309;

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

    @GetMapping("/idea")
    public String idea()
    {
        return "Welcome to cystudy, our very own app that will help you find study resources and study groups for any course taken at Iowa State ";
    }
}
