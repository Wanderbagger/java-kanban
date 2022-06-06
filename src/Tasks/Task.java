package Tasks;

public class Task {

    protected Integer taskId; // NEW Исправлено
    protected String title;
    protected String description;
    protected int status; // status = 0

    public Task(Integer taskId, String title, String description, int status) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public void setTaskId(Integer newTaskId) {
        this.taskId = newTaskId;
    }

    public static Integer getTaskId(Task task) {
        return task.taskId;
    }


    public void setStatus(int newStatus) {
        this.status = newStatus;
    }

    @Override
    public String toString() {
        String [] printStatus = {"NEW", "IN_PROGRESS", "DONE"};
        return "{taskId=" + "'" + taskId + "'" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + printStatus[status] +
                '}';
    }





}