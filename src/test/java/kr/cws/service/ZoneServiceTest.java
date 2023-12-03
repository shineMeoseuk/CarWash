package kr.cws.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.cws.exception.DuplicatedException;
import kr.cws.mapper.ReservationMapper;
import kr.cws.mapper.ZoneMapper;
import kr.cws.model.domain.Zone;
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
    @DisplayName("자리 생성에 성공합니다.")
    public void createZoneTestWhenSuccess() {
        when(zoneMapper.isExistsZoneNumber(1, 1L)).thenReturn(false);
        zoneService.createZone(zoneReq, 1L);
        verify(zoneMapper).insertZone(any(Zone.class));
    }

    @Test
    @DisplayName("자리 생성에 실패합니다. :중복된 번호.")
    public void createZoneTestWhenFail() {
        when(zoneMapper.isExistsZoneNumber(1, 1L)).thenReturn(true);
        assertThrows(DuplicatedException.class, () -> zoneService.createZone(zoneReq, 1L));
    }

    @Test
    @DisplayName("자리 수정에 성공합니다.")
    public void updateZoneTestWhenSuccess() {
        when(zoneMapper.isExistsZoneNumber(anyInt(), anyLong())).thenReturn(false);
        zoneService.updateZone(zoneReq, 1L, 1L);
        verify(zoneMapper).updateZone(any(Zone.class));
    }

    @Test
    @DisplayName("자리 생성에 실패합니다. :중복된 자리 번호")
    public void updateZoneTestWhenFail() {
        when(zoneMapper.isExistsZoneNumber(anyInt(), anyLong())).thenReturn(true);
        assertThrows(DuplicatedException.class, () -> zoneService.updateZone(zoneReq, 1L, 1L));
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
