package kr.cws.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.cws.exception.DuplicatedException;
import kr.cws.mapper.ReservationMapper;
import kr.cws.mapper.ZoneMapper;
import kr.cws.model.dto.request.ZoneReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ZoneServiceTest {

    @InjectMocks
    ZoneService zoneService;

    @Mock
    ZoneMapper zoneMapper;

    @Mock
    ReservationMapper reservationMapper;

    ZoneReq zoneReq;

    @BeforeEach
    public void beforeEach() {
        zoneReq = ZoneReq.builder()
            .number(1)
            .build();
    }

    @Test
    @DisplayName("자리 삭제에 성공합니다.")
    public void deleteZoneTestWhenSuccess() {
        when(reservationMapper.isExistsNowReservationByZoneId(anyLong())).thenReturn(false);
        zoneService.deleteZone(anyLong());
        verify(reservationMapper).isExistsNowReservationByZoneId(anyLong());
        verify(zoneMapper).deleteZone(anyLong());
    }

    @Test
    @DisplayName("자리 삭제에 실패합니다. :등록된 예약이 존재합니다.")
    public void deleteZoneTestWhenFail() {
        when(reservationMapper.isExistsNowReservationByZoneId(anyLong())).thenReturn(true);
        assertThrows(DuplicatedException.class, () -> zoneService.deleteZone(anyLong()));
    }
}
