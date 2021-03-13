package zagbor.practice.dto;

import lombok.Builder;
import lombok.Data;
import zagbor.practice.model.EventType;
import java.util.Date;

@Builder
@Data
public class EventDto {
    private long id;
    private EventType type;
    private FileDto file;
    private Date date;
}
