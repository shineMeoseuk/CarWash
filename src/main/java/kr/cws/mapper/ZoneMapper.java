package kr.cws.mapper;

import java.time.LocalDateTime;
import java.util.List;
import kr.cws.model.domain.Zone;
import kr.cws.model.dto.response.ZoneUseInfoRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Zone Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface ZoneMapper {

    void insertZones(List<Zone> zones);

    void insertZone(Zone zone);

    void updateZone(Zone zone);

    void deleteZone(Long zoneId);

    List<ZoneUseInfoRes> selectZoneUseInfo(@Param("carwashId") Long carWashId, @Param("searchTime")
    LocalDateTime searchTime);

    boolean isExistsZoneNumber(@Param("zoneNumber") Integer zoneNumber,
        @Param("carWashId") Long carWashId);
}
