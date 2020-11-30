package com.example.mediatalk.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TVApi {
	private String rank; 
	private String tvNm; 
	private String poster_path; 
	private String link; 
	private String id; //link를 들어가려면 id가 필요
}
