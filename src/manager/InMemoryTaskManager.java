package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    public Map<Integer, Task> tasks;  // Создали три мэпа, каждый - для своего типа задачи
    public Map<Integer, Epic> epics;
    public Map<Integer, Subtask> subtasks;
    private final InMemoryHistoryManager history;
    private final Comparator<Task> taskComparator = Comparator.comparing(Task::getStartTime);
    protected Set<Task> prioritizedTasks = new TreeSet<>(taskComparator);

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
            addNewPrioritizedTask(task);
            tasks.put(id, task);
        }
    }

    @Override
    public void addEpic(Epic epic) { // Добавление нового эпика
        if (epic != null) {
            id++;
            epic.setId(id);
            epic.setStatus(Status.NEW);
            addNewPrioritizedTask(epic);
            epics.put(id, epic);
        }
    }

    @Override
    public void addSubtask(Integer epicId, Subtask subtask) { // Добавление новой подзадачи
        if (subtask != null) {
            id++;
            subtask.setId(id);
            subtask.setEpicId(epicId);
            addNewPrioritizedTask(subtask);
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
    public void deleteAllSubtasks() {  // очистка списка подзадач
        subtasks.clear();
        System.out.println("Все подзадачи удалены");
    }

    @Override
    public void deleteAllSubtasksByEpicId(int epicId) {  // удаление всех подзадач одного эпика
        Iterator<Map.Entry<Integer, Subtask>> iterator = subtasks.entrySet().iterator();
        while (iterator.hasNext()) {
            Subtask subtask = subtasks.get(iterator.next().getKey());
            if (epicId == subtask.getEpicId()) {
                iterator.remove();
                history.removeRecord(subtask.getId());
            }
        }
        changeEpicStatus(epicId); // проверка статуса эпика с учетом удаленных подзадач
    }

    @Override
    public void deleteTask(int id) {  // удаление одной задачи
        Task task = tasks.get(id);
        if (task != null) {
            history.removeRecord(task.getId()); // удаление из истории
            tasks.remove(id);
        } else {
            System.out.println("Такой задачи не существует");
        }

    }

    @Override
    public void deleteEpic(int id) {  // удаление одного эпика
        Epic epic = epics.get(id);
        if (epic != null) {
            history.removeRecord(epic.getId()); // удаление из истории
            epics.remove(id);
            deleteAllSubtasksByEpicId(id);
        } else {
            System.out.println("Такого эпика не существует");
        }
    }

    @Override
    public void deleteSubtask(int id) {  // удаление одной подзадачи
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            history.removeRecord(subtask.getId()); // удаление из истории
            int epicId = subtask.getEpicId();
            subtasks.remove(id); // удалили подзадачу
            Epic epic = epics.get(epicId);
            if (epic != null) {
                changeEpicStatus(epic.getId()); // Проверка на завершенность эпика
            }
        } else {
            System.out.println("Такой подзадачи не существует");
        }
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
            if (subtask.getEpicId() == id) {
                System.out.println(subtask);
                noSubtasks = false;
            }
        }
        if (noSubtasks) {
            System.out.println("В данном эпике отсутствуют подзадачи");
        }
    }

    @Override
    public void printTask(int id) {  // Вывод одной задачи по Id
        history.addRecord(tasks.get(id));
        System.out.println("Задача:");
        System.out.println(getTaskById(id));
    }

    @Override
    public void printEpic(int id) {  // Вывод одного эпика по Id
        System.out.println("Эпик:");
        System.out.println(getEpicById(id));
        history.addRecord(epics.get(id));
        System.out.println("Подзадачи:"); // Вывод его подзадач
        printAllSubtasks(id);
    }

    @Override
    public void printSubtask(int id) {  // Вывод одной подзадачи по ID
        history.addRecord(subtasks.get(id));
        System.out.println("Подзадача:");
        System.out.println(getSubtaskById(id));
    }

    @Override
    public void updateTask(int id, Task task) { // обновление задачи
        if (task != null) {
            deleteTask(id);
            task.setId(id);
            addNewPrioritizedTask(task);
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Такой задачи не существует");
        }
    }

    @Override
    public void updateEpic(int id, Epic epic) { // обновление эпика
        if (epic != null) {
            int oldEpicId = epic.getId();
            deleteAllSubtasksByEpicId(id);
            deleteEpic(id);
            epic.setId(id);
            addNewPrioritizedTask(epic);
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
        } else {
            System.out.println("Такого эпика не существует");
        }
    }

    @Override
    public void updateSubtask(int id, Subtask subtask) { // обновление подзадачи
        if (subtask != null) {
            subtasks.remove(id);
            subtask.setId(id);
            addNewPrioritizedTask(subtask);
            subtasks.put(id, subtask);
            changeEpicStatus(subtask.getEpicId()); // Проверка на завершенность эпика
        } else {
            System.out.println("Такой подзадачи не существует");
        }
    }

    public void changeEpicStatus(int id) {  // Изменение статуса Эпика
        Epic epic = epics.get(id);
        if (epic != null) {
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

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        return subtask;
    }

    @Override
    public Map<Integer, Task> getAllTasks() {
        return tasks;
    }

    @Override
    public Map<Integer, Epic> getAllEpics() {
        return epics;
    }

    @Override
    public Map<Integer, Subtask> getAllSubtasks() {
        return subtasks;
    }

    @Override
    public void addNewPrioritizedTask(Task task) {
        validateTaskPriority(task);
        prioritizedTasks.add(task);
    }

    @Override
    public void validateTaskPriority(Task task) { // Проверка на валидацию
        List<Task> validatingTasks = getPrioritizedTasks();
        for (Task validatingTask : validatingTasks) {
            if ((task.getStartTime().isBefore(validatingTask.getStartTime())
                    && task.getEndTime().isAfter(validatingTask.getStartTime()))
                    ||
                    (task.getStartTime().isBefore(validatingTask.getEndTime())
                    && task.getEndTime().isAfter(validatingTask.getStartTime()))) {
                throw new ManagerValidateException("Задачи #" + task.getId() + " и #" + validatingTask.getId() + "пересекаются");
            }
        }
    }

    @Override
    public ArrayList<Task> getPrioritizedTasks() {  // Сортировка по приоритету
        ArrayList<Task> priority = new ArrayList<>();
        for(Task task : prioritizedTasks) {
            priority.add(task);
        }
        return priority;
    }

    @Override
    public void save() {

    }

    @Override
    public boolean load() {
        return false;
    }
}

