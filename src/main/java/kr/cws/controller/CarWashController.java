package kr.cws.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.time.LocalDateTime;
import javax.validation.Valid;
import kr.cws.annotation.BlackCheck;
import kr.cws.annotation.CurrentUserId;
import kr.cws.annotation.LoginCheck;
import kr.cws.annotation.OwnerCheck;
import kr.cws.model.dto.request.CarWashReq;
import kr.cws.model.dto.request.ReservationReq;
import kr.cws.model.dto.request.ReviewReq;
import kr.cws.model.dto.request.SearchTimeReq;
import kr.cws.model.dto.request.ZoneReq;
import kr.cws.model.dto.response.CarWashDetailRes;
import kr.cws.model.dto.response.ReviewRes;
import kr.cws.service.CarWashService;
import kr.cws.service.ReservationService;
import kr.cws.service.ReviewService;
import kr.cws.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final ReviewService reviewService;
    private final ZoneService zoneService;
    private final ReservationService reservationService;

    /**
     * 세차장 등록하기.
     *
     * @param userId
     * @param carWashReq
     * @since 1.0.0
     */
    @PostMapping
    @LoginCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCarWash(@CurrentUserId Long userId,
        @Valid @RequestBody CarWashReq carWashReq) {
        carWashService.registerCarWash(userId, carWashReq);
    }

    /**
     * 세차장 상세 조회하기.
     *
     * @param userId        유저 ID - @BlackCheck 용도
     * @param carWashId     세차장 ID
     * @param searchTimeReq 검색시간 DTO
     * @since 1.0.0
     */
    @GetMapping("/{carWashId}")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.OK)
    public CarWashDetailRes getCarWash(@CurrentUserId Long userId,
        @PathVariable("carWashId") Long carWashId,
        @RequestBody(required = false) SearchTimeReq searchTimeReq) {
        if (searchTimeReq == null) {
            searchTimeReq = new SearchTimeReq(LocalDateTime.now());
        }
        return carWashService.getCarWash(carWashId, searchTimeReq);
    }

    /**
     * 북 마크 등록하기.
     *
     * @since 1.0.0
     */
    @PostMapping("/{carWashId}/bookmarks")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void registerBookmark(@CurrentUserId Long userId,
        @PathVariable("carWashId") Long carWashId) {
        carWashService.registerBookmark(userId, carWashId);
    }

    /**
     * 북 마크 취소하기.
     *
     * @since 1.0.0
     */
    @DeleteMapping("/{carWashId}/bookmarks")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBookmark(@CurrentUserId Long userId,
        @PathVariable("carWashId") Long carWashId) {
        carWashService.cancelBookmark(userId, carWashId);
    }

    /**
     * 자리 추가하기.
     *
     * @param userId    유저 ID - @BlackCheck 용도
     * @param carWashId 세차장 ID
     * @param zoneReq   Zone DTO
     * @since 1.0.0
     */
    @PostMapping("/{carWashId}/zones")
    @LoginCheck
    @OwnerCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void createZone(@CurrentUserId Long userId, @PathVariable("carWashId") Long carWashId,
        @RequestBody ZoneReq zoneReq) {
        zoneService.createZone(zoneReq, carWashId);
    }

    /**
     * 자리 수정하기
     *
     * @param userId
     * @param carWashId
     * @param zoneId
     * @param zoneReq
     * @since 1.0.0
     */
    @PutMapping("/{carWashId}/zones/{zoneId")
    @LoginCheck
    @OwnerCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.OK)
    public void updateZone(@CurrentUserId Long userId, @PathVariable("carWashId") Long carWashId,
        @PathVariable("zoneId") Long zoneId, @RequestBody ZoneReq zoneReq) {
        zoneService.updateZone(zoneReq, zoneId, carWashId);
    }

    /**
     * 자리 삭제하기.
     *
     * @param userId    유저 ID - @BlackCheck, @OwnerCheck 용도
     * @param carWashId 세차장 ID - @BlackCheck, @OwnerCheck 용도
     * @param zoneId    자리 ID
     * @since 1.0.0
     */
    @DeleteMapping("/{carWashId}/zones/{zoneId}")
    @LoginCheck
    @OwnerCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZone(@CurrentUserId Long userId, @PathVariable("carWashId") Long carWashId,
        @PathVariable("zoneId") Long zoneId) {
        zoneService.deleteZone(zoneId);
    }

    /**
     * 예약하기.
     *
     * @since 1.0.0
     */
    @PostMapping("/{carWashId}/zones/{zoneId}/reservations")
    @LoginCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(@CurrentUserId Long userId, @PathVariable("zoneId") Long zoneId,
        @Valid @RequestBody ReservationReq reservationReq) {
        reservationService.createReservation(reservationReq, userId, zoneId);
    }

    /**
     * 예약 취소하기
     *
     * @since 1.0.0
     */
    @DeleteMapping("/{carWashId}/zones/{zoneId}/reservations/{reservationId}")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@CurrentUserId Long userId,
        @PathVariable("reservationId") Long reservationId) {
        reservationService.cancelReservation(userId, reservationId);
    }

    /**
     * 리뷰 목록 조회하기.
     *
     * @since 1.0.0
     */
    @GetMapping("/{carWashId}/reviews")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.OK)
    public PageInfo<ReviewRes> getReviews(@PathVariable("carWashId") Long carWashId,
        @RequestParam("page") Integer page,
        @RequestParam("size") Integer size) {
        PageHelper.startPage(page, size);
        return PageInfo.of(reviewService.getReviews(carWashId));
    }

    /**
     * 리뷰 등록하기.
     *
     * @Param reviewReq 리뷰 DTO
     * @Param userId    유저 ID
     * @Param carWashId 세차장 ID
     * @since 1.0.0
     */
    @PostMapping("/{carWashId}/reviews")
    //@LoginCheck
    //@BlackCheck
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@CurrentUserId Long userId, @PathVariable("carWashId") Long carWashId,
        @Valid @RequestBody ReviewReq reviewReq) {
        reviewService.createReview(reviewReq, userId, carWashId);
    }

    /**
     * 리뷰 수정하기.
     *
     * @Param userId    유저 ID
     * @Param carWashId 세차장 ID - @BlackCheck 용도
     * @Param reviewId  리뷰 ID
     * @Param reviewReq 리뷰 DTO
     * @since 1.0.0
     */
    @PutMapping("/{carWashId}/reviews/{reviewId}")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.OK)
    public void updateReview(@CurrentUserId Long userId, @PathVariable("carWashId") Long carWashId,
        @PathVariable("reviewId") Long reviewId, @RequestBody ReviewReq reviewReq) {
        reviewService.updateReview(reviewReq, userId, reviewId);
    }


    /**
     * 리뷰 삭제하기.
     *
     * @Param userId    유저 ID
     * @Param carWashId 세차장 ID - @BlackCheck 용도
     * @Param reviewId  리뷰 ID
     * @since 1.0.0
     */
    @DeleteMapping("/{carWashId}/reviews{reviewId}")
    @LoginCheck
    @BlackCheck
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@CurrentUserId Long userId,
        @PathVariable("carWashId") Long carWashId, @PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
    }
}