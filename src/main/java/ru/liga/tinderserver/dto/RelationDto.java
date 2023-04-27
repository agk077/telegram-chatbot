package ru.liga.tinderserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationDto {
    private Long userId;
    private Long selectedUserId;
    private boolean sympathy;
}
