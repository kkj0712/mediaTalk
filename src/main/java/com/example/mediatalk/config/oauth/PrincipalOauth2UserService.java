package com.example.mediatalk.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.mediatalk.config.auth.PrincipalDetails;
import com.example.mediatalk.config.oauth.provider.FacebookUserInfo;
import com.example.mediatalk.config.oauth.provider.GoogleUserInfo;
import com.example.mediatalk.config.oauth.provider.OAuth2UserInfo;
import com.example.mediatalk.model.User;
import com.example.mediatalk.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	/* 구글로부터 받은 userRequest 데이터에 대해 후처리되는 함수
	 * 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	 * */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User=super.loadUser(userRequest);

		// 강제 회원가입
		OAuth2UserInfo oAuth2UserInfo=null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo=new GoogleUserInfo(oauth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			System.out.println("페이스북 로그인 요청");
			oAuth2UserInfo=new FacebookUserInfo(oauth2User.getAttributes());
		}else {
			System.out.println("구글 혹은 페이스북만 지원");
		}
		
		String provider=oAuth2UserInfo.getProvider();
		String providerId=oAuth2UserInfo.getProviderId(); 
		String username=provider+"_"+providerId; //username 중복방지
		String password=bCryptPasswordEncoder.encode("겟인데어");
		String email=oAuth2UserInfo.getEmail();
		String role="ROLE_USER";
		
		// 이미 회원가입이 되어있는지 확인
		User userEntity=userRepository.findByUsername(username);
		if(userEntity==null) {
			System.out.println("oauth 로그인 최초");
			userEntity=User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		}else {
			System.out.println("이미 oauth 로그인 회원입니다.");
		}
		
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	}
}