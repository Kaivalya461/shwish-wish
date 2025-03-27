package in.kvapps.shwish_wish.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class MyActivityDto {
    private String activityId;
    private ZonedDateTime activityTime;
    private boolean validLocation;
    private String answers;
    private String lat;
    private String lon;
}
