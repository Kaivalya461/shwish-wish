package in.kvapps.shwish_wish.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

import static in.kvapps.shwish_wish.constant.ContentConstants.*;
import static in.kvapps.shwish_wish.constant.ContentConstants.ANS4;

@UtilityClass
@Log4j2
public class AnswerValidator {

    public boolean isValidAnswers(String answers, String lat, String lon) {
        String[] answerArray = answers.split("_");
        String key = AnswerValidator.getQNAKey(lat, lon);
        if (0 == answerArray.length || null == key) {
            log.warn("isValidAnswers -> Empty Answers Received OR Incorrect Key");
            return false;
        }
        String val1 = EncryptDecryptUtil.encrypt(sanitizeText(answerArray[0]), key+key);
        String val2 = EncryptDecryptUtil.encrypt(sanitizeText(answerArray[1]), key+key);
        if ((Objects.equals(val1, ANS1) && Objects.equals(val2, ANS2)) ||
                (Objects.equals(val1, ANS3) && Objects.equals(val2, ANS4))) {
            return true;
        }
        return false;
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

    private String sanitizeText(String input) {
        String sanitizedText = input.replaceAll("\\s+", "");
        sanitizedText = sanitizedText.replaceAll("[^0-9]", "");
        return sanitizedText;
    }
}
