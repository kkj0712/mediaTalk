package com.example.mediatalk.config.oauth.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{
	
	private Map<String, Object> attributes; //oauth2User가 들고 있는 getAttributes()
	
	public GoogleUserInfo(Map<String, Object> attributes) {
		this.attributes=attributes;
	}

	@Override
	public String getProviderId() {
		return (String) attributes.get("sub"); //String으로 오버라이딩
	}

	@Override
	public String getProvider() {
		return "google";
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

}