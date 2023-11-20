package kr.cws.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import kr.cws.mapper.ReservationMapper;
import kr.cws.model.domain.Reservation;
import kr.cws.model.dto.request.ReservationReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    ReservationMapper reservationMapper;

    ReservationReq reservationReq;

    @BeforeEach
    public void beforeEach() {
        reservationReq = ReservationReq.builder()
            .startTime(LocalDateTime.now().plusMinutes(1))
            .endTime(LocalDateTime.now().plusMinutes(11))
            .build();
    }

    @Test
    @DisplayName("예약 생성에 성공합니다.")
    public void createReservationWhenSuccess() {
        reservationService.createReservation(reservationReq, 1L, 2L);
        verify(reservationMapper).insertReservation(any(Reservation.class));
    }

    @Test
    @DisplayName("예약 생성에 실패합니다. : 잘못된 시간 입력")
    public void createReservationWhenFail() {
        reservationReq.setEndTime(LocalDateTime.now().plusMinutes(2));
        assertThrows(IllegalArgumentException.class,
            () -> reservationService.createReservation(reservationReq, 1L, 2L));

        reservationReq.setStartTime(LocalDateTime.now().plusMinutes(100));
        assertThrows(IllegalArgumentException.class,
            () -> reservationService.createReservation(reservationReq, 1L, 2L));

        reservationReq.setStartTime(LocalDateTime.now().plusMinutes(1));
        assertThrows(IllegalArgumentException.class,
            () -> reservationService.createReservation(reservationReq, 1L, 2L));
    }
}
