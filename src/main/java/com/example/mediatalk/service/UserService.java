package com.example.mediatalk.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mediatalk.model.User;
import com.example.mediatalk.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	// 회원 가입
	@Transactional
	public void registerUser(User user) {
		String rawPassword=user.getPassword();
		String encPassword=encoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		try {
			userRepository.save(user);
		} catch (Exception e) {
			System.out.println("회원가입 실패");
		}
	}

	// 회원 수
	@Transactional
	public int userCount() {
		return userRepository.userCount();
	}
	
	// 이게 있어야지 cache에서 flush기능을 해서 db가 update된다.
	// 회원 수정
	@Transactional 
	public void update(User user) {
		User reUser=userRepository.findById(user.getId());
		reUser.setRole(user.getRole());
		reUser.setGrade(user.getGrade());
	}
	
	// 회원 삭제
	@Transactional
	public void delete(int id) {
		userRepository.deleteById(id);
	}

	// 마이페이지 이동
	@Transactional
	public User userDetail(int prinId) {
		return userRepository.userDetailPage(prinId);
	}
	
	// 마이페이지 수정
	@Transactional 
	public void mypageUpdate(User user) {
		User reUser=userRepository.findById(user.getId());
		reUser.setPassword(user.getPassword());
	}
	
}
