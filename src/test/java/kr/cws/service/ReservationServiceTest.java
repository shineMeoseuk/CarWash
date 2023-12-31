package kr.cws.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import kr.cws.exception.NotFoundException;
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

    @Test
    @DisplayName("예약 취소에 성공합니다.")
    public void cancelReservationWhenSuccess() {
        Reservation reservation = Reservation.builder()
            .id(1L)
            .startTime(LocalDateTime.now().plusMinutes(11))
            .build();

        when(reservationMapper.selectReservationByIdAndUserId(1L, 2L)).thenReturn(
            Optional.of(reservation));
        reservationService.cancelReservation(1L, 2L);
        verify(reservationMapper).selectReservationByIdAndUserId(1L, 2L);
        verify(reservationMapper).deleteReservation(2L);
    }

    @Test
    @DisplayName("예약 취소에 실패합니다. :존재하지 않는 예약입니다.")
    public void cancelReservationWhenFail1() {
        when(reservationMapper.selectReservationByIdAndUserId(1L, 2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> reservationService.cancelReservation(1L, 2L));
    }

    @Test
    @DisplayName("예약 취소에 실패합니다. :예약 취소는 10분전에 할 수 없습니다.")
    public void cancelReservationWhenFail2() {
        Reservation reservation = Reservation.builder()
            .id(1L)
            .startTime(LocalDateTime.now().plusMinutes(5))
            .endTime(LocalDateTime.now().plusHours(1))
            .build();

        when(reservationMapper.selectReservationByIdAndUserId(1L, 2L)).thenReturn(
            Optional.of(reservation));
        assertThrows(IllegalArgumentException.class,
            () -> reservationService.cancelReservation(1L, 2L));
    }

    @Test
    @DisplayName("예약 취소에 실패합니다. :이미 진행중인 예약입니다.")
    public void cancelReservationWhenFail3() {
        Reservation reservation = Reservation.builder()
            .id(1L)
            .startTime(LocalDateTime.now().minusHours(1))
            .endTime(LocalDateTime.now().plusHours(1))
            .build();

        when(reservationMapper.selectReservationByIdAndUserId(1L, 2L)).thenReturn(
            Optional.of(reservation));
        assertThrows(IllegalArgumentException.class,
            () -> reservationService.cancelReservation(1L, 2L));
    }

    @Test
    @DisplayName("예약 취소에 실패합니다. :이미 종료된 예약입니다.")
    public void cancelReservationWhenFail4() {
        Reservation reservation = Reservation.builder()
            .id(1L)
            .startTime(LocalDateTime.now().minusHours(2))
            .endTime(LocalDateTime.now().plusHours(1))
            .build();

        when(reservationMapper.selectReservationByIdAndUserId(1L, 2L)).thenReturn(
            Optional.of(reservation));
        assertThrows(IllegalArgumentException.class,
            () -> reservationService.cancelReservation(1L, 2L));
    }
}
