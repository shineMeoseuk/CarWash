package kr.cws.mapper;

import java.util.List;
import kr.cws.model.domain.Zone;
import org.apache.ibatis.annotations.Mapper;

/**
 * Zone Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface ZoneMapper {

    void insertZones(List<Zone> zones);
}
