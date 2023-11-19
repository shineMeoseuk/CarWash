package kr.cws.service;

import kr.cws.exception.DuplicatedException;
import kr.cws.mapper.UserMapper;
import kr.cws.model.domain.User;
import kr.cws.model.dto.request.SignUpReq;
import kr.cws.utils.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Service.
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    /**
     * 회원 가입.
     *
     * @param signUpReq 회원가입 DTO
     * @since 1.0.0
     */

    public void signUp(SignUpReq signUpReq) {
        if (isExistsEmail(signUpReq.getEmail())) {
            throw new DuplicatedException("This email already exists.");
        }

        User user = User.builder()
            .email(signUpReq.getEmail())
            .password(PasswordEncrypt.encrypt(signUpReq.getPassword()))
            .name(signUpReq.getName())
            .address(signUpReq.getAddress())
            .build();
        userMapper.insertUser(user);
    }

    @Transactional(readOnly = true)
    public boolean isExistsEmail(String email) {
        return userMapper.isExistsEmail(email);
    }
}
