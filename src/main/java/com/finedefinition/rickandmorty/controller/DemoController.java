package com.finedefinition.rickandmorty.controller;

import com.finedefinition.rickandmorty.dto.ApiResponseDto;
import com.finedefinition.rickandmorty.model.MovieCharacter;
import com.finedefinition.rickandmorty.service.HttpClient;
import com.finedefinition.rickandmorty.service.MovieCharacterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/demo")
public class DemoController {
    private final MovieCharacterService movieCharacterService;

    public DemoController(MovieCharacterService movieCharacterService) {
        this.movieCharacterService = movieCharacterService;
    }

    @GetMapping
    public String runDemo() {
        movieCharacterService.syncExternalCharacters();
        return "Done!";
    }
}




