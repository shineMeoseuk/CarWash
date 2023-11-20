package kr.cws.mapper;

import kr.cws.model.domain.Reservation;
import org.apache.ibatis.annotations.Mapper;

/**
 * Reservation Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface ReservationMapper {

    void insertReservation(Reservation reservation);
}
