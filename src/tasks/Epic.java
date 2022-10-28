package tasks;

import java.time.Instant;
import java.util.*;

public class Epic extends Task {

    private Instant endTime;

    public Epic(TypeTask typeTask, int id, String description, String name, Status status, Instant startTime, long duration) {

        super(typeTask, id, description, name, status, startTime, duration);
        this.endTime = super.getEndTime();
    }

    @Override
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", description='" + getDescription() + '\'' +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() + '\'' +
                ", startTime='" + getStartTime().toEpochMilli() + '\'' +
                ", endTime='" + getEndTime().toEpochMilli() + '\'' +
                ", duration='" + getDuration() +
                '}';
    }
}