package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.dao.BoardDao;
import com.example.board.vo.BoardVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDao boardDao;
	
	@Override
	public List<BoardVO> readAllByCateNo(int cateNo) {
		return null;
	}

	@Override
	public List<BoardVO> readRecomRevList() {
		return this.boardDao.selectRecomRevList();
	}
	@Override
	public List<BoardVO> readNoticeRevList() {
		return this.boardDao.selectNoticeRevList();
	}
}
