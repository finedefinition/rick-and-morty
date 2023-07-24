package com.finedefinition.rickandmorty.controller;

import com.finedefinition.rickandmorty.model.MovieCharacter;
import com.finedefinition.rickandmorty.service.HttpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
    private final HttpClient httpClient;

    public DemoController(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @GetMapping
    public String runDemo() {
        httpClient.get("https://rickandmortyapi.com/api/character", MovieCharacter.class);
        return "Done!";
    }
}




