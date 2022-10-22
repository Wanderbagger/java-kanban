package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    public static final String HEADER = "type,id,name,status,description,epic";
  // загрузка TasksManager'а из файла после запуска программы
    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager tasksManager = new FileBackedTasksManager(file);
        try {
            String data = Files.readString(file.toPath());
            String[] lines = data.split(System.lineSeparator());
            List<Integer> history = Collections.emptyList();
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                if (line.isBlank()) {
                    history = CSVFormatter.historyFromString(lines[i + 1]);
                    break;
                }
                Task task = CSVFormatter.fromString(line);
                if (task != null) {
                    switch (task.getTypeTask()) {
                        case TASK:
                            tasksManager.tasks.put(task.getId(), task);
                            break;
                        case EPIC:
                            tasksManager.epics.put(task.getId(), (Epic) task);
                            break;
                        case SUBTASK:
                            tasksManager.subtasks.put(task.getId(), (Subtask) task);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + task.getTypeTask());
                    }
                }
            }

            // восстановление истории
            InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
            for (Integer taskId : history) {
                historyManager.addRecord(tasksManager.tasks.get(taskId));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла");
        }
        return tasksManager;
    }

    public void save(String fileName) {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        try (Writer fileWriter = new FileWriter(fileName);
             BufferedWriter br = new BufferedWriter(fileWriter)) {
            br.write(HEADER + System.lineSeparator());
            for (Task task : tasks.values()) {
                br.write(CSVFormatter.toString(task) + System.lineSeparator());
            }
            for (Task task : epics.values()) {
                br.write(CSVFormatter.toString(task) + System.lineSeparator());
            }
            for (Task task : subtasks.values()) {
                br.write(CSVFormatter.toString(task) + subtasks.get(task.getId()).getEpicId()
                        + System.lineSeparator());
            }
            br.write(System.lineSeparator());
            br.write(CSVFormatter.historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи файла");
        }
    }

    public FileBackedTasksManager(File file) {
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save("save.csv");
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save("save.csv");
    }

    @Override
    public void addSubtask(Integer epicId, Subtask subtask) {
        super.addSubtask(epicId, subtask);
        save("save.csv");
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save("save.csv");
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save("save.csv");
    }

    @Override
    public void deleteAllSubtasks(int epicId) {
        super.deleteAllSubtasks(epicId);
        save("save.csv");
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save("save.csv");
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save("save.csv");
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save("save.csv");
    }

    @Override
    public void printTasks() {
        super.printTasks();
        save("save.csv");
    }

    @Override
    public void printEpics() {
        super.printEpics();
        save("save.csv");
    }

    @Override
    public void printAllSubtasks(int id) {
        super.printAllSubtasks(id);
        save("save.csv");
    }

    @Override
    public void printTask(int id) {
        super.printTask(id);
        save("save.csv");
    }

    @Override
    public void printEpic(int id) {
        super.printEpic(id);
        save("save.csv");
    }

    @Override
    public void printSubtask(int id) {
        super.printSubtask(id);
        save("save.csv");
    }

    @Override
    public void updateTask(int id, Task task) {
        super.updateTask(id, task);
        save("save.csv");
    }

    @Override
    public void updateEpic(int id, Epic epic) {
        super.updateEpic(id, epic);
        save("save.csv");
    }

    @Override
    public void updateSubtask(int id, Subtask subtask) {
        super.updateSubtask(id, subtask);
        save("save.csv");
    }

    @Override
    public void changeEpicStatus(int id) {
        super.changeEpicStatus(id);
        save("save.csv");
    }
}
