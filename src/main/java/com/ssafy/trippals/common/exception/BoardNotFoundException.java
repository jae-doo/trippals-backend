package com.ssafy.trippals.common.exception;

public class BoardNotFoundException extends RuntimeException {
    public static final String message = "게시글을 찾을 수 없습니다.";

    public BoardNotFoundException() {
        super(message);
    }
}
