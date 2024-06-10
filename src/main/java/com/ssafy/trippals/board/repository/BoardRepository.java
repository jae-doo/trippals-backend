package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findBoard(BoardParamDto boardParamDto);

    int countBoard(String searchWord);

    List<Board> findBoardByTitleLike(BoardParamDto boardParamDto);

    int countByTitleLike(String searchWord);
}
