package com.ssafy.trippals.board.service;

import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.dto.BoardResultDto;
import com.ssafy.trippals.board.dto.BoardUserVO;
import com.ssafy.trippals.board.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService{
    private final BookmarkRepository bookmarkRepository;

    @Override
    public BoardResultDto bookmarkList(BoardParamDto boardParamDto) {

        BoardResultDto boardResultDto = new BoardResultDto();

        try {
            boardResultDto.setList(bookmarkRepository.findByUserSeq(boardParamDto));
            boardResultDto.setCount(bookmarkRepository.countBookmark(boardParamDto));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return boardResultDto;
    }

    @Override
    public int bookmarkInsert(BoardUserVO boardUserVO) {
        return bookmarkRepository.insertBookmark(boardUserVO);
    }

    @Override
    public void bookmarkUpdate(BoardUserVO boardUserVO) {
        //bookmark 이름이 있다면 필요
    }
    @Override
    public void bookmarkDelete(BoardUserVO boardUserVO) {
        bookmarkRepository.deleteBookmark(boardUserVO);
    }

}
