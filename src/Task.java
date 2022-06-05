

public class Task {
    protected Integer taskID;
    protected String title;
    protected String description;
    int status; // status = 0 NEW, 1 - IN_PROGRESS, 2 - DONE

    public Task(Integer taskID, String title, String description, int status) {
        this.taskID = taskID;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {
        return "{taskID=" + "'" + taskID + "'" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + getStatus(status) +
                '}';
    }


    public String getStatus(int status) {
        switch (status) {
            case 0:
                return "NEW";
            case 1:
                return "IN_PROGRESS";
            case 2:
                return "DONE";
        }
        return "STATUS_ERROR";
    }


}