package in.kvapps.shwish_wish.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.kvapps.shwish_wish.dto.MyActivityDto;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Log4j2
public class EmailService {
    @Autowired private JavaMailSender javaMailSender;
    @Value("${kv.mail.recipient}") private String recipient;
    @Autowired private ObjectMapper objectMapper;

    public void sendEmailWithFile(String to, String subject, String body, String fileName, byte[] attachmentBytes) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            if(attachmentBytes != null) {
                ByteArrayDataSource dataSource = new ByteArrayDataSource(attachmentBytes, "application/json");
                helper.addAttachment(fileName, dataSource);
            }

            javaMailSender.send(message);
            log.info("Mail Sent");
        } catch (Exception ex) {
            log.error("Error while sending mail alert, errorMsg: {}", ex.getMessage(), ex);
        }
    }

    public void sendMailToMe(MyActivityDto myActivityDto, String body) {
        byte[] jsonBytes = null;
        try {
            //prepare file
            // Serialize the object to a file
            jsonBytes = objectMapper.writeValueAsBytes(myActivityDto);
        } catch (Exception exception) {
            log.error("Exception while object-mapper conversion, errorMsg:{}", exception.getMessage(), exception);
        }

        sendEmailWithFile(
                recipient,
                getSubjectForMsg1(myActivityDto.getActivityTime()),
                body,
                getFileName(myActivityDto.getActivityTime()),
                jsonBytes
        );
    }

    private String getSubjectForMsg1(ZonedDateTime activityTime) {
        //Test Mail - ShwishWish App Testing - 2025-03-24_01:13:37AM_IST
        String formatedTime = formatZonedDateTime(activityTime);
        return "ShwishWish App Alerts - " + formatedTime;
    }

    public static String formatZonedDateTime(ZonedDateTime zonedDateTime) {
        // Convert To IST
        ZonedDateTime istDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));

        // Define the formatter for the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh:mm:ssa");

        // Format the ZonedDateTime object
        String formattedDateTime = istDateTime.format(formatter);

        // Extract the timezone abbreviation (e.g., "IST")
        String timeZoneAbbreviation = istDateTime.getZone().getId();

        // Combine the formatted date/time with the timezone
        return formattedDateTime + "_" + timeZoneAbbreviation;
    }

    private String getFileName(ZonedDateTime activityTime) {
        return "Activity_" + formatZonedDateTime(activityTime) + ".json";
    }
}
