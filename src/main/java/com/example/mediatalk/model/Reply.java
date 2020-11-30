package com.example.mediatalk.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;

import com.example.mediatalk.dto.ReplyDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name="movie_reply")
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cnum;
	private String username;
	
	@Column(nullable=false, length=200)
	private String msg;
	
	@CreationTimestamp
	@Column
	private LocalDateTime regdate;
	
	@ManyToOne
	@JoinColumn(name="bnum")
	private Board movieboard;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(nullable=true)
	private Integer caution;
	
	@Override
	public String toString() {
		return "MovieReply [cnum=" + cnum + ", username=" + username + ", msg=" + msg + ", regdate=" + regdate
				+ ", movieboard=" + movieboard + ", user=" + user + ", caution=" + caution + "]";
	}
}
