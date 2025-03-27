package in.kvapps.shwish_wish.service;

import in.kvapps.shwish_wish.model.UserLocation;
import in.kvapps.shwish_wish.util.LocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeoLocationService {
    @Autowired private LocationValidator locationValidator;

    public boolean validLocation(UserLocation userLocation) {

        return locationValidator.isLocationValid(
                userLocation.getLatitude(),
                userLocation.getLongitude()
        );
    }
}
