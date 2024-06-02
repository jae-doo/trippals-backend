package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
