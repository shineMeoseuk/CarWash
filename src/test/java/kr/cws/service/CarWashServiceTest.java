package kr.cws.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.cws.exception.DuplicatedException;
import kr.cws.exception.NotFoundException;
import kr.cws.mapper.CarWashMapper;
import kr.cws.mapper.ZoneMapper;
import kr.cws.model.domain.CarWash;
import kr.cws.model.dto.request.CarWashReq;
import kr.cws.model.dto.request.SearchTimeReq;
import kr.cws.model.dto.request.ZoneReq;
import kr.cws.model.dto.response.CarWashDetailRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarWashServiceTest {

    @InjectMocks
    CarWashService carWashService;

    @Mock
    CarWashMapper carWashMapper;

    @Mock
    ZoneMapper zoneMapper;

    CarWashReq carWashReq;

    @Test
    @DisplayName("세차장 등록에 성공합니다.")
    public void registerCarWashTestWhenSuccess() {
        List<ZoneReq> zones = new ArrayList<>();
        ZoneReq zone1 = ZoneReq.builder()
            .number(1)
            .build();

        ZoneReq zone2 = ZoneReq.builder()
            .number(2)
            .build();

        zones.add(zone1);
        zones.add(zone2);

        carWashReq = CarWashReq.builder()
            .title("title")
            .address("address")
            .thumbnail("thumbnail")
            .zones(zones)
            .build();

        carWashService.registerCarWash(1L, carWashReq);
        verify(carWashMapper).insertCarWash(any(CarWash.class));
        verify(zoneMapper).insertZones(any());
        verify(zoneMapper).insertZones(any());
    }

    @Test
    @DisplayName("세차장 상세 조회에 성공합니다.")
    public void getCarWashTestWhenSuccess() {
        when(carWashMapper.selectCarWashDetailById(anyLong())).thenReturn(
            Optional.of(new CarWashDetailRes()));
        when(zoneMapper.selectZoneUseInfo(anyLong(), any(LocalDateTime.class))).thenReturn(any());
        carWashService.getCarWash(1L, new SearchTimeReq(LocalDateTime.now()));
        verify(carWashMapper).selectCarWashDetailById(anyLong());
        verify(zoneMapper).selectZoneUseInfo(anyLong(), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("세차장 상세 조회에 실패합니다. :존재하지 않는 세차장")
    public void getCarWashTestWhenFail() {
        when(carWashMapper.selectCarWashDetailById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
            () -> carWashService.getCarWash(1L, new SearchTimeReq(LocalDateTime.now())));
    }

    @Test
    @DisplayName("북마크 등록에 성공합니다.")
    public void registerBookmarkTestWhenSuccess() {
        carWashService.registerBookmark(1L, 1L);
        verify(carWashMapper).insertBookmark(1L, 1L);
    }

    @Test
    @DisplayName("북마크 등록에 실패합니다. :이미 등록된 북마크")
    public void registerBookmarkTestWhenFail() {
        when(carWashMapper.isExistsBookmark(1L, 1L)).thenReturn(true);
        assertThrows(DuplicatedException.class, () -> carWashService.registerBookmark(1L, 1L));
    }

    @Test
    @DisplayName("북마크 취소에 성공합니다.")
    public void cancelBookmarkTestWhenSuccess() {
        when(carWashMapper.deleteBookmark(1L, 1L)).thenReturn(1);
        carWashService.cancelBookmark(1L, 1L);
        verify(carWashMapper).deleteBookmark(1L, 1L);
    }

    @Test
    @DisplayName("북마크 취소에 실패합니다. :등록하지 않은 북마크")
    public void cancelBookmarkTestWhenFail() {
        when(carWashMapper.deleteBookmark(1L, 1L)).thenReturn(0);
        assertThrows(NotFoundException.class, () -> carWashService.cancelBookmark(1L, 1L));
    }
}