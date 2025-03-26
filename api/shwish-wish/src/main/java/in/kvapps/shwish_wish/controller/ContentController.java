package in.kvapps.shwish_wish.controller;

import in.kvapps.shwish_wish.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content")
@CrossOrigin("*")
public class ContentController {
    @Autowired private ContentService contentService;

    @GetMapping("/qna")
    public ResponseEntity<String> getQnAContent(@RequestParam String lat,
                                                @RequestParam String lon) {
        String result = contentService.getQnAContent(lat, lon);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/message")
    public ResponseEntity<String> getMessageContent(@RequestParam String lat,
                                                    @RequestParam String lon,
                                                    @RequestParam String ans1,
                                                    @RequestParam String ans2) {
        String result = contentService.getMessageContent(lat, lon, ans1, ans2);
        return ResponseEntity.ok(result);
    }
}
