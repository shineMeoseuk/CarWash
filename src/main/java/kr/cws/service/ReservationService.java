package kr.cws.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import kr.cws.annotation.LoginCheck;
import kr.cws.exception.DuplicatedException;
import kr.cws.exception.NotFoundException;
import kr.cws.mapper.ReservationMapper;
import kr.cws.model.domain.Reservation;
import kr.cws.model.dto.request.ReservationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;

    /**
     * 예약하기.
     * <p>
     * 1. 시작 시간이 현재 시간보다 앞에 있는 경우. 2. 시작 시간이 완료시간보다 뒤에 있는 경우. 3. 총 이용 시간이 10분 이내일 경우 예외 발생.
     *
     * @param reservationReq
     * @param userId
     * @param zoneId
     */
    public void createReservation(ReservationReq reservationReq, Long userId, Long zoneId) {
        LocalDateTime startTime = reservationReq.getStartTime();
        LocalDateTime endTime = reservationReq.getEndTime();

        if (startTime.isBefore(LocalDateTime.now()) || startTime.isAfter(endTime)
            || ChronoUnit.MINUTES.between(startTime, endTime) < 10) {
            throw new IllegalArgumentException("Please check your reservation time again.");
        }

        if (reservationMapper.isExistsReservationByZoneIdAndUseTime(zoneId, startTime, endTime)) {
            throw new DuplicatedException("This Reservation Time already exists.");
        }

        Reservation reservation = Reservation.builder()
            .userId(userId)
            .zoneId(zoneId)
            .startTime(startTime)
            .endTime(endTime)
            .build();

        reservationMapper.insertReservation(reservation);
    }


    /**
     * 예약 취소하기. 예약 시작 시간 10분 전에 취소 가능. 10분 이내일 경우 예외 발생.
     *
     * @since 1.0.0
     */
    public void cancelReservation(Long userId, Long reservationId) {
        Optional<Reservation> reservation = reservationMapper.selectReservationByIdAndUserId(userId,
            reservationId);

        reservation.orElseThrow(() -> new NotFoundException("Select not found reservation"));
        if (reservation.get().getStartTime().minusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservations can not be canceled 10 minutes.");
        }

        reservationMapper.deleteReservation(reservationId);
    }

}
