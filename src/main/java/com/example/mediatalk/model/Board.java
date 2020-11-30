package com.example.mediatalk.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="movie_board")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bnum; //기본키
	
	@Column(nullable=false, length=100)
	private String title; //제목
	
	@Lob //대용량데이터
	private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인됨
	
	@CreationTimestamp
	@Column(name="regdate")
	private LocalDateTime regdate; //작성일
	
	private int hitcount; //조회수
	private int replycnt; //댓글수
	private int good; //추천수
	private int caution; //신고횟수
	
	private String username;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user; //유저 정보
	
	@OneToMany(mappedBy="movieboard", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"movieboard"})
	@OrderBy("cnum asc")
	private List<Reply> reply; //댓글 출력
	
}