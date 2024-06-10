package com.ssafy.trippals.board.dto;

import com.ssafy.trippals.board.entity.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BoardResultDto {
	private BoardDto dto;
	private List<BoardDto> list;
	private int count;//pagination
	private boolean checkBookmark;

	public void setList(List<Board> list) {
		this.list = list.stream().map(BoardDto::new).toList();
	}
}
