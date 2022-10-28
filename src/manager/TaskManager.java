package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface TaskManager { // Интерфейс менеджера задач

    void addTask(Task task);   // Добавление новой задачи

    void addEpic(Epic epic);  // Добавление нового эпика

    void addSubtask(Integer epicId, Subtask subtask); // Добавление новой подзадачи

   void deleteAllTasks();   // очистка списка задач

    void deleteAllEpics();   // очистка списка эпиков

 void deleteAllSubtasks();

 void deleteAllSubtasksByEpicId(int epicId);   // удаление всех подзадач одного эпика

    void deleteTask(int taskId);

    void deleteEpic(int taskId);

    void deleteSubtask(int subtaskId);

    void printTasks();

    void printEpics();

    void printAllSubtasks(int epicId);

    void printTask(int taskId);

    void printEpic(int epicId);

    void printSubtask(int taskId);

    void updateTask(int taskId, Task task);

    void updateEpic(int taskId, Epic epic);

    void updateSubtask(int subtaskID, Subtask subtask);

    List<Task> getHistory();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

 void addNewPrioritizedTask(Task task);

 void validateTaskPriority(Task task);

 List<Task> getPrioritizedTasks();

    void save();

    boolean load();
}


