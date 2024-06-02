package com.ssafy.trippals.board.dto;

import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.route.entity.Route;
import com.ssafy.trippals.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Integer seq;
    private Integer userSeq;
    private String title;
    private String content;
    private LocalDateTime regDt;
    private boolean isDraft;
    private int readCount;
    private String writer;
    private List<BoardFileDto> fileList;
    private boolean sameUser;

    private Integer routeSeq;
    private String routeName;
    private String thumbnail;
    private String overview;
    private String startDate;

    public Board convertToBoard() {
        return Board.builder()
                .seq(seq)
                .title(title)
                .content(content)
                .readCount(readCount)
                .isDraft(isDraft)
                .createdTime(LocalDateTime.now())
                .user(new User(userSeq))
                .route(new Route(routeSeq))
                .build();
    }
}
