package tasks;

public enum Status {

    NEW,
    IN_PROGRESS,
    DONE;

    public static Status getStatus(String status) {
        if (status.equals("NEW")) {
            return Status.NEW;
        } else if (status.equals("IN_PROGRESS")) {
            return Status.IN_PROGRESS;
        } else if (status.equals("DONE")) {
            return Status.DONE;
        }
        return null;
    }
}