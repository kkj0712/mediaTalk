package com.example.mediatalk.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.mediatalk.model.Board;
import com.example.mediatalk.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

	//마이페이지 내가 쓴 댓글 확인
	@Query(value="select * from movie_reply where user_id=?1", nativeQuery=true)
	public Page<Reply> findReplyByUserId(int user_id, Pageable pageable);

}
