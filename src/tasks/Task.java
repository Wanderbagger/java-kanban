package tasks;

import java.time.Instant;

import java.util.Objects;

public class Task {
    protected TypeTask typeTask; // тип задачи
    private String description;
    private int id;
    private String name;
    private Status status;
    private Instant startTime;
    private long duration;


    public Task(
            TypeTask typeTask,
            int id,
            String description,
            String name,
            Status status,
            Instant startTime,
            long duration
    ) {
        this.typeTask = typeTask;
        this.id = id;
        this.description = description;
        this.name = name;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public TypeTask getTypeTask() {
        return typeTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Instant getEndTime() {
        long SECONDS_IN_MINUTE = 60L;
        return startTime.plusSeconds(duration * SECONDS_IN_MINUTE);
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status + '\'' +
                ", startTime='" + startTime.toEpochMilli() + '\'' +
                ", endTime='" + getEndTime().toEpochMilli() + '\'' +
                ", duration='" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(description, task.description) && Objects.equals(name, task.name) &&
                status == task.status && Objects.equals(startTime, task.startTime) &&
                Objects.equals(duration, task.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name, status, startTime, duration);
    }


}