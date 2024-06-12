package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.common.page.dto.PageParams;

import java.util.List;

public interface BookmarkCustomRepository {
    List<Board> findByUserSeq(int userSeq, PageParams pageParams);

    int countBookmark(int userSeq);
}
