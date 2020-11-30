package com.example.mediatalk.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.mediatalk.model.User;

import lombok.Data;

//Authentication 객체에 저장할 수 있는 유일한 타입
@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	private User user; //콤포지션
	private Map<String, Object> attributes;
	
	// 일반 로그인할때 사용하는 생성자
	public PrincipalDetails(User user) {
		this.user=user;
	}

	// OAuth 로그인할때 사용하는 생성자
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user=user;
		this.attributes=attributes;
	}
	
	// 해당 유저의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection <GrantedAuthority> collect=new ArrayList<>();
		collect.add(()->{ return user.getRole();});
		return collect;
	}

	// 패스워드 리턴
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	// 닉네임 리턴
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정 유효기간
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정 잠겼는지
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 계정이 1년 지났는지
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 사용가능한지
	@Override
	public boolean isEnabled() {
		/* 사이트에서 1년동안 회원이 로그인을 안하면 휴면 계정으로 전환한다면
		 * 유저의 loginDate 같은 컬럼이 더 있어야함
		 * 현재시간-user.getloginDate()>=1년: return false;
		 * */
		return true;
	}

	// 구글에서 받은 유저의 정보 저장
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	// 구글에서 받은 유저의 sub키 값
	@Override
	public String getName() {
		return null;
	}
}