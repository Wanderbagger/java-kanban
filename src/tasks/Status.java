package tasks;

public enum Status {

    NEW,
    IN_PROGRESS,
    DONE;

    public static Status getStatus(String status) {
        for(Status enumStatus : Status.values())
                if (enumStatus.toString().equals(status)){
                    return enumStatus;
                }
            return null;
        }
    }
