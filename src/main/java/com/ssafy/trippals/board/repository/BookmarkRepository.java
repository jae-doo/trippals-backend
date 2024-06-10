package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.board.entity.BoardBookmark;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface BookmarkRepository extends Repository<BoardBookmark, Integer> {
    boolean existsBoardBookmarkByBoardSeqAndUserSeq(int boardSeq, int userSeq);
    List<Board> findByUserSeq(BoardParamDto bookmarkDto);
}
