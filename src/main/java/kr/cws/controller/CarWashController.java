package kr.cws.controller;

import javax.validation.Valid;
import kr.cws.annotation.CurrentUserId;
import kr.cws.annotation.LoginCheck;
import kr.cws.model.dto.request.CarWashReq;
import kr.cws.service.CarWashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * CarWash 컨트롤러.
 *
 * @since 1.0.0
 */
@RequestMapping("/carwash")
@RequiredArgsConstructor
@RestController
public class CarWashController {

    private final CarWashService carWashService;

    /**
     * 세차장 등록하기.
     *
     * @param userId
     * @param carWashReq
     * @since 1.0.0
     */
    @PostMapping
    //@LoginCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCarWash(@CurrentUserId Long userId,
        @Valid @RequestBody CarWashReq carWashReq) {
        carWashService.registerCarWash(userId, carWashReq);
    }
}
