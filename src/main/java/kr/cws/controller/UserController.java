package kr.cws.controller;

import javax.validation.Valid;
import kr.cws.annotation.CurrentUserId;
import kr.cws.annotation.LoginCheck;
import kr.cws.model.dto.request.LoginReq;
import kr.cws.model.dto.request.SignUpReq;
import kr.cws.service.LoginService;
import kr.cws.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller.
 *
 * @since 1.0.0
 */
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    /**
     * 회원 가입.
     *
     * @param signUpReq 회원가입 입력 정보
     * @since 1.0.0
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody SignUpReq signUpReq) {
        userService.signUp(signUpReq);
    }

    /**
     * 로그인
     *
     * @param loginReq 로그인 입력 정보
     * @since 1.0.0
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@Valid @RequestBody LoginReq loginReq) {
        loginService.login(loginReq);
    }

    /**
     * 로그아웃
     *
     * @since 1.0.0
     */
    @PostMapping("/logout")
    @LoginCheck
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        loginService.logout();
    }

    /**
     * 회원 탈퇴
     *
     * @param userId 유저 ID.
     * @since 1.0.0
     */
    @DeleteMapping
    @LoginCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@CurrentUserId Long userId) {
        userService.deleteUser(userId);
    }
}
