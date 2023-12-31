package kr.cws.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.cws.exception.DuplicatedException;
import kr.cws.exception.NotFoundException;
import kr.cws.mapper.UserMapper;
import kr.cws.model.domain.User;
import kr.cws.model.dto.request.SignUpReq;
import kr.cws.model.dto.request.UserUpdateReq;
import kr.cws.model.dto.response.UserInfoRes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    User user;
    SignUpReq signUpReq;
    UserInfoRes userInfoRes;

    @BeforeEach
    public void makeUser() {
        user = User.builder()
            .id(1L)
            .email("email@email.com")
            .password("name")
            .name("name")
            .address("address")
            .build();

        signUpReq = SignUpReq.builder()
            .email("email@email.com")
            .password("password")
            .name("name")
            .address("address")
            .build();

        userInfoRes = UserInfoRes.builder()
            .email("email")
            .name("name")
            .address("address")
            .build();
    }

    @Test
    @DisplayName("회원가입에 성공합니다.")
    public void signUpTestWhenSuccess() {
        when(userMapper.isExistsEmail(signUpReq.getEmail())).thenReturn(false);
        userService.signUp(signUpReq);
        verify(userMapper).insertUser(any(User.class));
    }

    @Test
    @DisplayName("회원가입에 실패합니다. :중복된 이메일")
    public void signUpTestWhenFail() {
        when(userMapper.isExistsEmail(signUpReq.getEmail())).thenReturn(true);
        assertThrows(DuplicatedException.class, () -> userService.signUp(signUpReq));
        verify(userMapper).isExistsEmail(signUpReq.getEmail());
    }

    @Test
    @DisplayName("회원 조회에 성공합니다.")
    public void selectUserByEmailTestWhenSuccess() {
        when(userMapper.selectUserByEmail(signUpReq.getEmail())).thenReturn(Optional.ofNullable(user));
        userService.selectUserByEmail(signUpReq.getEmail());
    }

    @Test
    @DisplayName("회원 조회에 실패합니다. :존재하지 않는 이메일")
    public void selectUserByEmailTestWhenFail() {
        assertThrows(NotFoundException.class, () -> userService.selectUserByEmail(signUpReq.getEmail()));
    }

    @Test
    @DisplayName("회원탈퇴에 성공합니다.")
    public void deleteUserWhenSuccess() {
        userService.deleteUser(user.getId());
        verify(userMapper).deleteUser(user.getId());
    }

    @Test
    @DisplayName("회원 정보 조회에 성공합니다.")
    public void getUserInfoWhenSuccess() {
        when(userMapper.selectUserById(1L)).thenReturn(Optional.ofNullable(userInfoRes));
        userService.getUserInfo(1L);
        verify(userMapper).selectUserById(1L);
    }

    @Test
    @DisplayName("회원 정보 조회에 실패합니다. :존재하지 않는 유저 ID.")
    public void getUserInfoWhenFail() {
        assertThrows(NotFoundException.class, () -> userService.getUserInfo(anyLong()));
    }

    @Test
    @DisplayName("회원 정보 수정에 성공합니다.")
    public void updateUserInfoWhenSuccess() {
        UserUpdateReq requestDto = UserUpdateReq.builder().build();
        userService.updateUserInfo(user.getId(), requestDto);
        verify(userMapper).updateUserById(any(User.class));
    }

}
