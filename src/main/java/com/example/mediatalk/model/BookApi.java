package com.example.mediatalk.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BookApi {
	private String rank; //순위
	private String bookNm; //책제목
	private String cover; //책커버 
	private String link; //인터파크 링크
}
