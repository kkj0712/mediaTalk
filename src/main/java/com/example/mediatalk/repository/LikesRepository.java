package com.example.mediatalk.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.mediatalk.model.Likes;

public interface LikesRepository extends JpaRepository<Likes, Integer>{
	
	// 추천 수 출력
	@Query(value="select count(*) from likes where bnum=?1", nativeQuery=true)
	public int getLikeCnt(int bnum);

	//동일게시글 추천여부 검색
	@Query(value="select count(*) from likes where bnum=?1 and user_id=?2", nativeQuery=true)
	public int likesCheck(int bnum, int user_id);
}
