package tasks;

public enum TypeTask {
    TASK,
    EPIC,
    SUBTASK;

    public static TypeTask getType(String type) {
        if ("TASK".equals(type)) {
            return TypeTask.TASK;
        } else if ("EPIC".equals(type)) {
            return TypeTask.EPIC;
        } else if ("SUBTASK".equals(type)) {
            return TypeTask.SUBTASK;
        }
        return null;
    }
}
