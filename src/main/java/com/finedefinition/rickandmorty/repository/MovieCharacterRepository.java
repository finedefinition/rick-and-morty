package com.finedefinition.rickandmorty.repository;

import com.finedefinition.rickandmorty.model.MovieCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
}
