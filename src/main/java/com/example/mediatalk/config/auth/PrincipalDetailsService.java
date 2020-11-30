package com.example.mediatalk.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.mediatalk.model.User;
import com.example.mediatalk.repository.UserRepository;


@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	/* return된 값이 Authentication내부에 들어간다.
	 * =>Security session(내부 Authentication(내부 UserDetails))
	 * 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	 * */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUsername(username);
		if(user==null) {
			return null;
		}
		return new PrincipalDetails(user);
	}
}
