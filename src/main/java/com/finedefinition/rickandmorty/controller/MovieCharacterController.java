package com.finedefinition.rickandmorty.controller;

import com.finedefinition.rickandmorty.dto.CharacterResponseDto;
import com.finedefinition.rickandmorty.dto.mapper.MovieCharacterMapper;
import com.finedefinition.rickandmorty.model.MovieCharacter;
import com.finedefinition.rickandmorty.service.MovieCharacterService;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Tutorial", description = "Rick and Morty management APIs")
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper movieCharacterMapper;

    public MovieCharacterController(MovieCharacterService movieCharacterService,
                                    MovieCharacterMapper movieCharacterMapper) {
        this.movieCharacterService = movieCharacterService;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @GetMapping("/random")
    @Operation(
            summary = "Retrieve a random Movie Character",
            description = "Get a random movie character object. The response is Movie Character object with id, name, status and gender.",
            tags = { "get" })
    public CharacterResponseDto getRandomCharacter() {
        MovieCharacter randomCharacter = movieCharacterService.getRandomCharacter();
        return movieCharacterMapper.toDto(randomCharacter);
    }

    @GetMapping("/by-name")
    @Operation(
            summary = "Retrieve a Movie Character by name",
            description = "Get movie character object by part of name. The response is list of Movie Character object with id, name, status and gender.",
            tags = { "get" })
    public List<CharacterResponseDto> findAllByName(@RequestParam("name") String namePart) {
        return movieCharacterService.findAllByNameContains(namePart)
                .stream()
                .map(movieCharacterMapper::toDto)
                .collect(Collectors.toList());
    }

}
