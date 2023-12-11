package kr.cws.model.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Zone Model.
 *
 * @since 1.0.0
 */
@Builder
@Getter
public class Zone {

    private Long id;
    private Long carWashId;
    private Integer number;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
