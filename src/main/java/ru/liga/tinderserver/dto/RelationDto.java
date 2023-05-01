package ru.liga.tinderserver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationDto {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long selectedUserId;
    private boolean sympathy;
}
