package com.lemorning.demo.user;

import com.lemorning.demo.product.Product;
import com.lemorning.demo.product.ProductRepository;
import com.lemorning.demo.point.PointRepository;
import com.lemorning.demo.upload.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

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
	 * @param dto 회원정보가 들어있는 DTO
	 * @return 저장되는 회원의 PK
	 */
	@Transactional
	public User save(UserDto dto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		dto.setPassword(encoder.encode(dto.getPassword()));

		try {
			String profileImageUrl = s3Service.upload("", dto.getProfile());
			return userRepository.save(User.builder()
					.email(dto.getEmail())
					.password(dto.getPassword())
					.auth(dto.getAuth())
					.nickname(dto.getNickname())
					.birthday(dto.getBirthday())
					.gender(dto.getGender())
					.profileImageUrl(profileImageUrl)
					.build());
		}catch (Exception e){
			log.info("user signup error");
		}
		return null;
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