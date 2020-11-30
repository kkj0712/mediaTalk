package com.example.mediatalk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.mediatalk.model.Board;
import com.example.mediatalk.model.Reply;

public interface BoardRepository extends JpaRepository<Board, Integer>{

	
	Board findByBnum(int bnum);
	
	Page<Board> findAll(Pageable pageable);
	Page<Board> findByTitleContaining(String title, Pageable pageable);
	Page<Board> findByContentContaining(String content, Pageable pageable);
	
	@Query(value="SELECT a.*, COUNT(b.bnum) rcnt FROM "
			+ "(SELECT * FROM movie_board where title like '%반갑%' ORDER BY bnum DESC LIMIT 0, 10) a "
			+ "LEFT JOIN movie_reply b ON a.bnum = b.bnum GROUP BY a.bnum;", nativeQuery=true)
	public List<Board> testList();
	
	//마이페이지 내가 쓴 글 확인 + pageable이라서 개수까지 나옴
	@Query(value="select * from movie_board where user_id=?1", nativeQuery=true)
	public Page<Board> findByUserId(int user_id, Pageable pageable);
	
}
