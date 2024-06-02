package com.ssafy.trippals.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    private String fileName;
    private Long fileSize;
    private String fileContentType;
    private String fileUuid;

    @Column(name = "reg_dt")
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "board_seq")
    private Board board;
}
