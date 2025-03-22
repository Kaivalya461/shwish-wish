package in.kvapps.shwish_wish.service;

import in.kvapps.shwish_wish.model.UserLocation;
import in.kvapps.shwish_wish.util.LocationValidator;
import org.springframework.stereotype.Service;

@Service
public class GeoLocationService {
    public boolean validLocation(UserLocation userLocation) {

        return LocationValidator.isLocationValid(
                userLocation.getLatitude(),
                userLocation.getLongitude()
        );
    }
}
