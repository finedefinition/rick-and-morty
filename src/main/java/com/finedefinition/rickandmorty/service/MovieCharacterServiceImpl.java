package com.finedefinition.rickandmorty.service;

import com.finedefinition.rickandmorty.dto.ApiResponseDto;
import com.finedefinition.rickandmorty.repository.MovieCharacterRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {

    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;

    public MovieCharacterServiceImpl(HttpClient httpClient, MovieCharacterRepository movieCharacterRepository) {
        this.httpClient = httpClient;
        this.movieCharacterRepository = movieCharacterRepository;
    }

    @Override
    public void syncExternalCharacters() {
        ApiResponseDto apiResponseDto = httpClient
                .get("https://rickandmortyapi.com/api/character", ApiResponseDto.class);
      //  log.info("API response {}" , apiResponseDto);
    }
}
