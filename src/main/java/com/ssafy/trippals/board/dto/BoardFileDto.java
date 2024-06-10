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

    public BoardFileDto(BoardFile boardFile) {
        this.fileId = boardFile.getSeq();
        this.boardSeq = boardFile.getBoard().getSeq();
        this.fileName = boardFile.getFileName();
        this.fileSize = boardFile.getFileSize();
        this.fileContentType = boardFile.getFileContentType();
        this.fileUUID = boardFile.getFileUuid();
        this.regDt = boardFile.getCreateDate();
    }

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