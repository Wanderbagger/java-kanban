package tasks;

public class Task {

    private int id;
    private String title;
    private String description;
    private Status status;
    protected TypeTask typeTask; // тип задачи

    public Task(TypeTask typeTask, int id, String title, String description, Status status) {
        this.typeTask = typeTask;
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;


    }

    @Override
    public String toString() {

        return "{id=" + "'" + getId() + "'" +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}' + '\n';
    }

    public TypeTask getTypeTask() {
        return typeTask;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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