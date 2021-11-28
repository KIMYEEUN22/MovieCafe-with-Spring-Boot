package com.example.board.dao;

import java.util.List;

import com.example.board.vo.BoardFileVO;
import com.example.board.vo.BoardVO;

public interface BoardDao {

	List<BoardVO> selectList(int cateNo);
	void insertBoard(BoardVO board);
	void insertBoardFile(BoardFileVO file);
	void insertBoardNewMem(BoardVO board);
	void insertBoardTip(BoardVO board);
	void updateBoard(BoardVO board);
	void updateBoardNewMem(BoardVO board);
	BoardVO selectBoard(int boardNo);
	int lastId();
	
}
