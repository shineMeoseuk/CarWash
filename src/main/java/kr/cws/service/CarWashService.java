package kr.cws.service;

import java.util.ArrayList;
import java.util.List;
import kr.cws.mapper.CarWashMapper;
import kr.cws.mapper.ZoneMapper;
import kr.cws.model.domain.CarWash;
import kr.cws.model.domain.Zone;
import kr.cws.model.dto.request.CarWashReq;
import kr.cws.model.dto.request.ZoneReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CarWash Service.
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CarWashService {

    private final CarWashMapper carWashMapper;
    private final ZoneMapper zoneMapper;

    /**
     * 세차장 등록하기. 입력 받은 세차장 정보로 등록. 생성한 세차장의 id를 Zone에 지정한 후 zone 생성.
     *
     * @param userId     유저 ID
     * @param carWashReq 세차장 DTO
     * @since 1.0.0
     */

    public void registerCarWash(Long userId, CarWashReq carWashReq) {
        CarWash carWash = CarWash.builder()
            .userId(userId)
            .title(carWashReq.getTitle())
            .address(carWashReq.getAddress())
            .thumbnail(carWashReq.getThumbnail())
            .build();

        carWashMapper.insertCafeWash(carWash);

        List<Zone> zones = new ArrayList<>();
        for (ZoneReq zoneReq : carWashReq.getZones()) {
            Zone zone = Zone.builder()
                .carWashId(carWash.getId())
                .number(zoneReq.getNumber())
                .build();

            zones.add(zone);
        }

        zoneMapper.insertZones(zones);
    }
}
