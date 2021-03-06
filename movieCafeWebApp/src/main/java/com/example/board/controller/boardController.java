package com.example.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.service.BoardService;
import com.example.board.vo.BoardFileVO;
import com.example.board.vo.BoardVO;
import com.example.board.vo.CommentVO;
import com.example.board.vo.SearchVO;
import com.example.member.service.UserService;
import com.example.member.vo.UserInfoVo;
import com.example.util.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class boardController {

	@Autowired
	private BoardService boardServie;
	
	@Autowired
	private UserService userService; 
	
	@Autowired
    private FileUploadService fileUploadService;

	@GetMapping("/boardlist/{cateNo}")
	public String boardlist(@PathVariable int cateNo, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfoVo userInfo = (UserInfoVo) session.getAttribute("userInfo");
		
		model.addAttribute("userInfo", userInfo);
		List<BoardVO> list = boardServie.readAllByCateNo(cateNo);
		
		for(BoardVO board : list) {
			board.setRecomCount(this.boardServie.readRecomCount(board.getBoardNo()));
			board.setCommentCount(this.boardServie.readCommCount(board.getBoardNo()));
		}
		
		model.addAttribute("list",list);
		
		return "views/board/boardlist";
	}
	
	// 게시글 조회 ajax  
	   @GetMapping("/getBoardList/{cateNo}")
	   public @ResponseBody List<BoardVO> listBoard(@PathVariable int cateNo) {
	      List<BoardVO> list= boardServie.readAllByCateNo(cateNo);
	  	for(BoardVO board : list) {
			board.setRecomCount(this.boardServie.readRecomCount(board.getBoardNo()));
			board.setCommentCount(this.boardServie.readCommCount(board.getBoardNo()));
		}
	      return list;
	   }

	// 게시글 상세보기

		@GetMapping("/detail/{boardNo}")

		public String deltailboard(@PathVariable int boardNo, Model model, HttpServletRequest request) {

			HttpSession session = request.getSession();
			UserInfoVo userInfo = (UserInfoVo) session.getAttribute("userInfo");
			
			BoardVO board = boardServie.readOne(boardNo);

			board.setBoardfile(this.boardServie.readBoardFile(boardNo));
			
    	board.setCommentCount(this.boardServie.readCommCount(boardNo));

			board.setRecomCount(this.boardServie.readRecomCount(boardNo));
			List<CommentVO> list = this.boardServie.readCommentList(boardNo);
			
			int cateNo = board.getCateNo();
			
			
			model.addAttribute("board", board);
			model.addAttribute("list", list);
			model.addAttribute("userInfo", userInfo);
			// UserInfo 가 있을 경우
			if (userInfo != null) {
				if (cateNo == 4) {
					return "views/board/boardTipDetail";
				} else {
					return "views/board/boardDetail";
				}
				// UserInfo가 없을 경우
			} else {
				return "redirect:/loginFail";
			}
			
		}
		
		@GetMapping("/detail/detailTipboard{boardNo}")
		public String detailTipboard(@PathVariable int boardNo, Model model, HttpServletRequest request) {
			HttpSession session = request.getSession();
			UserInfoVo userInfo = (UserInfoVo) session.getAttribute("userInfo");
			
			BoardVO board = boardServie.readOne(boardNo);
			
			List<CommentVO> list = this.boardServie.readCommentList(boardNo);
			model.addAttribute("board", board);
			model.addAttribute("list", list);
			model.addAttribute("userInfo", userInfo);
			return "views/board/boardTipDetail";
		}
	 
	@PostMapping("/deleteBoard/{boardNo}")
	public String deleteBoard(@PathVariable int boardNo, BoardVO board, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		UserInfoVo userInfo = (UserInfoVo) session.getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		
		int cateNo = board.getCateNo();
		
		this.boardServie.removeBoard(boardNo);
		
		if (cateNo == 1) {
			return "redirect:/boardlist/1";
		}else if(cateNo == 2){
			return "redirect:/boardlist/2";
		}else if(cateNo == 3){
			return "redirect:/boardlist/3";
		}else{
			return "redirect:/boardlist/4";
	}
		
	}
	

	@GetMapping("/boardlist/boardwrite/{cateNo}")
	public String boardwrite(@PathVariable int cateNo, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		UserInfoVo userInfo = (UserInfoVo) session.getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("rankType", "A");
		if (cateNo == 4) {
			return "views/board/boardwriteTip";
		} else {
			return "views/board/boardwrite";
	}
	}
	
	
	@PostMapping("/fileUpload")
	public String boardwrite(BoardVO board, @RequestPart(value = "boardFileInput", required = false) MultipartFile boardFileSys, 
			Model model, HttpSession session, HttpServletRequest request) {
		BoardFileVO boardfile = new BoardFileVO();
		UserInfoVo userInfo = (UserInfoVo) session.getAttribute("userInfo");
		boardfile.setBoardfileOrigin(boardFileSys.getOriginalFilename());
		String userId1 = board.getUserId();
		String boardFileName = null;
		if(!boardFileSys.getOriginalFilename().isEmpty()) {
			boardFileName = fileUploadService.boardRestore(boardFileSys, request);
			boardfile.setBoardfileSys(boardFileName);
			boardfile.setBoardfileSize(boardFileSys.getSize());
			boardfile.setBoardfileType(boardFileSys.getContentType());
			board.setBoardfile(boardfile);
			//this.boardServie.createFile(boardfile);
		}
		
		int cateNo = board.getCateNo();
		
		model.addAttribute("userInfo", userInfo);
		if (cateNo == 1) {
			
			this.boardServie.createBoard(board);
			
			if(!board.getBoardfile().equals(null)) {
				int no = this.boardServie.lastId();
				boardfile.setBoardNo(no);
			this.boardServie.createFile(boardfile);
			}
			UserInfoVo user = userService.uploadUserInfo(userId1);
			session.setAttribute("userInfo", user);
			return "redirect:boardlist/1";
		}else if(cateNo ==2){
			this.boardServie.createBoard(board);

			if(!board.getBoardfile().equals(null)) {
				int no = this.boardServie.lastId();
				boardfile.setBoardNo(no);
			this.boardServie.createFile(boardfile);
			}
			UserInfoVo user = userService.uploadUserInfo(userId1);
			session.setAttribute("userInfo", user);
			return "redirect:boardlist/2";
		}else if(cateNo ==3){
			this.boardServie.createBoard(board);
			if(!board.getBoardfile().equals(null)) {
				int no = this.boardServie.lastId();
				boardfile.setBoardNo(no);
			this.boardServie.createFile(boardfile);
			}
			UserInfoVo user = userService.uploadUserInfo(userId1);
			session.setAttribute("userInfo", user);
			return "redirect:boardlist/3";
		}else{
				this.boardServie.createBoard(board);

				if(!board.getBoardfile().equals(null)) {
					int no = this.boardServie.lastId();
					boardfile.setBoardNo(no);
				this.boardServie.createFile(boardfile);
				}
				// 세션에 가져온 유저정보 등록
				UserInfoVo user = userService.uploadUserInfo(userId1);
				session.setAttribute("userInfo", user);
				return "redirect:boardlist/4";
	}
	}

	

	@PostMapping("/tipfileUpload")
	public String boardwriteTip(int cateNo, BoardVO board, HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();
		UserInfoVo userInfo = (UserInfoVo) session.getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		if(board.getApiX().equals("")) board.setApiX("null");
		if(board.getApiY().equals("")) board.setApiY("null");
		
		this.boardServie.createTipBoard(board);

		return "redirect:/boardlist/4";
	}

	

	// 게시글 수정폼불러오기
	@GetMapping("/modifyform/{boardNo}")
	public String boardModifyForm(@PathVariable int boardNo, @RequestPart(value = "boardFileInput",required = false) MultipartFile boardFileSys, 
			Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfoVo userInfo = (UserInfoVo) session.getAttribute("userInfo");
		
		BoardVO board = boardServie.readOne(boardNo);
		board.setBoardNo(boardNo);
		board.setBoardfile(this.boardServie.readBoardFile(boardNo));
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("board",board);
		//System.out.println(board.getBoardfile().getBoardfileOrigin());
		if(board.getHorseNo()>=1 && board.getHorseNo()<8  ) 
			{
			model.addAttribute("cateNo",2);
			return "views/board/boardModifyForm";
			}
		else if(board.getHorseNo()==8 || board.getHorseNo()==9  ) 
			{
			model.addAttribute("cateNo",3);
			return "views/board/boardModifyForm";
			}
		else if(board.getHorseNo()==10 || board.getHorseNo()== 11 )
			{
			model.addAttribute("cateNo",4);
			return "views/board/boardTipModifyForm";
			}
		else {
			model.addAttribute("cateNo",1);
			return "views/board/boardModifyForm";
		}
		
	}

	//게시글 수정
	@PostMapping("/modifyboard/{cateNo}")
	public String boardModify(@PathVariable int cateNo, @RequestParam("boardContent") String boardContent,  
							@RequestParam("horseNo") int horseNo, @RequestParam("boardNotice") int boardNotice,
							@RequestParam("boardTitle") String boardTitle, @RequestPart(value = "boardFileInput",required = false) MultipartFile boardFileSys,
							@RequestParam("boardNo") int boardNo, @RequestParam("boardfileNo") int boardfileNo,
							Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfoVo userInfo = (UserInfoVo) session.getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		
		BoardVO newboard = new BoardVO();
		BoardFileVO boardfile = new BoardFileVO();
		
		boardfile.setBoardfileOrigin(boardFileSys.getOriginalFilename());
		
		String boardFileName = null;
		if(!boardFileSys.getOriginalFilename().isEmpty()) {
			boardFileName = fileUploadService.boardRestore(boardFileSys, request);
			boardfile.setBoardfileSys(boardFileName);
			boardfile.setBoardfileSize(boardFileSys.getSize());
			boardfile.setBoardfileType(boardFileSys.getContentType());
			newboard.setBoardfile(boardfile);
		}
		
		newboard.setBoardTitle(boardTitle);
		newboard.setBoardContent(boardContent);
		newboard.setHorseNo(horseNo);
		
		newboard.setBoardNotice(boardNotice);
		newboard.setBoardNo(boardNo);
		//newboard.setBoardfile(boardfile);
		this.boardServie.modifyBoard(newboard);
		
		if(!newboard.getBoardfile().equals(null)) {
			
			boardfile.setBoardNo(boardNo);
			this.boardServie.removeFile(boardfileNo);
		this.boardServie.createFile(boardfile);
		}

		
		if (cateNo == 1) {
			return "redirect:/boardlist/1";
		}else if(cateNo == 2){
			return "redirect:/boardlist/2";
		}else{
			return "redirect:/boardlist/3";
		}
	}
	
	
	@PostMapping("/modifyTipboard")
	public String boardTipModify(BoardVO board , Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfoVo userInfo = (UserInfoVo) session.getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		
		if(board.getApiX().equals("")) board.setApiX("null");
		if(board.getApiY().equals("")) board.setApiY("null");
		
		this.boardServie.modifyBoard(board);
		
		
			return "redirect:/boardlist/4";
	}
	// ***********************검색 추가 코드************************ 
	//게시글 검색
	@GetMapping("/boardlist/boardsearch/{cateNo}")
	public String boardSearch(@PathVariable int cateNo, @RequestParam String keyfield, 
			@RequestParam String keyword, Model model) {
		SearchVO search = new SearchVO();
		
			search.setCateNo(cateNo);
			search.setKeyfield(keyfield);
			search.setKeyword(keyword);
		
		List<BoardVO> list = this.boardServie.searchBoard(search);
		model.addAttribute("list",list);
		model.addAttribute("cateNo",cateNo);
		return "views/board/boardsearch";
	}
	
	//게시글 검색
		@GetMapping("/boardlist/boardsearch/boardsearch/{cateNo}")
		public String boardSearchSeq(@PathVariable int cateNo, String keyfield, String keyword,  Model model,
				RedirectAttributes redirect) {
			SearchVO search = new SearchVO();
			
				search.setCateNo(cateNo);
				search.setKeyfield(keyfield);
				search.setKeyword(keyword);
			
			List<BoardVO> list = this.boardServie.searchBoard(search);
			model.addAttribute("list",list);
			redirect.addAttribute("cateNo",cateNo);
			redirect.addAttribute("keyfield",keyfield);
			redirect.addAttribute("keyword",keyword);
		//	redirect.addAttribute("horse",horse);
			return "redirect:/boardlist/boardsearch/"+cateNo ;
			
		}
		
		
		
		// ***********************검색 추가 코드 끝************************ 
		


	}