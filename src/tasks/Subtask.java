package tasks;

import java.time.Instant;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(
            TypeTask typeTask, int id, String description, String name, Status status, Instant startTime, long duration) {
        super(typeTask, id, description, name, status, startTime, duration);
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + getEpicId() +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() + '\'' +
                ", startTime='" + getStartTime().toEpochMilli() + '\'' +
                ", endTime='" + getEndTime().toEpochMilli() + '\'' +
                ", duration='" + getDuration() +
                '}';
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}