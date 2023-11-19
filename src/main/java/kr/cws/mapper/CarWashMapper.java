package kr.cws.mapper;

import kr.cws.model.domain.CarWash;
import org.apache.ibatis.annotations.Mapper;

/**
 * CafeWash Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface CarWashMapper {

    void insertCafeWash(CarWash carWash);
}
