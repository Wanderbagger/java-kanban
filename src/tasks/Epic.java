package tasks;

import manager.Status;

public class Epic extends Task {

    public Epic(Integer id, String title, String description, Status status) {
        super(id, title, description, status);
    }

    @Override
    public String toString() {
        return "{id=" + "'" + getId() + "'" +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}