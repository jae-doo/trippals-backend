package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.entity.BoardBookmark;
import org.springframework.data.repository.Repository;

public interface BookmarkRepository extends Repository<BoardBookmark, Integer>, BookmarkCustomRepository {
    boolean existsBoardBookmarkByBoardSeqAndUserSeq(int boardSeq, int userSeq);
}
