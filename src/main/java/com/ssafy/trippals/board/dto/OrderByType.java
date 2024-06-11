package com.ssafy.trippals.board.dto;

import java.util.Arrays;

public enum OrderByType {
    POPULAR, LATEST;

    public static OrderByType generate(String orderBy) {
        return Arrays.stream(OrderByType.values())
                .filter(o -> o.name().equals(orderBy.toUpperCase()))
                .findAny()
                .orElse(LATEST);
    }
}
