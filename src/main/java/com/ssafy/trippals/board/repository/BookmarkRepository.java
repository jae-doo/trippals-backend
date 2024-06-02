package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.board.entity.BoardBookmark;
import com.ssafy.trippals.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BoardBookmark, Integer> {
    boolean existsBoardBookmarkByBoardSeqAndUserSeq(int boardSeq, int userSeq);
}
