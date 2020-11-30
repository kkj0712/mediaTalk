package com.example.mediatalk.service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mediatalk.dto.ReplyDTO;
import com.example.mediatalk.model.Likes;
import com.example.mediatalk.model.Board;
import com.example.mediatalk.model.Reply;
import com.example.mediatalk.model.User;
import com.example.mediatalk.repository.ReplyRepository;
import com.example.mediatalk.repository.UserRepository;
import com.example.mediatalk.repository.LikesRepository;
import com.example.mediatalk.repository.BoardRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	@Autowired
	private final BoardRepository movieRepository;
	
	@Autowired
	private ReplyRepository movieReplyRepository; 
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LikesRepository likesRepository;
	
	// 게시글 입력
	@Transactional
	public void boardSave(Board movieBoard, User user) {
		movieBoard.setHitcount(0);
		movieBoard.setUser(user);
		movieBoard.setUsername(user.getUsername());
		movieRepository.save(movieBoard);
	}
	
	// 글 상세보기
	@Transactional
	public Board findByBnum(int bnum) {
		Board movieBoard=movieRepository.findById(bnum)
		.orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
		int rehit=movieBoard.getHitcount()+1;
		movieBoard.setHitcount(rehit);
		return movieBoard;
	}
	
	// 글 수정
	@Transactional
	public void updateProc(int bnum, Board movieBoard) {
		Board mb=movieRepository.findById(bnum)
				.orElseThrow(()->{
					return new IllegalArgumentException("글수정 실패");
				});
		mb.setTitle(movieBoard.getTitle());
		mb.setContent(movieBoard.getContent());
	}
	
	// 글 삭제
	@Transactional
	public void deleteProc(int bnum) {
		movieRepository.deleteById(bnum);
	}
	
	// 댓글 입력
	@Transactional
	public void replySave(ReplyDTO replyDto) {
		User user=userRepository.findById(replyDto.getUser_id());
		Board movieBoard=movieRepository.findById(replyDto.getBnum())
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 입력 실패: 게시글 id를 찾을 수 없습니다.");
				});
		Reply movieReply=Reply.builder()
				.user(user)
				.movieboard(movieBoard)
				.msg(replyDto.getMsg())
				.build();
		movieReplyRepository.save(movieReply);
		int reCount=movieBoard.getReplycnt()+1;
		movieBoard.setReplycnt(reCount);
	}
	
	// 댓글 삭제
	@Transactional
	public void deleteReplyProc(int cnum, int bnum) {
		Board movieBoard=movieRepository.findById(bnum)
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 입력 실패: 게시글 id를 찾을 수 없습니다.");
				});
		movieReplyRepository.deleteById(cnum);
		int reCount=movieBoard.getReplycnt()-1;
		movieBoard.setReplycnt(reCount);
	}
	
	// 추천하기
	@Transactional
	public void likeSave(int bnum, int user_id) {
		Board movieBoard=movieRepository.findById(bnum)
				.orElseThrow(()->{
					return new IllegalArgumentException("게시글 id를 찾을 수 없습니다.");
				});
		User user=userRepository.findById(user_id);
		Likes like=new Likes();
		like.setBnum(bnum);
		like.setUser(user);
		
		likesRepository.save(like);
		int good=movieBoard.getGood()+1;
		movieBoard.setGood(good);
	}
	
//	// 추천수 출력
//	@Transactional
//	public int getLikeCount(int bnum) {
//		return likesRepository.getLikeCnt(bnum);
//	}
}
