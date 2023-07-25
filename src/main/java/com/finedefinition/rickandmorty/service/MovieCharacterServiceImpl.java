package com.finedefinition.rickandmorty.service;

import com.finedefinition.rickandmorty.dto.ApiCharacterDto;
import com.finedefinition.rickandmorty.dto.ApiResponseDto;
import com.finedefinition.rickandmorty.dto.mapper.MovieCharacterMapper;
import com.finedefinition.rickandmorty.model.MovieCharacter;
import com.finedefinition.rickandmorty.repository.MovieCharacterRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {

    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper movieCharacterMapper;

    public MovieCharacterServiceImpl(HttpClient httpClient
            , MovieCharacterRepository movieCharacterRepository
            , MovieCharacterMapper movieCharacterMapper) {
        this.httpClient = httpClient;
        this.movieCharacterRepository = movieCharacterRepository;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @Override
    public void syncExternalCharacters() {
        log.info("syncExternalCharacters was invoked at " + LocalDateTime.now());
        ApiResponseDto apiResponseDto = httpClient.get("https://rickandmortyapi.com/api/character",
                ApiResponseDto.class);
        // todo: fetch all characters from DB where external id is in response
        // todo: if character is present - update
        // todo: else add to DB
        saveDtosToDb(apiResponseDto);

        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtosToDb(apiResponseDto);
        }
    }
    private void saveDtosToDb(ApiResponseDto responseDto) {
        Map<Long, ApiCharacterDto> externalsDtos = Arrays.stream(responseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalsDtos.keySet();

        List<MovieCharacter> existingCharacterDtos = movieCharacterRepository
                .findAllByExternalIdIn(externalIds);

        Set<Long> existingIds = existingCharacterDtos.stream()
                .map(MovieCharacter::getExternalId)
                .collect(Collectors.toSet());

        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> movieCharacterMapper.parseApiCharacterResponseDto(externalsDtos.get(i)))
                .collect(Collectors.toList());

        movieCharacterRepository.saveAll(charactersToSave);
    }
}

