package tasks;

import manager.Status;

public class Subtask extends Task {

    private int epicId;

    public Subtask(Integer id, String title, String description, Status status, int epicId) {
        super(id, title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {

        return "{id=" + "'" + getId() + "'" +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicID=" + epicId +
                '}' + '\n';
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}