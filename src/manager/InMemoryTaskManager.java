package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>(); // Создали три Хэшмэпа, каждый - для своего типа задачи
    private HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();
    private final InMemoryHistoryManager history;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.history = new InMemoryHistoryManager();
    }

    @Override
    public void addTask(Task task) {  // Добавление новой задачи
        if (task != null) {
            id++;
            task.setId(id);
            tasks.put(id, task);
        }
    }

    @Override
    public void addEpic(Epic epic) { // Добавление нового эпика
        if (epic != null) {
            id++;
            epic.setId(id);
            epic.setStatus(Status.NEW);
            epics.put(id, epic);
        }
    }

    @Override
    public void addSubtask (Integer epicId, Subtask subtask) { // Добавление новой подзадачи
        if (subtask != null) {
            id++;
            subtask.setId(id);
            subtask.setEpicId(epicId);
            subtasks.put(id, subtask);
            changeEpicStatus(epicId); // Проверка на завершенность эпика
        }
    }

    @Override
    public void deleteAllTasks() {  // очистка списка задач
        tasks.clear();
        System.out.println("Все задачи удалены");
        }

    @Override
    public void deleteAllEpics() {  // очистка списка эпиков
        epics.clear();
        subtasks.clear(); // если все эпики удалены, то и подзадачи с ними
        System.out.println("Все эпики удалены");
    }

    @Override
    public void deleteAllSubtasks(int epicId) {  // удаление всех подзадач одного эпика
        ArrayList<Integer> findId = new ArrayList<>(); // я перебрал несколько вариантов и итератор тоже,
        for (int subtaskId : subtasks.keySet()) { //  так и не понял, как обойти java.util.ConcurrentModificationException
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask.getEpicId() == epicId) {
                findId.add(subtaskId); // добавили в список все Id всех подзадач, которые надо удалить
            }
        }
        for (int finder : findId) {
            deleteSubtask(finder); // удалили
        }
    }

    @Override
    public void deleteTask(int id) {  // удаление одной задачи
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {  // удаление одного эпика
        epics.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {  // удаление одной подзадачи
        Subtask subtask = subtasks.get(id);
        int epicId = subtask.getEpicId();
        subtasks.remove(id); // удалили подзадачу
        Epic epic = epics.get(epicId);
        changeEpicStatus(epic.getId()); // Проверка на завершенность эпика
    }

    @Override
    public void printTasks() {  // Вывод списка всех задач
        if (tasks.size() > 0) {
            System.out.println("Задачи:");
            for (int task : tasks.keySet()) {
                System.out.println(tasks.get(task));
            }
        } else {
            System.out.println("Задачи отсутствуют");
        }
    }

    @Override
    public void printEpics() {  // Вывод списка всех эпиков
        if (epics.size() > 0) {
            for (int epic : epics.keySet()) {
                printEpic(epic); // вызвали метод печати эпика и его подзадач
            }
        } else {
            System.out.println("Эпики отсутствуют");
        }
    }

    @Override
    public void printAllSubtasks(int id) {  //  Вывод всех подзадач одного эпика
        boolean noSubtasks = true;
        for (int subtaskId : subtasks.keySet()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask.getEpicId() == id){
                System.out.println(subtask);
                noSubtasks = false;
            }
        }
        if (noSubtasks){
            System.out.println("В данном эпике отсутствуют подзадачи");
        }
    }

    @Override
    public void printTask(int id) {  // Вывод одной задачи по Id
        history.addRecord(tasks.get(id));
        System.out.println(tasks.get(id));
    }

    @Override
    public void printEpic(int id) {  // Вывод одного эпика по Id
        System.out.println("Эпик:");
        System.out.println(epics.get(id));
        history.addRecord(epics.get(id));
        System.out.println("Подзадачи:"); // Вывод его подзадач
        printAllSubtasks(id);
    }

    @Override
    public void printSubtask(int id) {  // Вывод одной подзадачи по ID
        history.addRecord(subtasks.get(id));
        System.out.println(subtasks.get(id));
    }

    @Override
    public void updateTask(int id, Task task){ // обновление задачи
        if (task != null) {
            deleteTask(id);
            task.setId(id);
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(int id, Epic epic) { // обновление эпика
        if (epic != null) {
            int oldEpicId = epic.getId();
            deleteAllSubtasks(id);
            deleteEpic(id);
            epic.setId(id);
            epics.put(id, epic);
            if (epic.getStatus() == Status.DONE) {
                for (int subtaskID : subtasks.keySet()) {
                    Subtask subtask = subtasks.get(subtaskID);
                    if (subtask.getEpicId() == epic.getId()) {
                        subtask.setStatus(Status.DONE); // если Эпик завершен, то и все подзадачи завершены
                    }
                }
            }
            deleteEpic(oldEpicId);
       }
    }

    @Override
    public void updateSubtask(int id, Subtask subtask){ // обновление подзадачи
        if (subtask != null) {
            subtasks.remove(id);
            subtask.setId(id);
            subtasks.put(id, subtask);
            changeEpicStatus(subtask.getEpicId()); // Проверка на завершенность эпика
        } else {
            System.out.println("Такой подзадачи не существует");
        }
    }

    public void changeEpicStatus(int id){  // Изменение статуса Эпика
        Epic epic = epics.get(id);
        if(epic!=null) {
            int statusInProgressCounter = 0;
            int statusDoneCounter = 0;
            for (int subtaskID : subtasks.keySet()) {
                Subtask subtask = subtasks.get(subtaskID);
                if (subtask.getEpicId() == id) {
                    if (subtask.getStatus() == Status.IN_PROGRESS) {
                        statusInProgressCounter++;
                    } else if (subtask.getStatus() == Status.DONE) {
                        statusDoneCounter++;
                    }
                }
            }
            if (statusInProgressCounter == 0 && statusDoneCounter == 0) {
                epic.setStatus(Status.NEW);
            } else if (statusInProgressCounter == 0 && statusDoneCounter > 0) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    @Override
    public List<Task> getHistory() { // Получить историю просмотра
        return history.getHistory();
    }
}

