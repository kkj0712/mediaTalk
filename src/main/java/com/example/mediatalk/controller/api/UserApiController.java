package com.example.mediatalk.controller.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mediatalk.config.VerifyRecaptcha;
import com.example.mediatalk.model.User;
import com.example.mediatalk.repository.UserRepository;
import com.example.mediatalk.service.UserService;

@Controller
public class UserApiController {

	@Autowired
	private UserService uservice;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/* 리캡챠 */
	@PostMapping("/VerifyRecaptcha")
	@ResponseBody
	public int VerifyRecaptcha(HttpServletRequest request) {
		VerifyRecaptcha.setSecretKey("6LdtH-IZAAAAAExI9utYYO98qeVYtaKlHbBnm6fd");
		String gRecaptcahResponse=request.getParameter("recaptcha");
		try {
			if(VerifyRecaptcha.verify(gRecaptcahResponse)==1) {
				return 1; //성공
			}else {
				return 0; //실패
			}
		}catch(Exception e) {
			e.printStackTrace();
			return -1; //에러
		}
	}
	
	
	/* 회원 가입 */
	@PostMapping("/register.go")
	public String register(User user) {
		System.out.println(user.getPassword());
		uservice.registerUser(user);
		return "redirect:/";
	}
	
	/* 아이디 중복체크 */
	@PostMapping("nameCheck")
	@ResponseBody
	public void nameCheck(String username, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		if (userRepository.findByUsername(username)==null) {
			out.print(true);
		}else {
			out.print(false);
		}
	}
	
	/* email 중복체크 */
	@PostMapping("emailCheck")
	@ResponseBody
	public void emailCheck(String email, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		if (userRepository.findByEmail(email)==null) {
			out.print(true);
		} else {
			out.print(false);
		}
	}
	
	/* 회원 정보 수정 */
	@PutMapping("/admin/update/{id}")
	@ResponseBody
	public String update(@PathVariable int id, @RequestBody User user) {
		uservice.update(user);
		String emailStr=user.getEmail().toString();
		return emailStr;
	}
	
	/* 회원 삭제하기 */
	@DeleteMapping("/admin/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable int id, @RequestBody User user) {
		uservice.delete(id);
		String emailStr=user.getEmail().toString();
		return emailStr;
	}
	
	/* 마이페이지 비밀번호 수정 */
	@PutMapping("/user/mypage/update")
	@ResponseBody
	public String mypageUpdate(HttpServletRequest request, User user) {
		String rawPassword=request.getParameter("password");
		String encPassword=bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		uservice.mypageUpdate(user);
		return "str";
	}
	
	/* 마이페이지 회원 탈퇴 */
	@DeleteMapping("/user/mypage/delete/{id}")
	@ResponseBody
	public String mypageDelete(@PathVariable int id, HttpSession session) {
		uservice.delete(id);
		session.invalidate();
		return "str";
	}
}