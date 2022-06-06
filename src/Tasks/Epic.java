package Tasks;

public class Epic extends Task {


    public Epic(Integer taskID, String title, String description, int status) {
        super(taskID, title, description, status);
    }


    public Integer getTaskID(Epic epic) {
        return epic.taskId;
    }
    public void setTaskID(Integer newTaskID) {
        this.taskId = newTaskID;
    }
    public  int getStatus(Epic epic) {
        return epic.status;
    }

    @Override
    public String toString() {
        String [] printStatus = {"NEW", "IN_PROGRESS", "DONE"}; // NEW Исправлено
        return "{taskID=" + "'" + taskId + "'" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + printStatus[status] +
                '}';
    }

}