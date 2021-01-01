package com.example.demo.user;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.point.PointRepository;
import com.example.demo.upload.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

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

	private final ProductRepository productRepository;

	private final PointRepository pointRepository;
	private final S3Service s3Service;

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

	/**
	 * 유저의 완전한 정보를 업데이트하여 저장한다.
	 * @param dto
	 */
	@Transactional
	public Long updateUserInfo(UserDto dto) throws IOException {
		User findUser = findByEmail(dto.getEmail());
		findUser.setBirthday(dto.getBirthday());
		findUser.setGender(dto.getGender());
		findUser.setNickname(dto.getNickname());
		s3Service.upload("", dto.getProfile());
		findUser.setProfileImageUrl(dto.getProfileImageUrl());
		return findUser.getId();
	}

    @Transactional
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
		return user;
	}

	@Transactional
	public List<Product> findSellingList(String email) {
		User findUser = findByEmail(email);
		return findUser.getSellingProducts();
	}

	@Transactional
	public List<Product> findBuyingList(String email) {
		User findUser = findByEmail(email);
		return findUser.getBuyingProducts();
	}

	@Transactional
    public void updatePointInfo(long l, int pointAmount) {
		User user = userRepository.findById(l).orElseThrow();
		user.setPointSum(user.getPointSum()+pointAmount);
    }
}