package kr.cws.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import kr.cws.mapper.CarWashMapper;
import kr.cws.mapper.ZoneMapper;
import kr.cws.model.domain.CarWash;
import kr.cws.model.dto.request.CarWashReq;
import kr.cws.model.dto.request.ZoneReq;
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
        verify(carWashMapper).insertCafeWash(any(CarWash.class));
        verify(zoneMapper).insertZones(any());
        verify(zoneMapper).insertZones(any());

    }
}