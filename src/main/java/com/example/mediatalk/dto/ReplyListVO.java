package com.example.mediatalk.dto;

import java.util.List;

import com.example.mediatalk.model.Reply;

import lombok.Builder;
import lombok.Data;

@Data
public class ReplyListVO {
	private int count;
	private List<Reply> list;
	private String pageHtml;
	
	@Builder
	//이 객체를 만드는 이유?: Controller에서부터 넘겨줘야 할 값이 3개라서 (개수, 내용, 페이징). 이 객체를 한꺼번에 return 시킬려고.
	public ReplyListVO(int count, List<Reply> list, String pageHtml) {
		this.count=count;
		this.list=list;
		this.pageHtml=pageHtml;
	}
}
