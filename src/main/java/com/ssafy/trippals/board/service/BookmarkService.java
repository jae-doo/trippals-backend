package com.ssafy.trippals.board.service;


import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.common.page.dto.PageParams;
import com.ssafy.trippals.common.page.dto.PageResponse;

import java.util.List;

public interface BookmarkService {
    PageResponse<BoardDto> bookmarkList(int userSeq, PageParams pageParams);

    void bookmarkInsert(int boardId,int userSeq);
    void bookmarkDelete(int boardId,int userSeq);
}
