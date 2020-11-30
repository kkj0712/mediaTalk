package com.example.mediatalk.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.data.domain.Sort;
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
import com.example.mediatalk.dto.PageAction;
import com.example.mediatalk.dto.ReplyDTO;
import com.example.mediatalk.model.Board;
import com.example.mediatalk.model.Reply;
import com.example.mediatalk.model.User;
import com.example.mediatalk.repository.BoardRepository;
import com.example.mediatalk.repository.ReplyRepository;
import com.example.mediatalk.repository.UserRepository;
import com.example.mediatalk.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService uservice;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardRepository movieBoardRepository;
	
	@Autowired
	private ReplyRepository movieReplyRepository; 
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private PageAction page;
	

	/* 로그인 폼으로 이동 */
	@GetMapping("/login")
	public String loginForm() {
		return "/user/login";
	}
	
	/* 회원가입 폼으로 이동 */
	@GetMapping("/register.go")
	public String registerForm() {
		return "/user/register";
	}

	/* 회원목록 + 검색까지 */
	@GetMapping("/admin/userlist")
	public String userList(Model model,
			@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) 
			Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String field,
			@RequestParam(required = false, defaultValue = "") String word) {

		Page<User> ulist=userRepository.findAll(pageable);
		if(field.equals("username")) {
			ulist = userRepository.findByUsernameContaining(word, pageable);
		}else if(field.equals("email")){
			ulist = userRepository.findByEmailContaining(word, pageable);
		}
		
		int pageNumber=ulist.getPageable().getPageNumber(); //현재페이지
		int totalPages=ulist.getTotalPages(); //총 페이지 수. 검색에따라 10개면 10개..
		int pageBlock = 5; //블럭의 수 1, 2, 3, 4, 5	
		int startBlockPage = ((pageNumber)/pageBlock)*pageBlock+1; //현재 페이지가 7이라면 1*5+1=6
		int endBlockPage = startBlockPage+pageBlock-1; //6+5-1=10. 6,7,8,9,10해서 10.
		endBlockPage= totalPages<endBlockPage? totalPages:endBlockPage;
		
		model.addAttribute("startBlockPage", startBlockPage);
		model.addAttribute("endBlockPage", endBlockPage);
		model.addAttribute("ulist", ulist);
		
		return "/user/userlist";
	}
	
	/* 회원상세보기 */
	@GetMapping("/admin/view/{id}")
	public String userView(@PathVariable int id, Model model) {
		model.addAttribute("user", userRepository.findById(id));
		return "/user/userview";
	}
		
	/* 마이페이지 이동. Principal객체로는 getName()만 가능. 
	 * int 형을 인자값으로 쓰고 싶으므로 @AuthenticationPrincipal 어노테이션을 사용함
	 * */
	@GetMapping("/user/mypage")
	public String mypage(Model model,
			@AuthenticationPrincipal 
			PrincipalDetails principalDetails) {
		int prinId=principalDetails.getUser().getId();
		model.addAttribute("user", uservice.userDetail(prinId));
		return "/user/mypage";
	}
	
	//마이페이지 내가 쓴 글 목록
	@GetMapping("/user/mypage/writingList")
	public String writingList(Model model, Board movieBoard,
			@PageableDefault(size = 10, sort = "bnum", direction = Sort.Direction.DESC) 
			Pageable pageable, @AuthenticationPrincipal 
			PrincipalDetails principal) {
		
		Page<Board> mylist=movieBoardRepository.findByUserId(principal.getUser().getId(), pageable);
		
		int pageNumber=mylist.getPageable().getPageNumber(); //현재페이지
		int totalPages=mylist.getTotalPages(); //총 페이지 수. 검색에따라 10개면 10개..
		int pageBlock = 5; //블럭의 수 1, 2, 3, 4, 5	
		int startBlockPage = ((pageNumber)/pageBlock)*pageBlock+1; //현재 페이지가 7이라면 1*5+1=6
		int endBlockPage = startBlockPage+pageBlock-1; //6+5-1=10. 6,7,8,9,10해서 10.
		endBlockPage= totalPages<endBlockPage? totalPages:endBlockPage;
		
		model.addAttribute("startBlockPage", startBlockPage);
		model.addAttribute("endBlockPage", endBlockPage);
		model.addAttribute("mylist", mylist);
		
		return "/user/mypageBoardList";
	}
	
	//마이페이지 내가 쓴 댓글 목록
	@GetMapping("/user/mypage/replyList")
	public String writingReplyList(Model model, Reply movieReply,
			@PageableDefault(size = 10, sort = "regdate", direction = Sort.Direction.DESC) Pageable pageable, 
			@AuthenticationPrincipal PrincipalDetails principal) {
		
		//현재 로그인한 user가 쓴 댓글의 목록을 뽑아옴.
		Page<Reply> myreplyList=movieReplyRepository.findReplyByUserId(principal.getUser().getId(), pageable);

		int pageNumber=myreplyList.getPageable().getPageNumber(); //현재페이지
		int totalPages=myreplyList.getTotalPages(); //총 페이지 수. 검색에따라 10개면 10개..
		int pageBlock = 5; //블럭의 수 1, 2, 3, 4, 5	
		int startBlockPage = ((pageNumber)/pageBlock)*pageBlock+1; //현재 페이지가 7이라면 1*5+1=6
		int endBlockPage = startBlockPage+pageBlock-1; //6+5-1=10. 6,7,8,9,10해서 10.
		endBlockPage= totalPages<endBlockPage? totalPages:endBlockPage;
		
		model.addAttribute("startBlockPage", startBlockPage);
		model.addAttribute("endBlockPage", endBlockPage);
		model.addAttribute("myreplyList", myreplyList); //myreplyList에는 content, movieboard, user정보가 다 담겨져있음.
		
		return "/user/mypageReplyList";
	}
}