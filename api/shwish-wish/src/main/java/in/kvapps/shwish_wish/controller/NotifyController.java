package in.kvapps.shwish_wish.controller;

import in.kvapps.shwish_wish.dto.NotifyRequestDto;
import in.kvapps.shwish_wish.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
@CrossOrigin("*")
public class NotifyController {
    @Autowired private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotifyRequestDto requestDto) {
        emailService.sendNotification(requestDto);
        return ResponseEntity.ok("Notification email sent successfully!");
    }
}
