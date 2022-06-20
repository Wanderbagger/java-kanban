package tasks;

import manager.Status;

public class Subtask extends Task {

    private int epicId;

    public Subtask(Integer taskId, String title, String description, Status status, int epicId) {
        super(taskId, title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {

        return "{taskID=" + "'" + getId() + "'" +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicID=" + epicId +
                '}';
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}