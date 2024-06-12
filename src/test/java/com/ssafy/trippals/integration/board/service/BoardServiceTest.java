package com.ssafy.trippals.integration.board.service;

import com.ssafy.trippals.board.dto.BoardDto;
import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.service.BoardService;
import com.ssafy.trippals.board.service.BoardServiceImpl;
import com.ssafy.trippals.common.file.FileUploadServiceImpl;
import com.ssafy.trippals.common.page.dto.PageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({BoardServiceImpl.class, FileUploadServiceImpl.class})
class BoardServiceTest {
    @Autowired BoardService boardService;

    @Test
    void boardList() {
        // given
        BoardParamDto boardParamDto = new BoardParamDto(10, 10, "", "popular", "asc");

        // when
        PageResponse<BoardDto> response = boardService.boardList(boardParamDto);

        // then
        assertThat(response.getContents()).hasSize(10);
        assertThat(response.getContents()).allMatch(boardDto -> boardDto.getTitle().contains("제목"));
    }

    @Test
    void boardDetail() {
        // given
        BoardParamDto boardParamDto = new BoardParamDto(10, 10, "", "popular", "asc");
        PageResponse<BoardDto> response = boardService.boardList(boardParamDto);
        BoardDto expected = response.getContents().get(0);

        // when
        BoardDto actual = boardService.boardDetail(expected.getSeq(), expected.getUserSeq());

        // then
        assertThat(actual.getSeq()).isEqualTo(expected.getSeq());
    }
}