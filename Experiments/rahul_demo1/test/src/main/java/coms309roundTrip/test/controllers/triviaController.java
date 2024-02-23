package coms309roundTrip.test.controllers;
import coms309roundTrip.test.models.trivia;
import coms309roundTrip.test.repository.triviaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class triviaController {

    @Autowired
    triviaRepository triviaRepository;

    @GetMapping("trivia/all")
    List<trivia> GetAllTrivias(){
            return triviaRepository.findAll();
    }

    @PostMapping("trivia/post/{q}/{a}")
    trivia PostTriviaByPath(@PathVariable String q, @PathVariable String a){
        trivia newTrivia = new trivia();
        newTrivia.setQuestion(q);
        newTrivia.setAnswer(a);
        triviaRepository.save(newTrivia);
        return newTrivia;
    }
    @PostMapping("trivia/post")
    trivia PostTriviaByPath(@RequestBody trivia newTrivia){
        triviaRepository.save(newTrivia);
        return newTrivia;
    }
}
