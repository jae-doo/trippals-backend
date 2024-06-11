package com.ssafy.trippals.board.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BoardUserRead {
    @EmbeddedId
    private BoardUserReadId id;
}
