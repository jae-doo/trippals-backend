package com.ssafy.trippals.board.service;

import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.board.entity.BoardBookmark;
import com.ssafy.trippals.board.repository.BookmarkRepository;
import com.ssafy.trippals.common.page.dto.PageParams;
import com.ssafy.trippals.common.page.dto.PageResponse;
import com.ssafy.trippals.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService{
    private final BookmarkRepository bookmarkRepository;

    @Override
    @Transactional
    public PageResponse<BoardDto> bookmarkList(int userSeq, PageParams pageParams) {
        List<BoardDto> contents = bookmarkRepository.findByUserSeq(userSeq, pageParams)
                .stream().map(BoardDto::new).toList();
        int count = bookmarkRepository.countBookmark(userSeq);

        return new PageResponse<>(contents, pageParams, count);
    }

    @Override
    public void bookmarkInsert(int boardId,int userSeq) {
        bookmarkRepository.save(new BoardBookmark(new Board(boardId), new User(userSeq)));
    }

    @Override
    public void bookmarkDelete(int boardSeq,int userSeq) {
        bookmarkRepository.deleteByBoardSeqAndUserSeq(boardSeq, userSeq);
    }

}
