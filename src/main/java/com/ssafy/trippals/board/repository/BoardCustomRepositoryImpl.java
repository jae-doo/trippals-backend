package com.ssafy.trippals.board.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.trippals.board.dto.BoardParamDto;
import com.ssafy.trippals.board.dto.OrderByType;
import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.board.entity.BoardFile;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ssafy.trippals.board.entity.QBoard.board;
import static com.ssafy.trippals.board.entity.QBoardUserRead.boardUserRead;

@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findReadByUser(int boardSeq, int userSeq) {
        return queryFactory.select(boardUserRead.count())
                .from(boardUserRead)
                .where(boardUserRead.id.boardSeq.eq(boardSeq),
                        boardUserRead.id.userSeq.eq(userSeq))
                .fetchOne();
    }

    @Override
    public List<Board> findBoard(BoardParamDto boardParamDto) {
        OrderByType orderByType = OrderByType.generate(boardParamDto.getOrderBy());

        return queryFactory.selectFrom(board)
                .where(board.title.contains(boardParamDto.getSearchWord()))
                .orderBy(getOrder(orderByType, "asc".equals(boardParamDto.getAscDesc())))
                .limit(boardParamDto.getLimit())
                .offset(boardParamDto.getOffset())
                .fetch();
    }

    @Override
    public Long countBoard(String keyword) {
        return queryFactory.select(board.count())
                .from(board)
                .where(board.title.contains(keyword))
                .fetchOne();
    }

    private OrderSpecifier getOrder(OrderByType orderByType, boolean isAsc) {
        switch (orderByType) {
            case POPULAR -> {
                return isAsc ? board.readCount.asc() : board.readCount.desc();
            }

            default -> {
                return isAsc ? board.createdTime.asc() : board.createdTime.desc();
            }
        }
    }
}
