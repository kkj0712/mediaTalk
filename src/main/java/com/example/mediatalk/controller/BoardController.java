package com.example.mediatalk.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mediatalk.config.auth.PrincipalDetails;
import com.example.mediatalk.dto.ReplyDTO;
import com.example.mediatalk.model.Likes;
import com.example.mediatalk.model.Board;
import com.example.mediatalk.model.Reply;
import com.example.mediatalk.model.User;
import com.example.mediatalk.repository.LikesRepository;
import com.example.mediatalk.repository.BoardRepository;
import com.example.mediatalk.repository.UserRepository;
import com.example.mediatalk.service.BoardService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService mservice;
	
	@Autowired
	private BoardRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LikesRepository likesRepository;
	
	//추가폼으로 이동
	@GetMapping("/user/boardForm")
	public String movieInsert() {
		return "/board/boardInsert";
	}
	
	//글쓰기
	@PostMapping("/user/boardSave")
	@ResponseBody
	public String movieInsert(@RequestBody Board movieBoard,
			@AuthenticationPrincipal PrincipalDetails principal) {
		mservice.boardSave(movieBoard, principal.getUser());
		return "글입력";
	}
	
	//글목록으로 이동
	@GetMapping({ "", "/" })
	public String movieList(Model model, Board movieBoard,
			@PageableDefault(size = 10, sort = "bnum", direction = Sort.Direction.DESC) 
			Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String field,
			@RequestParam(required = false, defaultValue = "") String word) {
		Page<Board> mlist=movieRepository.findAll(pageable);
		if(field.equals("title")) {
			mlist = movieRepository.findByTitleContaining(word, pageable);
		}else if(field.equals("content")){
			mlist = movieRepository.findByContentContaining(word, pageable);
		}
		
		int pageNumber=mlist.getPageable().getPageNumber(); //현재페이지
		int totalPages=mlist.getTotalPages(); //총 페이지 수. 검색에따라 10개면 10개..
		int pageBlock = 5; //블럭의 수 1, 2, 3, 4, 5	
		int startBlockPage = ((pageNumber)/pageBlock)*pageBlock+1; //현재 페이지가 7이라면 1*5+1=6
		int endBlockPage = startBlockPage+pageBlock-1; //6+5-1=10. 6,7,8,9,10해서 10.
		endBlockPage= totalPages<endBlockPage? totalPages:endBlockPage;
		
		model.addAttribute("startBlockPage", startBlockPage);
		model.addAttribute("endBlockPage", endBlockPage);
		model.addAttribute("mlist", mlist);
		
		return "/board/boardList";
	}
	
	//글 상세보기
	@GetMapping("/boardView/{bnum}")
	public String userView(@PathVariable int bnum, Model model) {
		Board movieBoard=mservice.findByBnum(bnum);
		model.addAttribute("mview", movieBoard);
		return "/board/boardDetail";
	}
	
	//글 수정 폼으로 이동
	@GetMapping("/updateForm/{bnum}")
	public String boardUpdateForm(@PathVariable int bnum, Model model) {
		Board movieBoard=movieRepository.findByBnum(bnum);
		model.addAttribute("board", movieBoard);
		return "/board/boardUpdateForm";
	}
	
	//수정하기
	@PutMapping("/updateProc/{bnum}")
	@ResponseBody
	public String boardUpdateProc(@PathVariable("bnum") int bnum, 
			@RequestBody Board movieBoard) {
		mservice.updateProc(bnum, movieBoard);
		return "글수정";
	}
	
	//삭제하기 
	@DeleteMapping("/deleteProc/{bnum}")
	@ResponseBody
	public String boardDeleteProc(@PathVariable("bnum") int bnum) {
		mservice.deleteProc(bnum);
		return "글삭제";
	}
	
	//댓글작성
	/*@PostMapping("/movie_boardView/{bnum}/reply")
	@ResponseBody
	public String replyInsert(@PathVariable int bnum, @RequestBody MovieReply movieReply,
			@AuthenticationPrincipal PrincipalDetails principal) {
		mservice.replySave(principal.getUser(), bnum, movieReply);
		return "댓글입력";
	} */
	
	//댓글 작성->RelyDTO로 한번에 받기.
	@PostMapping("/boardView/{bnum}/reply")
	@ResponseBody
	public String replyInsert(@RequestBody ReplyDTO replyDto) {
		mservice.replySave(replyDto);
		return "댓글입력";
	}
	
	//댓글 삭제하기
	@DeleteMapping("/deleteReplyProc/{bnum}/reply/{cnum}")
	@ResponseBody
	public String replyDeleteProc(@PathVariable("cnum") int cnum,
			@PathVariable("bnum") int bnum) {
		mservice.deleteReplyProc(cnum, bnum);
		return "글삭제";
	}
	
	//추천 했는지 확인
	@PostMapping("/boardView/likeCheck/{bnum}")
	@ResponseBody
	public int likeCheck(@PathVariable("bnum") int bnum,
			@AuthenticationPrincipal PrincipalDetails principal) {
		int user_id=principal.getUser().getId();
		int result=likesRepository.likesCheck(bnum, user_id);
		return result;
	}
	
	//추천하기
	@PostMapping("/boardView/likeSave/{bnum}")
	@ResponseBody
	public String likeSave(@PathVariable("bnum") int bnum,
			@AuthenticationPrincipal PrincipalDetails principal) {
		int user_id=principal.getUser().getId(); //현재 로그인한 유저의 id
		mservice.likeSave(bnum, user_id);
		return "추천완료";
	}
	
	//추천수 가지고 오기
	@PostMapping("/boardView/likeCount/{bnum}")
	@ResponseBody
	public int likeCount(@PathVariable("bnum")int bnum) {
		return likesRepository.getLikeCnt(bnum); 
	}
	
}
