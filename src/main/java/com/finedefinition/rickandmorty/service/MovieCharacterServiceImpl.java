package com.finedefinition.rickandmorty.service;

import com.finedefinition.rickandmorty.dto.external.ApiCharacterDto;
import com.finedefinition.rickandmorty.dto.external.ApiResponseDto;
import com.finedefinition.rickandmorty.dto.mapper.MovieCharacterMapper;
import com.finedefinition.rickandmorty.model.MovieCharacter;
import com.finedefinition.rickandmorty.repository.MovieCharacterRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {

    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper movieCharacterMapper;

    public MovieCharacterServiceImpl(HttpClient httpClient,
                                     MovieCharacterRepository movieCharacterRepository,
                                     MovieCharacterMapper movieCharacterMapper) {
        this.httpClient = httpClient;
        this.movieCharacterRepository = movieCharacterRepository;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    //@Scheduled(cron = "0 0 8 * * ?")
    @Scheduled(cron = "*/30 * * * * ?")
    @PostConstruct
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

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = movieCharacterRepository.count();
        long randomId = (long) (Math.random() * count);
        return movieCharacterRepository.getById(randomId);
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return movieCharacterRepository.findAllByNameContains(namePart);
    }
}

