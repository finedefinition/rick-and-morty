package com.finedefinition.rickandmorty.controller;

import com.finedefinition.rickandmorty.dto.CharacterResponseDto;
import com.finedefinition.rickandmorty.dto.mapper.MovieCharacterMapper;
import com.finedefinition.rickandmorty.model.MovieCharacter;
import com.finedefinition.rickandmorty.service.MovieCharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper movieCharacterMapper;

    public MovieCharacterController(MovieCharacterService movieCharacterService
            , MovieCharacterMapper movieCharacterMapper) {
        this.movieCharacterService = movieCharacterService;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @GetMapping("/random")
//    @Operation(summary = "get random character")
    public CharacterResponseDto getRandomCharacter() {
        MovieCharacter randomCharacter = movieCharacterService.getRandomCharacter();
        return movieCharacterMapper.toDto(randomCharacter);
    }

}
