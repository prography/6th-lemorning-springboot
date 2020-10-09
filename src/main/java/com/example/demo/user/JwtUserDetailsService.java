package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

	//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		if ("user_id".equals(username)) {
//			return new User("user_id", "$2a$10$jCvWm3NXDRFs/EfuI4h4.u0ZxNocv.ZkgEy6qbjVXrfQ5.KzLfhAe",
//					new ArrayList<>());
//		} else {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//	}
	private final UserRepository userRepository;

	/**
	 * Spring Security 필수 메소드 구현
	 *
	 * @param email 이메일
	 * @return UserDetails
	 * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
	 */
	@Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
	public User loadUserByUsername(String email) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException((email)));
	}
	/**
	 * 회원정보 저장
	 *
	 * @param infoDto 회원정보가 들어있는 DTO
	 * @return 저장되는 회원의 PK
	 */
	@Transactional
	public Long save(UserDto infoDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		infoDto.setPassword(encoder.encode(infoDto.getPassword()));

		return userRepository.save(User.builder()
				.email(infoDto.getEmail())
				.auth(infoDto.getAuth())
				.password(infoDto.getPassword()).build()).getId();
	}

	@Transactional
    public void addPoint(String name, int amount) {
		User user = userRepository.findByEmail(name).orElseThrow(EntityNotFoundException::new);
		user.setPoint(amount+user.getPoint());
    }

    @Transactional
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
		return user;
	}
}