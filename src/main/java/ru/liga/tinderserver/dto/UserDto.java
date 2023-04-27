package ru.liga.tinderserver.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private String desc;
    private String gender;
    private String genderForSearch;

    public enum Gender {
        MALE, FEMALE
    }
}
