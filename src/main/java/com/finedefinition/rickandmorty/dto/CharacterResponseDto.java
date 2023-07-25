package com.finedefinition.rickandmorty.dto;

import com.finedefinition.rickandmorty.model.Gender;
import com.finedefinition.rickandmorty.model.Status;
import lombok.Data;

@Data
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
