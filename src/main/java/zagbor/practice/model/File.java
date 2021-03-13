package zagbor.practice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "filestatus")
    @Enumerated(EnumType.STRING)
    private FileStatuses fileStatus;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "file", fetch = FetchType.EAGER)
    private List<Event> events;

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public FileStatuses getFileStatus() {
        return this.fileStatus;
    }

    @JsonBackReference
    public User getUser() {
        return this.user;
    }

    @JsonManagedReference
    public List<Event> getEvents() {
        return this.events;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFileStatus(FileStatuses fileStatus) {
        this.fileStatus = fileStatus;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof File)) return false;
        final File other = (File) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$fileStatus = this.getFileStatus();
        final Object other$fileStatus = other.getFileStatus();
        if (this$fileStatus == null ? other$fileStatus != null : !this$fileStatus.equals(other$fileStatus)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$events = this.getEvents();
        final Object other$events = other.getEvents();
        if (this$events == null ? other$events != null : !this$events.equals(other$events)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof File;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $fileStatus = this.getFileStatus();
        result = result * PRIME + ($fileStatus == null ? 43 : $fileStatus.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $events = this.getEvents();
        result = result * PRIME + ($events == null ? 43 : $events.hashCode());
        return result;
    }

    public String toString() {
        return "File(id=" + this.getId() + ", name=" + this.getName() + ", fileStatus=" + this.getFileStatus() + ", user=" + this.getUser() + ", events=" + this.getEvents() + ")";
    }
}
