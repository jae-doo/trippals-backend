package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.entity.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Integer>, BoardCustomRepository {
}
