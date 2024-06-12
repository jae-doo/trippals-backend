package com.ssafy.trippals.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.trippals.board.entity.Board;
import com.ssafy.trippals.common.page.dto.PageParams;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.ssafy.trippals.board.entity.QBoard.board;
import static com.ssafy.trippals.board.entity.QBoardBookmark.boardBookmark;

public class BookmarkCustomRepositoryImpl implements BookmarkCustomRepository {

    private final JPAQueryFactory queryFactory;

    public BookmarkCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Board> findByUserSeq(int userSeq, PageParams pageParams) {
       return queryFactory.selectFrom(board)
                .join(board, boardBookmark.board)
                .on(boardBookmark.user.seq.eq(userSeq))
                .limit(pageParams.getLimit())
                .offset(pageParams.getOffset())
                .fetch();
    }

    @Override
    public int countBookmark(int userSeq) {
        return Math.toIntExact(queryFactory.select(boardBookmark.count())
                .from(boardBookmark)
                .where(boardBookmark.user.seq.eq(userSeq))
                .fetchOne());

    }
}
