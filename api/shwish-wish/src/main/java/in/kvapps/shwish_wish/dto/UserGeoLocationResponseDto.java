package in.kvapps.shwish_wish.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGeoLocationResponseDto {
    private boolean isValidLocation;
    private double latitudeReceived;
    private double longitudeReceived;
}