package com.finedefinition.rickandmorty.service;

import com.finedefinition.rickandmorty.model.MovieCharacter;

public interface MovieCharacterService {
    void syncExternalCharacters();
    MovieCharacter getRandomCharacter();

}
