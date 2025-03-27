package in.kvapps.shwish_wish.util;

import in.kvapps.shwish_wish.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static in.kvapps.shwish_wish.constant.ContentConstants.LOC1;
import static in.kvapps.shwish_wish.constant.ContentConstants.LOC2;

@Service
public class LocationValidator {
    private final List<double[]> validLocations = new ArrayList<>();

    public LocationValidator(@Autowired AppConfig appConfig) {
        // Add valid locations (latitude, longitude)
        validLocations.add(getLocation(LOC1, appConfig.getKey()));
        validLocations.add(getLocation(LOC2, appConfig.getKey()));
    }

    public boolean isLocationValid(double userLat, double userLong) {
        for (double[] location : validLocations) {
            if (calculateDistance(userLat, userLong, location[0], location[1]) <= 4) { // 4 km radius
                return true;
            }
        }
        return false;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Earth's radius in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    private double[] getLocation(String locationString, String key) {
        var stringArray = EncryptDecryptUtil.decrypt(locationString, key).split("_");

        return new double[]{
                Double.parseDouble(stringArray[0]),
                Double.parseDouble(stringArray[1]),
        };
    }
}

