package in.kvapps.shwish_wish.service;

import in.kvapps.shwish_wish.dto.ContentDto;
import in.kvapps.shwish_wish.util.EncryptDecryptUtil;
import in.kvapps.shwish_wish.util.LocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        boolean allValidAnswers = isValidAnswers(answers, lat, lon);
        if(isValid && allValidAnswers) {
            contentDto.setMsg(MSG1 + "_" + MSG2);
        }

        return contentDto;
    }

    private boolean isValidAnswers(String answers, String lat, String lon) {
        String[] answerArray = answers.split("_");
        String key = getQNAKey(lat, lon);
        String val1 = EncryptDecryptUtil.encrypt(sanitizeText(answerArray[0]), key+key);
        String val2 = EncryptDecryptUtil.encrypt(sanitizeText(answerArray[1]), key+key);
        if (Objects.equals(val1, ANS1) && Objects.equals(val2, ANS2)) {
            return true;
        }
        return false;
    }

    private String sanitizeText(String input) {
        String sanitizedText = input.replaceAll("\\s+", "");
        sanitizedText = sanitizedText.replaceAll("[^0-9]", "");
        return sanitizedText;
    }

    public String getQNAKey(String lat, String lon) {
        if (lat == null || lon == null) {
            return "InvalidKey";
        }

        String key1 = lat.replace(".", "");
        String key2 = lon.replace(".", "");
        String part1 = key1.substring(0, Math.min(key1.length(), 4));
        String part2 = key2.substring(0, Math.min(key2.length(), 4));

        return part1 + part2;
    }
}
