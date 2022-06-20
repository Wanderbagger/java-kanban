package tasks;

import manager.Status;

public class Epic extends Task {

    public Epic(Integer taskId, String title, String description, Status status) {
        super(taskId, title, description, status);
    }

    @Override
    public String toString() {
        return "{taskId=" + "'" + getId() + "'" +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}