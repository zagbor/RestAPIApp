package zagbor.practice.dto;

import lombok.Builder;
import lombok.Data;
import zagbor.practice.model.FileStatuses;
import java.util.List;
@Builder
@Data
public class FileDto {

    private long id;
    private String name;
    private FileStatuses fileStatus;
    private UserDto user;
    private List<EventDto> events;
}
