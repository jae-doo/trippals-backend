package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.dto.BoardFileDto;
import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.board.entity.BoardFile;

import java.util.List;

public interface BoardCustomRepository {
    int findReadByUser(BoardParamDto boardParamDto);
    void insertReadByUser(Integer boardSeq, Integer userSeq);
    void boardReadCountUpdate(Integer boardSeq);
    Board findBoardBySeq(Integer boardSeq);
    List<BoardFile> boardDetailFileList(Integer seq);
    List<Board> findBoardByTitleLike(BoardParamDto boardParamDto);
}
