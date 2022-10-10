package tasks;

public class Epic extends Task {

    public Epic(TypeTask typeTask, Integer id, String title, String description, Status status) {
        super(typeTask, id, title, description, status);
    }

    @Override
    public String toString() {
        return "{id=" + "'" + getId() + "'" +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}' + '\n';
    }
}