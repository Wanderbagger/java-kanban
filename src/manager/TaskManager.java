package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager { // Интерфейс менеджера задач


    public void addTask(Task task);   // Добавление новой задачи

    public void addEpic(Epic epic);  // Добавление нового эпика

    public void addSubtask(Integer epicId, Subtask subtask); // Добавление новой подзадачи

    public void deleteAllTasks();   // очистка списка задач

    public void deleteAllEpics();   // очистка списка эпиков

    public void deleteAllSubtasks(int epicId);   // удаление всех подзадач одного эпика

    public void deleteTask(int taskId);

    public void deleteEpic(int taskId);

    public void deleteSubtask(int subtaskId);

    public void printTasks();

    public void printEpics();

    public void printAllSubtasks(int epicId);

    public void printTask(int taskId);

    public void printEpic(int epicId);

    public void printSubtask(int taskId);

    public void updateTask(int taskId, Task task);

    public void updateEpic(int taskId, Epic epic);

    public void updateSubtask(int subtaskID, Subtask subtask);

    public List<Task> getHistory();
}


