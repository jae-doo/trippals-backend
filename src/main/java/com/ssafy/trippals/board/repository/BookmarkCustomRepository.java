package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.dto.BoardUserVO;
import com.ssafy.trippals.board.entity.Board;

import java.util.List;

public interface BookmarkCustomRepository {
    List<Board> findByUserSeq(BoardParamDto bookmarkDto);

    int countBookmark(BoardParamDto boardParamDto);

    int insertBookmark(BoardUserVO boardUserVO);

    void deleteBookmark(BoardUserVO boardUserVO);
}
