package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Integer>, BoardCustomRepository {
    List<Board> findBoard(BoardParamDto boardParamDto);

    int countBoard(String searchWord);

    int countByTitleLike(String searchWord);
}
