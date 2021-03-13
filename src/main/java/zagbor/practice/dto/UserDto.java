package zagbor.practice.dto;

import lombok.Builder;
import lombok.Data;
import zagbor.practice.model.UserRole;

import java.util.List;

@Builder
@Data
public class UserDto {
    private long id;
    private String name;
    private List<FileDto> files;
    private UserRole role;
}
