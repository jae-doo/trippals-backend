package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.entity.BoardBookmark;
import org.springframework.data.repository.CrudRepository;

public interface BookmarkRepository extends CrudRepository<BoardBookmark, Integer>, BookmarkCustomRepository {
    boolean existsBoardBookmarkByBoardSeqAndUserSeq(int boardSeq, int userSeq);
    void deleteByBoardSeqAndUserSeq(int boardSeq, int userSeq);
}
