
public class Subtask extends Task {
    protected int epicID = 0;

    public Subtask(Integer taskID, String title, String description, int status, int epicID) {
        super(taskID, title, description, status);
        this.epicID = epicID;
    }

    @Override
    public String toString() {
        return "{taskID=" + "'" + taskID + "'" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + getStatus(status) +
                ", epicID=" + epicID +
                '}';
    }
}