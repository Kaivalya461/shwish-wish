package in.kvapps.shwish_wish.dto;

import lombok.Data;

@Data
public class NotifyRequestDto {
    private String notificationSource; // Used to find which transaction triggered the Notification.
    private String lat;
    private String lon;
    private String answers;
}
