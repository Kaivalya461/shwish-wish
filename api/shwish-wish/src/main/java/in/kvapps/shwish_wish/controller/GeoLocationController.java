package in.kvapps.shwish_wish.controller;

import in.kvapps.shwish_wish.dto.UserGeoLocationResponseDto;
import in.kvapps.shwish_wish.model.UserLocation;
import in.kvapps.shwish_wish.service.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-geo-location")
@CrossOrigin("*.kvapps.in")
public class GeoLocationController {
    @Autowired private GeoLocationService geoLocationService;

    @PostMapping("/validate")
    public ResponseEntity<UserGeoLocationResponseDto> validLocation(@RequestBody UserLocation userLocation) {
        boolean isValidLocation = geoLocationService.validLocation(userLocation);
        return ResponseEntity.ok(
                UserGeoLocationResponseDto.builder()
                        .isValidLocation(isValidLocation)
                        .latitudeReceived(userLocation.getLatitude())
                        .longitudeReceived(userLocation.getLongitude())
                        .build());
    }
}
