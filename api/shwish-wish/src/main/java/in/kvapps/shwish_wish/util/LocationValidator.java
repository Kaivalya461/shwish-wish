package in.kvapps.shwish_wish.util;

import java.util.ArrayList;
import java.util.List;

public class LocationValidator {
    private static final List<double[]> validLocations = new ArrayList<>();

    static {
        // Add valid locations (latitude, longitude)
        validLocations.add(new double[]{19.0760, 72.8777}); // Example: Mumbai
        validLocations.add(new double[]{28.6139, 77.2090}); // Example: Delhi
    }

    public static boolean isLocationValid(double userLat, double userLong) {
        for (double[] location : validLocations) {
            if (calculateDistance(userLat, userLong, location[0], location[1]) <= 10) { // 10 km radius
                return true;
            }
        }
        return false;
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Earth's radius in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}

