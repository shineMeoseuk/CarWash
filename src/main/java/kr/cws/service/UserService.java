package kr.cws.service;

import kr.cws.exception.DuplicatedException;
import kr.cws.exception.NotFoundException;
import kr.cws.mapper.UserMapper;
import kr.cws.model.domain.User;
import kr.cws.model.dto.request.SignUpReq;
import kr.cws.model.dto.request.UserUpdateReq;
import kr.cws.model.dto.response.UserInfoRes;
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

    /**
     * Email 일치하는 유저 검색.
     *
     * @param email 이메일
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email)
            .orElseThrow(() -> new NotFoundException("Select not found user"));
    }

    /**
     * id에 해당하는 유저 삭제.
     *
     * @param userId 유저 ID
     * @since 1.0.0
     */

    public void deleteUser(Long userId) {
        userMapper.deleteUser(userId);
    }

    /**
     * id에 해당하는 유저 정보 조회.
     *
     * @param userId 유저 ID.
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public UserInfoRes getUserInfo(Long userId) {
        return userMapper.selectUserById(userId)
            .orElseThrow(() -> new NotFoundException("Select not found user"));
    }

    /**
     * id에 해당하는 유저 정보 수정.
     *
     * @param userId        유저 ID.
     * @param userUpdateReq 회원수정 DTO.
     * @since 1.0.0
     */
    public void updateUserInfo(Long userId, UserUpdateReq userUpdateReq) {
        User user = User.builder()
            .id(userId)
            .name(userUpdateReq.getName())
            .address(userUpdateReq.getAddress())
            .build();

        userMapper.updateUserById(user);
    }
}
