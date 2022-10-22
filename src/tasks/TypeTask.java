package tasks;

public enum TypeTask {
    TASK,
    EPIC,
    SUBTASK;

    public static TypeTask getType(String type) {
        for(TypeTask typeTask : TypeTask.values())
            if (typeTask.toString().equals(type)){
                return typeTask;
            }
        return null;
    }
}
