package kr.cws.mapper;

import kr.cws.model.domain.CarWash;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * CafeWash Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface CarWashMapper {

    void insertCarWash(CarWash carWash);

    boolean isBlockUser(@Param("userId") Long userId, @Param("carWashId") Long carWashId);
}
