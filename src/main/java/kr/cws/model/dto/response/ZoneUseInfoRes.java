package kr.cws.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Zone 사용 정보 Response Dto.
 *
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ZoneUseInfoRes {

    private Long id;
    private Integer number;
    private Boolean currentUse;
}
