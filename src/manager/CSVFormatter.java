package manager;

import tasks.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CSVFormatter {
    public static final  String SEPARATOR = ",";

    public CSVFormatter() {
    }

    // чтение задачи из строки
    public static Task fromString(String value) {
        String[] data = value.split(SEPARATOR);
        int id = Integer.parseInt(data[1]);
        String description = data[2];
        String name = data[3];
        Status status = Status.valueOf(data[4].toUpperCase());
        Instant startTime = Instant.parse(data[5]);
        long duration = Long.parseLong(data[6]);

        switch (data[1]) {
            case "TASK":
                Task task = new Task(TypeTask.getType(data[1]), id, description, name, status, startTime, duration);
                task.setId(id);
                return task;
            case "EPIC":
                return new Epic(TypeTask.getType(data[1]), id, description, name, status, startTime, duration);
            case "SUBTASK":
                return new Subtask(TypeTask.getType(data[1]), id, description, name, status, startTime, duration);
            default:
                return null;
        }
    }

    // чтение истории просмотров из строки
    public static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();
        String[] data = value.split(SEPARATOR);
        for (String s : data) {
            history.add(Integer.parseInt(s));
        }
        return history;
    }


    // запись задачи типа Task в строку
    public static String toString(Task task) {
        return  task.getTypeTask() + SEPARATOR + task.getId() + SEPARATOR
                + task.getName() + SEPARATOR + task.getStatus() + SEPARATOR
                + task.getDescription() + SEPARATOR;
    }

    // запись истории просмотров в строку
    static String historyToString(HistoryManager manager) {
        StringBuilder sb = new StringBuilder();
        for (Task task : manager.getHistory()) {
            sb.append(task.getId()).append(SEPARATOR);
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }
}