package ru.liga.tinderserver.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Entity
@Table(name = "relation", schema = "hw_tinder")
@Data
@NoArgsConstructor
public class Relation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "user_id")
    private Long userId;
    @Column (name = "selected_user_id")
    private Long selectedUserId;
    @Column(name = "sympathy", columnDefinition = "boolean default false")
    private boolean sympathy;



}
