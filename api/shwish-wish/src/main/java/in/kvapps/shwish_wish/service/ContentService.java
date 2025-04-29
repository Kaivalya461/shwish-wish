package in.kvapps.shwish_wish.service;

import in.kvapps.shwish_wish.dto.ContentDto;
import in.kvapps.shwish_wish.util.AnswerValidator;
import in.kvapps.shwish_wish.util.EncryptDecryptUtil;
import in.kvapps.shwish_wish.util.LocationValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import static in.kvapps.shwish_wish.constant.ContentConstants.*;

@Service
@Log4j2
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
        boolean allValidAnswers = AnswerValidator.isValidAnswers(answers, lat, lon);
        if(isValid && allValidAnswers) {
            contentDto.setMsg(MSG1 + "_" + MSG2);
        }

        return contentDto;
    }

    public ContentDto getSpecialEventMessageContent(String lat, String lon, String answers) {
        ContentDto contentDto = new ContentDto();
        boolean isValid = locationValidator.isLocationValid(Double.parseDouble(lat), Double.parseDouble(lon));
        boolean allValidAnswers = AnswerValidator.isValidAnswers(answers, lat, lon);
        if(isValid && allValidAnswers) {
            contentDto.setMsg(MSG3);
        }

        return contentDto;
    }

//    private void sendMailAlert(String lat, String lon, String answers, boolean isValidLocation, String body) {
//        MyActivityDto activityDto = MyActivityDto.builder()
//                .activityId(UUID.randomUUID().toString())
//                .validLocation(isValidLocation)
//                .activityTime(ZonedDateTime.now(ZoneId.of("UTC")))
//                .answers(answers)
//                .lat(lat)
//                .lon(lon)
//                .build();
//
//        Thread mailAlertThread = new Thread(() -> emailService.sendMailToMe(activityDto, body));
//        mailAlertThread.start();
//    }

    public ContentDto getImageContent(String lat, String lon, String answers) {
        ContentDto contentDto = new ContentDto();
        if(!locationValidator.isLocationValid(Double.parseDouble(lat), Double.parseDouble(lon))
            || !AnswerValidator.isValidAnswers(answers, lat, lon)) {
            return contentDto;
        }

        contentDto.setImg(IMG);
        return contentDto;
    }

    public String encodeImageToBase64(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        try (FileInputStream fileInputStream = new FileInputStream(imageFile)) {
            byte[] imageBytes = new byte[(int) imageFile.length()];
            fileInputStream.read(imageBytes);

            // Encode the byte array to Base64
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
