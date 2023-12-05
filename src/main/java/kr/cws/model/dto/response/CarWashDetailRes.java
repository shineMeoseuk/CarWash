package kr.cws.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CarWashDetailRes {

    private Long id;
    private String title;
    private String address;
    private String thumbnail;
    private Long useCount;
    private Long bookmarkCount;
    private Float reviewAverage;
    private List<ZoneUseInfoRes> zones;
}
