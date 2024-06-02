package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.dto.BoardFileDto;
import com.ssafy.trippals.board.dto.BoardParamDto;

import java.util.List;

public interface BoardCustomRepository {
    List<BoardDto> findBoard(BoardParamDto boardParamDto);
    int countBoard(String searchWord);
    List<BoardDto> findBoardBySearchWord(BoardParamDto boardParamDto);
    int countBySearchWord(String searchWord);

    int findReadByUser(BoardParamDto boardParamDto);

    void insertReadByUser(Integer boardSeq, Integer userSeq);

    void boardReadCountUpdate(Integer boardSeq);

    BoardDto findBoardBySeq(Integer boardSeq);

    List<BoardFileDto> boardDetailFileList(Integer seq);
}
