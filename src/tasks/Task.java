package tasks;
import manager.Status;

public class Task {

    private Integer Id;
    private String title;
    private String description;
    private Status status;

    public Task(Integer Id, String title, String description, Status status) {
        this.Id = Id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {

        return "{taskId=" + "'" + getId() + "'" +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }

    public Integer getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}