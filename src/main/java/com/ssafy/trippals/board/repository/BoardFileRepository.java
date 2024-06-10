package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface BoardFileRepository extends CrudRepository<BoardFile, Integer> {
    List<BoardFile> findAllByBoardSeq(Integer boardSeq);
    void deleteAllByBoardSeq(Integer boardSeq);
}
