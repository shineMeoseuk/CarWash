package kr.cws.mapper;

import java.util.List;
import kr.cws.model.domain.Zone;
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

    boolean isExistsZoneNumber(@Param("zoneNumber") Integer zoneNumber,
        @Param("carWashId") Long carWashId);
}
