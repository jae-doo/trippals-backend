package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.board.entity.BoardFile;

import java.util.List;

public interface BoardCustomRepository {
    Long findReadByUser(int boardSeq, int userSeq);
    List<Board> findBoard(BoardParamDto boardParamDto);
    Long countBoard(String keyword);
}
