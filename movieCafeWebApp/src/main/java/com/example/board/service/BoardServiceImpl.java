package com.example.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.dao.BoardDao;
import com.example.board.vo.BoardFileVO;
import com.example.board.vo.BoardVO;
import com.example.board.vo.CommentVO;
import com.example.board.vo.RecomVO;
import com.example.board.vo.ReportVO;
import com.example.board.vo.SearchVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDao boardDao;
	
	//게시글 목록 조회
	@Override
	public List<BoardVO> readAllByCateNo(int cateNo) {
		return this.boardDao.selectList(cateNo);
	}

	//게시글 상세조회
	@Override
	public BoardVO readOne(int boardNo) {
		return this.boardDao.selectBoard(boardNo);
	}

	//인기글조회
	@Override
	public List<BoardVO> readRecomRevList() {
		return this.boardDao.selectRecomRevList();
	}
	//공지글조회
	@Override
	public List<BoardVO> readNoticeRevList() {
		return this.boardDao.selectNoticeRevList();
	}
	
	//게시글 작성
	@Override
	public void createBoard(BoardVO board) {
		
		this.boardDao.insertBoard(board);
	}

	
	@Override
	public int lastId() {
		return this.boardDao.lastId();
	}

	//팁 게시글 작성
	@Override
	public void createTipBoard(BoardVO board) {
		this.boardDao.insertBoardTip(board);
		
	}
	
	//파일 등록
	@Override
	public void createFile(BoardFileVO file) {
		this.boardDao.insertBoardFile(file);
		
	}
	
	
	
	
	@Override
	public void modifyFile(BoardFileVO file) {
		this.boardDao.updateBoardFile(file);
		
	}

	
	@Override
	public void removeFile(int boardFileNo) {
		this.boardDao.deleteBoardFile(boardFileNo);
		
	}

	@Override
	public BoardFileVO readBoardFile(int boardNo) {
		return this.boardDao.selectBoardFile(boardNo);
	}
	
	
	

	//게시글 수정
	@Override
	public void modifyBoard(BoardVO board) {
		this.boardDao.updateBoard(board);
		
	}
	

	//게시글 삭제
	@Override
	public void removeBoard(int boardNo) {
		this.boardDao.deleteBoard(boardNo);
	}

	//게시글 추천
	@Override
	public void createRecommend(RecomVO recommend) {
		this.boardDao.insertRecommend(recommend);
			
		}

	//게시글 추천 취소
	@Override
	public void dropRecommend(RecomVO recommend) {
		this.boardDao.deleteRecommend(recommend);		
	}

	//게시글 추천 이력 조회
	@Override
	public boolean readIsRecom(RecomVO recommend) {
		return this.boardDao.selectIsRecom(recommend);
	}

	//게시글 신고 등록
	@Override
	public void createReport(ReportVO report) {
		this.boardDao.insertReport(report);
			
	}

	//게시글 신고 취소
	@Override
	public void dropReport(ReportVO report) {
		this.boardDao.deleteReport(report);
		
	}

	//게시글 신고 여부
	@Override
	public boolean readIsReport(ReportVO report) {
		return this.boardDao.selectIsReport(report);
	}

		
	@Override
	public List readCommentList(int boardNo) {
		return this.boardDao.selectComList(boardNo);
	}

	@Override
	public void createComment(CommentVO comment) {
		this.boardDao.insertComment(comment);
	}

	@Override
	public void readMapCommentList(Map map) {
		this.boardDao.selectMap(map);
	}

	@Override
	public void removeComment(int comNo) {
		this.boardDao.deleteComment(comNo);;
	}
	
	@Override
	public void modifyComment(CommentVO comment) {
		this.boardDao.updateComment(comment);
	}

	// **********검색 추가 코드**********
	@Override
	public List<BoardVO> searchBoard(SearchVO search) {
		
		SearchVO searchKeyWord = new SearchVO();
		searchKeyWord.setCateNo(search.getCateNo());
		searchKeyWord.setKeyword(search.getKeyword());
		
		if(search.getKeyfield().equals("boardTitle")) {
		return this.boardDao.selectSearchByTitle(searchKeyWord);
		} else if(search.getKeyfield().equals("boardContent")) {
			return this.boardDao.selectSearchByContent(searchKeyWord);
		} else if(search.getKeyfield().equals("userId")){
			return this.boardDao.selectSearchByUser(searchKeyWord);
			
		} else if(search.getKeyfield().equals("all")) {return this.boardDao.selectSearchByAll(searchKeyWord);}
		else 
			{
				return this.boardDao.selctSearchByHorse(search);
			}
	
	}
	// **********검색 추가 코드**********

	@Override
	public int readRecomCount(int boardNo) {
		return this.boardDao.selectRecomCount(boardNo);
	}

	@Override
	public int readCommCount(int boardNo) {
		return this.boardDao.selectCommCount(boardNo);
	}

	
	
}
