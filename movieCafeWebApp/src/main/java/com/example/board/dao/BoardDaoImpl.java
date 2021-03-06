package com.example.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.board.vo.BoardFileVO;
import com.example.board.vo.BoardVO;
import com.example.board.vo.CommentVO;
import com.example.board.vo.RecomVO;
import com.example.board.vo.ReportVO;
import com.example.board.vo.SearchVO;

@Repository("boardDao")
public class BoardDaoImpl implements BoardDao {

	@Autowired
	private SqlSession sqlSession;

	// 게시글 목록 조회
	@Override
	public List<BoardVO> selectList(int cateNo) {

		return this.sqlSession.selectList("selectByCateNo", cateNo);

	}

	// 인기글 조회
	@Override
	public List<BoardVO> selectRecomRevList() {
		return this.sqlSession.selectList("Board.mainBestListSelect");
	}

	// 공지글 조회
	@Override
	public List<BoardVO> selectNoticeRevList() {
		return this.sqlSession.selectList("Board.mainNoticeListSelect");
	}

	// 게시글 상세보기
	@Override
	public BoardVO selectBoard(int boardNo) {
		return this.sqlSession.selectOne("selectDetailBoard", boardNo);
	}

	// 게시글 작성
	@Override
	public void insertBoard(BoardVO board) {
		this.sqlSession.insert("insertBoardCall", board);
	}

	// 팁 게시글 작성
	@Override
	public void insertBoardTip(BoardVO board) {
		this.sqlSession.insert("insertTipBoardCall", board);

	}

	// 파일 등록
	@Override
	public void insertBoardFile(BoardFileVO file) {
		this.sqlSession.insert("insertBoardFileCall", file);
		
	}

	//파일 수정
	@Override
	public void updateBoardFile(BoardFileVO file) {
		this.sqlSession.update("updateBoardFileCall",file);
		
	}

	
	@Override
	public void deleteBoardFile(int boardFileNo) {
		this.sqlSession.delete("deleteBoardFileCall",boardFileNo );
		
	}

	@Override
	public BoardFileVO selectBoardFile(int boardNo) {
		return this.sqlSession.selectOne("selectBoardFileCall", boardNo);
	}

	// LAST_ID구하기
	@Override
	public int lastId() {
		return this.sqlSession.selectOne("lastId");
	}

	// 게시글 수정
	@Override
	public void updateBoard(BoardVO board) {
		this.sqlSession.update("updateBoardCall", board);

	}

	// 게시글 삭제
	@Override
	public void deleteBoard(int boardNo) {
		this.sqlSession.delete("deleteBoard", boardNo);
	}

	//게시글 추천
	@Override
	public void insertRecommend(RecomVO recommend) {
		this.sqlSession.insert("insertRecomCall", recommend);
			
	}

	//추천 취소
	@Override
	public void deleteRecommend(RecomVO recommend) {
		this.sqlSession.delete("deleteRecomCall",recommend);
		}
	
	
	//추천 이력 조회
	@Override
	public boolean selectIsRecom(RecomVO recommend) {
		return this.sqlSession.selectOne("selectIsRecomCall",recommend);
	}

	//게시글 신고 등록
	@Override
	public void insertReport(ReportVO report) {
		this.sqlSession.insert("insertReportCall", report);
			
	}
	//게시글 신고 삭제
	@Override
	public void deleteReport(ReportVO report) {
		this.sqlSession.delete("deleteReportCall",report);
			
	}

	//신고 여부 조회
	@Override
	public boolean selectIsReport(ReportVO report) {
		return this.sqlSession.selectOne("selectIsReportCall",report);
	}

	@Override
	public List selectComList(int boardNo) {
		return this.sqlSession.selectList("selectCommentList", boardNo);
	}

	@Override
	public void insertComment(CommentVO comment) {
		this.sqlSession.insert("commmentInsert", comment);
	}

	@Override
	public void selectMap(Map map) {
		List<CommentVO> list = this.sqlSession.selectList("selectCommentList", map.get("boardNo"));
		map.put("results", list);
	}

	@Override
	public void deleteComment(int comNo) {
		this.sqlSession.delete("deleteComment", comNo);
	}
	
	@Override
	public void updateComment(CommentVO comment) {
		this.sqlSession.update("updateCommentCall", comment);
		
	}

	
	// ***********검색 추가 코드 시작 ************//
	@Override
	public List<BoardVO> selectSearchByTitle(SearchVO search) {
		return this.sqlSession.selectList("searchByTitle", search);
	}

	@Override
	public List<BoardVO> selectSearchByContent(SearchVO search) {
		
		return this.sqlSession.selectList("searchByContent", search);
	}

	@Override
	public List<BoardVO> selectSearchByUser(SearchVO search) {
		return this.sqlSession.selectList("searchByUser", search);
	}

	@Override
	public List<BoardVO> selectSearchByAll(SearchVO search) {
		return this.sqlSession.selectList("searchByAll",search);
	}

	@Override
	public List<BoardVO> selctSearchByHorse(SearchVO search) {
		
		return this.sqlSession.selectList("searchByHorse",search);
	}

	//*********검색 추가코드 끝**********
	
	@Override
	public int selectRecomCount(int boardNo) {
		return this.sqlSession.selectOne("recomCntCall", boardNo);
	}

	@Override
	public int selectCommCount(int boardNo) {
		return this.sqlSession.selectOne("commentCntCall", boardNo);
	}

	
}