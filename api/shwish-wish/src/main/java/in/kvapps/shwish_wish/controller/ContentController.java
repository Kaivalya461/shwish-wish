package in.kvapps.shwish_wish.controller;

import in.kvapps.shwish_wish.dto.ContentDto;
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
    public ResponseEntity<ContentDto> getQnAContent(@RequestParam String lat,
                                                @RequestParam String lon) {
        var result = contentService.getQnAContent(lat, lon);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/message")
    public ResponseEntity<ContentDto> getMessageContent(@RequestParam String lat,
                                                        @RequestParam String lon,
                                                        @RequestParam String answers) {
        var result = contentService.getMessageContent(lat, lon, answers);
        return ResponseEntity.ok(result);
    }
}
