package com.example.mediatalk.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.mediatalk.model.Board;
import com.example.mediatalk.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);

	User findByEmail(String email);
	
	User findById(int id);
	
	// 회원 목록 + 검색
	Page<User> findAll(Pageable pageable);
	Page<User> findByUsernameContaining(String username, Pageable pageable);
	Page<User> findByEmailContaining(String email, Pageable pageable);

	// 회원 상세보기
	@Query(value="select * from user where id=?1", nativeQuery = true)
	public User userDetailPage(int id);
	
	// 회원 수
	@Query(value="select count (*) from user", nativeQuery = true)
	public int userCount();
	
}