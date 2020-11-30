package com.example.mediatalk.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mediatalk.config.auth.PrincipalDetails;
import com.example.mediatalk.model.Board;
import com.example.mediatalk.model.Reply;
import com.example.mediatalk.model.User;
import com.example.mediatalk.repository.ReplyRepository;
import com.example.mediatalk.repository.BoardRepository;
import com.example.mediatalk.repository.UserRepository;
import com.example.mediatalk.service.MovieApiService;
import com.example.mediatalk.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {

	@Autowired
	private UserService uservice;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

/* ==========================시큐리티 접근 테스트용============================= */

	/*
	 * OAuth 로그인을 해도 PrincipalDetails 타입으로 받을 수 있고 일반 로그인을 해도 PrincipalDetails 타입으로
	 * 받을 수 있다.
	 */
	@GetMapping("/user")
	@ResponseBody
	public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principalDetails : " + principalDetails.getUser());
		return "유저 페이지입니다.";
	}

	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}

	// 사용자 권한 정보에 따라 접근제한
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}

	// 실행 전에 권한 검사
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}

	// 일반 사용자 로그인 테스트
	@GetMapping("/test/login")
	@ResponseBody
	public String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) { // DI(의존성
																													// 주입)
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		return "세션 정보 확인하기";
	}

	// oauth 로그인 테스트
	@GetMapping("/test/oauth/login")
	@ResponseBody
	public String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		return "OAuth 세션 정보 확인하기";
	}

	// db에 사용자 입력
	@GetMapping("/testregister")
	public String testRegister() {
//		String encPassword=bCryptPasswordEncoder.encode("aaaaaaaa");
//		for(int i=0;i<100;i++) {
//			if(i<80) {
//				User user=new User();
//				user.setUsername("user"+i);
//				user.setEmail("user"+i+"@user");
//				user.setPassword(encPassword);
//				user.setRole("ROLE_USER");
//				userRepository.save(user);
//	        }else if(i<90) {
//	        	User user=new User();
//	        	user.setUsername("manager"+i);
//	        	user.setEmail("manager"+i+"@manager");
//	        	user.setPassword(encPassword);
//	        	user.setRole("ROLE_MANAGER");
//	        	userRepository.save(user);
//	        }else {
//	        	User user=new User();
//	        	user.setUsername("admin"+i);
//	        	user.setEmail("admin"+i+"@admin");
//	        	user.setPassword(encPassword);
//	        	user.setRole("ROLE_ADMIN");
//	        	userRepository.save(user);
//	        }
//		}
		return "redirect:/";
	}
	
	// db에 글 입력
	@GetMapping("/writeRegister")
	public String writeRegister() {
		for(int i=0;i<100;i++) {
			if(i<80) {
				Board movieBoard=new Board();
				movieBoard.setUsername("user"+i);
				movieBoard.setTitle("반갑습니다"+i);
				movieBoard.setContent("반갑습니다"+i);
				movieBoard.setHitcount(0);
				boardRepository.save(movieBoard);
	        }else if(i<90) {
	        	Board movieBoard=new Board();
				movieBoard.setUsername("manager"+i);
				movieBoard.setTitle("매니저입니다."+i);
				movieBoard.setContent("매니저입니다"+i);
				movieBoard.setHitcount(0);
				boardRepository.save(movieBoard);
	        }else {
	        	Board movieBoard=new Board();
				movieBoard.setUsername("admin"+i);
				movieBoard.setTitle("관리자입니다."+i);
				movieBoard.setContent("관리자입니다"+i);
				movieBoard.setHitcount(0);
				boardRepository.save(movieBoard);
	        }
		}
		return "redirect:/";
	}
	
	@GetMapping("/reply")
	@ResponseBody
	public List<Reply> getReply() {
		return replyRepository.findAll();
	}

}