package com.ssafy.trippals.board.dto;

import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.board.entity.BoardFile;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardFileDto {
    private int fileId;
    private int boardSeq;
    private String fileName;
    private long fileSize;
    private String fileContentType;
    private String fileUUID;
    private LocalDateTime regDt;

    public BoardFile convertToBoardFile() {
        return BoardFile.builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .fileContentType(fileContentType)
                .fileUuid(fileUUID)
                .createDate(regDt)
                .board(new Board(boardSeq))
                .build();
    }
}