package kr.cws.model.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.cws.exception.DuplicatedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CarWash Request Dto.
 *
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarWashReq {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String address;

    private String thumbnail;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ZoneReq> zones;

    /**
     * Zones 입력시 중복된 자리 번호가 있는지 체크. 중복된 자리가 있다면 DuplicatedZoneNumberException 발생.
     *
     * @since 1.0.0
     */
    public void setZones(List<ZoneReq> zones) {
        if (zones.size() != zones.stream().distinct().count()) {
            throw new DuplicatedException("This zone number already exists.");
        }

        this.zones = zones;
    }
}
