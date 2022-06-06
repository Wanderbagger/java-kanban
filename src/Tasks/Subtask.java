package Tasks;

public class Subtask extends Task {

     protected int epicId; // NEW Исправлено

    public Subtask(Integer taskId, String title, String description, int status, int epicId) {
        super(taskId, title, description, status);
        this.epicId = epicId;
    }

    public static int getEpicId(Subtask subtask) {
        return subtask.epicId;
    }

    public static int getStatus(Subtask subtask) {
        return subtask.status;
    }

    public void setTaskId(Integer newTaskId) {
        this.taskId = newTaskId;
    }

    public void setEpicId(int newEpicId) {
        this.epicId = newEpicId;
    }

    @Override
    public String toString() {
        String [] printStatus = {"NEW", "IN_PROGRESS", "DONE"};
        return "{taskID=" + "'" + taskId + "'" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + printStatus[status] +
                ", epicID=" + epicId +
                '}';
    }
}