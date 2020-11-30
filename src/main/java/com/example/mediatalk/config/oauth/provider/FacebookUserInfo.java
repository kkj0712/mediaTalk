package com.example.mediatalk.config.oauth.provider;

import java.util.Map;

public class FacebookUserInfo implements OAuth2UserInfo{
	
	private Map<String, Object> attributes; //oauth2User가 들고 있는 getAttributes()
	
	public FacebookUserInfo(Map<String, Object> attributes) {
		this.attributes=attributes;
	}

	@Override
	public String getProviderId() {
		return (String) attributes.get("id"); //String으로 오버라이딩
	}

	@Override
	public String getProvider() {
		return "facebook";
	}

	@Override
	public String getEmail() {
		if(attributes.get("email")==null) {
			String fbEmail=(String) attributes.get("id")+"@facebook.com";
			//facebook은 번호로도 가입할 수 있으므로 fakeEmail생성
			return fbEmail;
		}
		return (String) attributes.get("email");  
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

}
