package kr.cws.service;

import kr.cws.exception.DuplicatedException;
import kr.cws.mapper.ReservationMapper;
import kr.cws.mapper.ZoneMapper;
import kr.cws.model.domain.Zone;
import kr.cws.model.dto.request.ZoneReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Zone Service
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneMapper zoneMapper;
    private final ReservationMapper reservationMapper;

    /**
     * 세차장 내 해당 방 번호가 존재하는지 확인.
     *
     * @param zoneNumber 자리 번호.
     * @param carWashId  세차장 ID.
     * @since 1.0.0
     */
    @Transactional(readOnly = true)
    public boolean isExistsZoneNumber(Integer zoneNumber, Long carWashId) {
        return zoneMapper.isExistsZoneNumber(zoneNumber, carWashId);
    }

    /**
     * 자리 추가 생성하기.
     *
     * @param zoneReq   자리 생성 DTO.
     * @param carWashId 세차장 ID.
     * @since 1.0.0
     */
    public void createZone(ZoneReq zoneReq, Long carWashId) {
        if (isExistsZoneNumber(zoneReq.getNumber(), carWashId)) {
            throw new DuplicatedException("This zone number already exists.");
        }

        Zone zone = Zone.builder()
            .carWashId(carWashId)
            .number(zoneReq.getNumber())
            .build();

        zoneMapper.insertZone(zone);
    }

    /**
     * 자리 수정하기.
     *
     * @param zoneReq   자리 생성 DTO.
     * @param zoneId    자리 ID.
     * @param carWashId 세차장 ID.
     * @since 1.0.0
     */
    public void updateZone(ZoneReq zoneReq, Long zoneId, Long carWashId) {
        if (isExistsZoneNumber(zoneReq.getNumber(), carWashId)) {
            throw new DuplicatedException("This zone number already exists.");
        }

        Zone zone = Zone.builder()
            .id(zoneId)
            .carWashId(carWashId)
            .number(zoneReq.getNumber())
            .build();

        zoneMapper.updateZone(zone);
    }

    /**
     * 자리 삭제하기.
     *
     * @param zoneId 자리 ID.
     * @since 1.0.0
     */
    public void deleteZone(Long zoneId) {
        if (reservationMapper.isExistsNowReservationByZoneId(zoneId)) {
            throw new DuplicatedException("A reservation exists at that time.");
        }
        zoneMapper.deleteZone(zoneId);
    }
}
