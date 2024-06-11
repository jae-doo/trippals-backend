package com.ssafy.trippals.board.service;

import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.common.page.dto.PageResponse;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface BoardService {
    PageResponse<BoardDto> boardList(BoardParamDto boardParamDto);
    BoardDto boardDetail(int boardSeq,int userSeq);
    void boardInsert(BoardDto dto, MultipartHttpServletRequest request);
    void boardUpdate(BoardDto dto, MultipartHttpServletRequest request);
    void boardDelete(int boardId,int userSeq);
}

