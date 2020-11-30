package com.example.mediatalk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
	private int user_id;
	private int bnum;
	private String msg;
	private String username;
	private int caution=0;
}
