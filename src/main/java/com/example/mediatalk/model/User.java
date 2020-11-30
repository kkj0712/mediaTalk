package com.example.mediatalk.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //파라미터 없는 기본 생성자 생성
@AllArgsConstructor
@Builder //ORM -> Java(다른언어) Object -> 테이블로 맵핑해주는 기술
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;

	@Column(nullable=false)
	private String email;
	private String role; //ROLE_USER, ROLE_ADMIN
	private Long grade; //ROLE_USER 중에서 회원등급 나누기
	
	private String provider; //google, naver 등등
	private String providerId; //getAttributes의 sub
	
	private int caution; //신고당한 횟수
	
	@PrePersist
	public void prePersist2() {
		this.grade= this.grade==null? 0:this.grade;
	}
	
	@CreationTimestamp
	@Column(name="createDate")
	private LocalDateTime createDate;

	@Builder
	public User(String username, String password, String email, String role, Long grade, String provider,
			String providerId, LocalDateTime createDate, int caution) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.grade = grade;
		this.provider = provider;
		this.providerId = providerId;
		this.createDate = createDate;
		this.caution=caution;
	}
}