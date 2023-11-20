package kr.cws.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import kr.cws.mapper.ReservationMapper;
import kr.cws.model.domain.Reservation;
import kr.cws.model.dto.request.ReservationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;

    public void createReservation(ReservationReq reservationReq, Long userId, Long zoneId) {
        LocalDateTime startTime = reservationReq.getStartTime();
        LocalDateTime endTime = reservationReq.getEndTime();

        if (startTime.isBefore(LocalDateTime.now()) || startTime.isAfter(endTime)
            || ChronoUnit.MINUTES.between(startTime, endTime) < 10) {
            throw new IllegalArgumentException("Please check your reservation time again.");
        }

        Reservation reservation = Reservation.builder()
            .userId(userId)
            .zoneId(zoneId)
            .startTime(startTime)
            .endTime(endTime)
            .build();

        reservationMapper.insertReservation(reservation);
    }
}
