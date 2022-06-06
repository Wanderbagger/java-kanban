import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    protected int id = 0;
    public HashMap<Integer, Task> tasks = new HashMap<Integer, Task>(); // Создали три Хэшмэпа, каждый - для своего типа задачи
    protected HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();

    public void addTask(Task task) {  // Добавление новой задачи
        if (task != null) {
            id++;
            task.setTaskId(id);
            tasks.put(id, task);
        }
    }

    public void addEpic(Epic epic) { // Добавление нового эпика
        if (epic != null) {
            id++;
            epic.setTaskId(id);
            epic.setStatus(0);
            epics.put(id, epic);
        }
    }

    public void addSubtask (Integer epicId, Subtask subtask) { // Добавление новой подзадачи
        if (subtask != null) {
            id++;
            subtask.setTaskId(id);
            subtask.setEpicId(epicId);
            subtasks.put(id, subtask);
            changeEpicStatus(epicId); // Проверка на завершенность эпика
        }// NEW Исправлено
    }

    public void deleteAllTasks() {  // очистка списка задач
        tasks.clear();
        System.out.println("Все задачи удалены");
        }

    public void deleteAllEpics() {  // очистка списка эпиков
        epics.clear();
        subtasks.clear(); // если все эпики удалены, то и подзадачи с ними
        System.out.println("Все эпики удалены");
    }

    public void deleteAllSubtasks(int epicId) {  // удаление всех подзадач одного эпика
        ArrayList<Integer> findId = new ArrayList<>();
        for (int subtaskId : subtasks.keySet()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask.getEpicId(subtask) == epicId) { // NEW я поначалу сделал в одном цикле поиск и удаление, но он выдал ошибку,
                // NEW по которой я в интернете прочитал, что нельзя одновременно перебирать коллецию и удалять из нее, поэтому так сделал
                findId.add(subtaskId);// добавили в список все Id всех подзадач, которые надо удалить
            }
        }
        for (int finder : findId) { // NEW исправлено
        subtasks.remove(finder); // удалили
        }
        Epic epic = epics.get(epicId);
            if (epic != null) {
                epic.setStatus(0); // если все подзадачи удалены - эпик получает статус NEW
            }
        }

    public void deleteTask(int taskId) {  // удаление одной задачи
        tasks.remove(taskId);
     }

    public void deleteEpic(int taskId) {  // удаление одного эпика
        epics.remove(taskId);
    }

    public void deleteSubtask(int subtaskId) {  // удаление одной подзадачи
        Subtask subtask = subtasks.get(subtaskId);
        int epicId = subtask.getEpicId(subtask);
        subtasks.remove(subtaskId); // удалили подзадачу
        Epic epic = epics.get(epicId);
        changeEpicStatus(epic.getTaskId(epic)); // Проверка на завершенность эпика
    }

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

    public void printEpics() {  // Вывод списка всех эпиков
        if (epics.size() > 0) {
            for (int epic : epics.keySet()) {
                printEpic(epic); // вызвали метод печати эпика и его подзадач
            }
        } else {
            System.out.println("Эпики отсутствуют");
        }
    }

    public void printAllSubtasks(int epicId) {  //  Вывод всех подзадач одного эпика
        boolean noSubtasks = true;
        for (int subtaskId : subtasks.keySet()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (Subtask.getEpicId(subtask) == epicId){
                System.out.println(subtask);
                noSubtasks = false;
            }
        }
        if (noSubtasks){
            System.out.println("В данном эпике отсутствуют подзадачи");
        }
    }

    public void printTask(int taskId) {  // Вывод одной задачи по Id
        System.out.println(tasks.get(taskId));
    }

    public void printEpic(int epicId) {  // Вывод одного эпика по Id
        System.out.println("Эпик:");
        System.out.println(epics.get(epicId));
        System.out.println("Подзадачи:"); // Вывод его подзадач
        printAllSubtasks(epicId);
    }

    public void printSubtask(int taskId) {  // Вывод одной подзадачи по ID
        System.out.println(subtasks.get(taskId));
    }
    public void updateTask(int taskId, Task task){ // обновление задачи
        if (task != null) {
            deleteTask(taskId);
            task.setTaskId(taskId);

            tasks.put(Task.getTaskId(task), task);
        }
    }

    public void updateEpic(int taskId, Epic epic) { // обновление подзадачи
        if (epic != null) {
            int oldEpicId = epic.getTaskId(epic);
            deleteAllSubtasks(taskId);
            deleteEpic(taskId);

            epic.setTaskId(taskId);


            epics.put(taskId, epic);

            for (int subtaskId : subtasks.keySet()) { // NEW ранее мы удалили подзадачи эпика, который изменяем,
                // а теперь мы ищем подзадачи эпика, который вставляем на его место, чтобы перетащить их следом за ним в новый Ай Ди
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask.getEpicId(subtask) == oldEpicId) {
                    subtask.setEpicId(taskId); // перетащили все подзадачи из старого эпика в обновленный

                }
            }
            if (epic.getStatus(epic) == 2) {
                for (int subtaskID : subtasks.keySet()) {
                    Subtask subtask = subtasks.get(subtaskID);
                    if (subtask.getEpicId(subtask) == epic.getTaskID(epic)) {
                        subtask.setStatus(2); // если Эпик завершен, то и все подзадачи завершены
                    }
                }
            }
            deleteEpic(oldEpicId);
            System.out.println("ПРОВЕРКА ЭПИКОВ"+epics);


        }
    }

    public void updateSubtask(int subtaskID, Subtask subtask){
        if (subtask != null) {
            subtasks.remove(subtaskID);
            subtask.setTaskId(subtaskID);
            subtasks.put(subtaskID, subtask);
        }
        changeEpicStatus(Subtask.getEpicId(subtask)); // Проверка на завершенность эпика
    }

    public void changeEpicStatus(int epicID){
        Epic epic = epics.get(epicID);
        if(epic!=null) {
            int statusInProgressCounter = 0;
            int statusDoneCounter = 0;
            for (int subtaskID : subtasks.keySet()) {
                Subtask subtask = subtasks.get(subtaskID);
                if (subtask.getEpicId(subtask) == epicID) {
                    if (subtask.getStatus(subtask) == 1) {
                        statusInProgressCounter++;
                    } else if (subtask.getStatus(subtask) == 2) {
                        statusDoneCounter++;
                    }
                }
            }
            if (statusInProgressCounter == 0 && statusDoneCounter == 0) {
                epic.setStatus(0);
            } else if (statusInProgressCounter == 0 && statusDoneCounter > 0) {
                epic.setStatus(2);
            } else {
                epic.setStatus(1);
            }
        }

    }




}


