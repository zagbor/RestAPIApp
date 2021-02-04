package zagbor.practice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "filestatus")
    private FileStatuses fileStatus;
    @Column(name = "url")
    private String url;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
    private List<Event> events;

    public enum FileStatuses {
        ACTIVE, DELETED
    }

}
