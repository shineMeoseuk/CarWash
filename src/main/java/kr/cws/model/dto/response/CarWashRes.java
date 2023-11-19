package kr.cws.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CarWash Response Dto.
 *
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CarWashRes {

    private Long id;
    private String title;
    private String address;
    private Float reviewAverage;
}
