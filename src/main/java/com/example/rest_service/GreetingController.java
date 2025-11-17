package com.example.rest_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();
    private static final String template = "Hello, %s!";
    private final int myInt = this.myInt;

    public record Greeting(long id, String content, int myInt){}

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(defaultValue = "World")String name, @RequestParam(defaultValue = "70")int myInt){
        return new Greeting(counter.incrementAndGet(),String.format(template,name),myInt);
    } //To pass multiple variables do : localhost:8080/greeting?name=NewString&myInt=68
}
