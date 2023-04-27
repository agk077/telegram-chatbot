package ru.liga.tinderserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "user", schema = "hw_tinder")
@Data
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "\"desc\"", columnDefinition = "TEXT")
    private String desc;
    @Column(name = "gender")
    private String gender;
    @Column(name = "gender_for_search")
    private String genderForSearch;

}
