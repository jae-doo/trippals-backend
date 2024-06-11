package com.ssafy.trippals.board.controller;

import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.service.BoardService;
import com.ssafy.trippals.board.service.BookmarkService;
import com.ssafy.trippals.common.SessionConst;
import com.ssafy.trippals.common.page.dto.PageResponse;
import com.ssafy.trippals.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BookmarkService bookmarkService;

    @GetMapping(value="/boards")
    public ResponseEntity<PageResponse<BoardDto>> boardList(BoardParamDto boardParamDto) {
        return ResponseEntity.ok(boardService.boardList(boardParamDto));
    }


    @GetMapping(value="/boards/{boardSeq}")
    public ResponseEntity<BoardDto> boardDetail(@PathVariable("boardSeq") int boardSeq, HttpSession session) {

        int userSeq=((UserDto) session.getAttribute(SessionConst.USER)).getSeq();

        BoardDto boardDto = boardService.boardDetail(boardSeq, userSeq);
        // 게시글 작성자와 현 사용자가 동일
        if( userSeq == boardDto.getUserSeq() ) {
            boardDto.setSameUser(true);
        }

        return ResponseEntity.ok(boardDto);
    }

    @PostMapping(value="/boards")
    @ResponseStatus(HttpStatus.OK)
    public void boardInsert(
            BoardDto boardDto,
            MultipartHttpServletRequest request) {

        boardDto.setUserSeq( ((UserDto) request.getSession().getAttribute(SessionConst.USER)).getSeq());
        System.out.println(boardDto);

        boardService.boardInsert(boardDto, request);
    }

    @PostMapping(value="/boards/{seq}")
    @ResponseStatus(HttpStatus.OK)
    public void boardUpdate(
            BoardDto boardDto,
            MultipartHttpServletRequest request){
        boardDto.setUserSeq( ((UserDto) request.getSession().getAttribute(SessionConst.USER)).getSeq());
        boardService.boardUpdate(boardDto, request);
    }

    @PostMapping(value="/boards/{boardSeq}/bookmark")
    @ResponseStatus(HttpStatus.OK)
    public void bookmarkInsert(HttpSession session,@PathVariable("boardSeq") int boardSeq){
        int userSeq=((UserDto) session.getAttribute(SessionConst.USER)).getSeq();
        bookmarkService.bookmarkInsert(boardSeq, userSeq);
    }

    //bookmark/{boardSeq} ??
    @DeleteMapping(value="/boards/{boardSeq}/bookmark")
    @ResponseStatus(HttpStatus.OK)
    public void bookmarkDelete(HttpSession session,@PathVariable("boardSeq") int boardSeq){
        int userSeq=((UserDto) session.getAttribute(SessionConst.USER)).getSeq();
        bookmarkService.bookmarkInsert(boardSeq, userSeq);
    }

    @DeleteMapping(value="/boards/{boardSeq}")
    @ResponseStatus(HttpStatus.OK)
    public void boardDelete(@PathVariable(value="boardSeq") int boardSeq,HttpSession session){
        UserDto userDto=(UserDto) session.getAttribute(SessionConst.USER);
        boardService.boardDelete(boardSeq, userDto.getSeq());
    }
}


