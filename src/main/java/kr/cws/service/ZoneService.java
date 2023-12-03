package kr.cws.service;

import kr.cws.exception.DuplicatedException;
import kr.cws.mapper.ReservationMapper;
import kr.cws.mapper.ZoneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void deleteZone(Long zoneId) {
        if (reservationMapper.isExistsNowReservationByZoneId(zoneId)) {
            throw new DuplicatedException("A reservation exists at that time.");
        }
        zoneMapper.deleteZone(zoneId);
    }
}
