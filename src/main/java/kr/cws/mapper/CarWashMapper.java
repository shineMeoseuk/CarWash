package kr.cws.mapper;

import java.util.Optional;
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

    void insertBookmark(@Param("userId") Long userId, @Param("carWashId") Long carWashId);

    int deleteBookmark(@Param("userId") Long userId, @Param("carWashId") Long carWashId);

    boolean isBlockUser(@Param("userId") Long userId, @Param("carWashId") Long carWashId);

    Optional<CarWash> selectCarWashById(Long carWashId);

    boolean isExistsBookmark(@Param("userId") Long userId, @Param("carWashId") Long carWashId);
}
