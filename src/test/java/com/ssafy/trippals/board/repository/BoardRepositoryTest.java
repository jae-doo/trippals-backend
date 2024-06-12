package com.ssafy.trippals.board.repository;

import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.board.entity.BoardUserRead;
import com.ssafy.trippals.board.entity.BoardUserReadId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest {
    @Autowired private BoardRepository boardRepository;
    @Autowired private TestEntityManager em;

    @Test
    @DisplayName("게시글 읽음 확인")
    public void userReadTest() {
        // given
        em.persist(new BoardUserRead(new BoardUserReadId(10, 10)));
        em.flush();

        // when
        Long expectRead = boardRepository.findReadByUser(10, 10);
        Long expectNotRead = boardRepository.findReadByUser(10, 9);

        // then
        assertNotEquals(0, expectRead);
        assertEquals(0, expectNotRead);
    }
}