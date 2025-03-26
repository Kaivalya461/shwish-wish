package in.kvapps.shwish_wish.service;

import in.kvapps.shwish_wish.util.LocationValidator;
import org.springframework.stereotype.Service;

import static in.kvapps.shwish_wish.constant.ContentConstants.*;

@Service
public class ContentService {
    public String getQnAContent(String lat, String lon) {
        boolean isValid = LocationValidator.isLocationValid(Double.parseDouble(lat), Double.parseDouble(lon));
        if(isValid) {
            return QNA1 + "_" + QNA2;
        }
        return "Invalid";
    }

    public String getMessageContent(String lat, String lon, String ans1, String ans2) {
        boolean isValid = LocationValidator.isLocationValid(Double.parseDouble(lat), Double.parseDouble(lon));
        if(isValid) {
            return MSG1 + "_" + MSG2;
        }
        return "Invalid";
    }
}
