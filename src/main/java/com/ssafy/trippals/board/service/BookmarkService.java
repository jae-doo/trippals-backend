package com.ssafy.trippals.board.service;


import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.dto.BoardParamDto;

import java.util.List;

public interface BookmarkService {
    List<BoardDto> bookmarkList(BoardParamDto boardParamDto);

    int bookmarkInsert(int boardId,int userSeq);
    void bookmarkUpdate(int boardId,int userSeq);
    void bookmarkDelete(int boardId,int userSeq);

//    int countBookmark(int userSeq);
}
