package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.entity.BoardUserRead;
import com.ssafy.trippals.board.entity.BoardUserReadId;
import org.springframework.data.repository.CrudRepository;

public interface BoardUserReadRepository extends CrudRepository<BoardUserRead, BoardUserReadId> {
}
