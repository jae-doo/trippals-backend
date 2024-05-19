package com.ssafy.trippals.board.controller;

import com.ssafy.trippals.SessionConst;
import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.dto.BoardResultDto;
import com.ssafy.trippals.board.dto.BoardUserVO;
import com.ssafy.trippals.board.service.BoardService;
import com.ssafy.trippals.board.service.BookmarkService;
import com.ssafy.trippals.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService service;
    private final BookmarkService bookmarkService;

    @GetMapping(value="/users/bookmarks")
    public ResponseEntity<List<BoardDto>> boardListByUser(HttpSession session){

        int userSeq=((UserDto) session.getAttribute(SessionConst.USER)).getSeq();
        BoardResultDto boardResultDto=new BoardResultDto();//pagination?

        return ResponseEntity.ok(bookmarkService.bookmarkList(userSeq));
    }
    @GetMapping(value="/boards")
    public ResponseEntity<BoardResultDto> boardList(BoardParamDto boardParamDto){

        BoardResultDto boardResultDto;

        if( boardParamDto.getSearchWord()==null ) {
            boardResultDto = service.boardList(boardParamDto);
        }else {
            boardResultDto = service.boardListSearchWord(boardParamDto);
            System.out.println(boardParamDto);
        }
        return ResponseEntity.ok(boardResultDto);
    }


    @GetMapping(value="/boards/{boardSeq}")
    public BoardResultDto boardDetail(@PathVariable("boardSeq") int boardSeq, HttpSession session){

        BoardParamDto boardParamDto = new BoardParamDto();
        boardParamDto.setUserSeq( ((UserDto) session.getAttribute(SessionConst.USER)).getSeq());
        boardParamDto.setBoardSeq(boardSeq);

        BoardResultDto boardResultDto = service.boardDetail(boardParamDto);
        // 게시글 작성자와 현 사용자가 동일
        if( ((UserDto) session.getAttribute(SessionConst.USER)).getSeq() == boardResultDto.getDto().getUserSeq() ) {
            boardResultDto.getDto().setSameUser(true);
        }

        return boardResultDto;
    }

    @PostMapping(value="/boards")
    //modelAttribute(queryString,formData)
    //requestParam(Java Type)
    //requestBody(JSON)
    public BoardResultDto boardInsert(
            BoardDto boardDto,
            MultipartHttpServletRequest request) {

        boardDto.setUserSeq( ((UserDto) request.getSession().getAttribute(SessionConst.USER)).getSeq());

        BoardResultDto boardResultDto = service.boardInsert(boardDto, request);

        return boardResultDto;
    }

    // PUT + multipart/form-data (X)
    // In RESTful,
    // PUT & DELETE methods are defined to be idempotent

    @PostMapping(value="/boards/{seq}")
    //pathVariable dto.seq에 자동할당
    public BoardResultDto boardUpdate(
            BoardDto boardDto,
            MultipartHttpServletRequest request){
        boardDto.setUserSeq( ((UserDto) request.getSession().getAttribute(SessionConst.USER)).getSeq());
        BoardResultDto boardResultDto = service.boardUpdate(boardDto, request);

        return boardResultDto;
    }

    @PostMapping(value="/boards/{boardSeq}/bookmark")
    @ResponseStatus(HttpStatus.OK)
    public void bookmarkInsert(HttpSession session,@PathVariable("boardSeq") int boardSeq){
        int userSeq=((UserDto) session.getAttribute(SessionConst.USER)).getSeq();

        BoardUserVO boardUserVO=new BoardUserVO(boardSeq,userSeq);
        bookmarkService.bookmarkInsert(boardUserVO);
    }

    //bookmark/{boardSeq} ??
    @DeleteMapping(value="/boards/{boardSeq}/bookmark")
    @ResponseStatus(HttpStatus.OK)
    public void bookmarkDelete(HttpSession session,@PathVariable("boardSeq") int boardSeq){
        int userSeq=((UserDto) session.getAttribute(SessionConst.USER)).getSeq();

        BoardUserVO boardUserVO=new BoardUserVO(boardSeq,userSeq);
        bookmarkService.bookmarkInsert(boardUserVO);
    }

    @DeleteMapping(value="/boards/{boardSeq}")
    public BoardResultDto boardDelete(@PathVariable(value="boardSeq") int boardSeq,HttpSession session){
        UserDto userDto=(UserDto) session.getAttribute(SessionConst.USER);
        BoardResultDto boardResultDto = service.boardDelete(boardSeq, userDto.getSeq());

        return boardResultDto;
    }
}

