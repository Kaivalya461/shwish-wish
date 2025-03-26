package in.kvapps.shwish_wish.service;

import in.kvapps.shwish_wish.dto.ContentDto;
import in.kvapps.shwish_wish.util.LocationValidator;
import org.springframework.stereotype.Service;

import static in.kvapps.shwish_wish.constant.ContentConstants.*;

@Service
public class ContentService {
    public ContentDto getQnAContent(String lat, String lon) {
        ContentDto contentDto = new ContentDto();
        boolean isValid = LocationValidator.isLocationValid(Double.parseDouble(lat), Double.parseDouble(lon));
        if(isValid) {
             contentDto.setQna(QNA1 + "_" + QNA2);
        }
        return contentDto;
    }

    public ContentDto getMessageContent(String lat, String lon, String answers) {
        ContentDto contentDto = new ContentDto();
        boolean isValid = LocationValidator.isLocationValid(Double.parseDouble(lat), Double.parseDouble(lon));
        if(isValid) {
            contentDto.setMsg(MSG1 + "_" + MSG2);
        }

        return contentDto;
    }
}
