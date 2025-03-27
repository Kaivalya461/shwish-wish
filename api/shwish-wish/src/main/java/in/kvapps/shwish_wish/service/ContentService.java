package in.kvapps.shwish_wish.service;

import in.kvapps.shwish_wish.dto.ContentDto;
import in.kvapps.shwish_wish.util.LocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static in.kvapps.shwish_wish.constant.ContentConstants.*;

@Service
public class ContentService {
    @Autowired private LocationValidator locationValidator;

    public ContentDto getQnAContent(String lat, String lon) {
        ContentDto contentDto = new ContentDto();
        boolean isValid = locationValidator.isLocationValid(Double.parseDouble(lat), Double.parseDouble(lon));
        if(isValid) {
             contentDto.setQna(QNA1 + "_" + QNA2);
        }
        return contentDto;
    }

    public ContentDto getMessageContent(String lat, String lon, String answers) {
        ContentDto contentDto = new ContentDto();
        boolean isValid = locationValidator.isLocationValid(Double.parseDouble(lat), Double.parseDouble(lon));
        boolean allValidAnswers = isValidAnswers(answers);
        if(isValid && allValidAnswers) {
            contentDto.setMsg(MSG1 + "_" + MSG2);
        }

        return contentDto;
    }

    private boolean isValidAnswers(String answers) {
        String[] answerArray = answers.split("_");


        return false;
    }
}
