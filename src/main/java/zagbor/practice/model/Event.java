package zagbor.practice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EventType type;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = File.class)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File file;
    @Column(name = "date_event")
    @Temporal(TemporalType.DATE)
    private Date date;

    public long getId() {
        return this.id;
    }

    public EventType getType() {
        return this.type;
    }
    @JsonBackReference
    public File getFile() {
        return this.file;
    }

    public Date getDate() {
        return this.date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Event)) return false;
        final Event other = (Event) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$file = this.getFile();
        final Object other$file = other.getFile();
        if (this$file == null ? other$file != null : !this$file.equals(other$file)) return false;
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if (this$date == null ? other$date != null : !this$date.equals(other$date)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Event;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $file = this.getFile();
        result = result * PRIME + ($file == null ? 43 : $file.hashCode());
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        return result;
    }

    public String toString() {
        return "Event(id=" + this.getId() + ", type=" + this.getType() + ", file=" + this.getFile() + ", date=" + this.getDate() + ")";
    }
}
