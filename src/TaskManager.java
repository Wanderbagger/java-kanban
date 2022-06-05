import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    protected static int taskID = 0;
    protected HashMap<Integer, Task> tasks = new HashMap<Integer, Task>(); // Создали три Хэшмэпа, каждый - для своего типа задачи
    protected HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();

    public void addTask(Task task) {  // Добавление новой задачи
        if (task != null) {
            taskID++;
            task.taskID = taskID;
            tasks.put(task.taskID, task);
        }
    }

    public void addEpic(Epic epic) { // Добавление нового эпика
        if (epic != null) {
            taskID++;
            epic.taskID = taskID;
            epic.status = 0;
            epics.put(epic.taskID, epic);
        }
    }

    public void addSubtask (Integer epicID, Subtask subtask) { // Добавление новой подзадачи
        if (subtask != null) {
            taskID++;
            subtask.taskID = taskID;
            Epic epic = epics.get(epicID);
            if (epic != null){
                subtasks.put(subtask.taskID, subtask);
                epic.status = 0;
            }
        }
        changeEpicStatus(subtask.epicID); // Проверка на завершенность эпика
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

    public void deleteAllSubtasks(int epicID) {  // удаление всех подзадач одного эпика
        ArrayList<Integer> findID = new ArrayList<>();
        for (int subtaskID : subtasks.keySet()) {
            Subtask subtask = subtasks.get(subtaskID);
            if (subtask.epicID == epicID) {
                findID.add(subtaskID);// добавили в список все ID всех подзадач, которые надо удалить
            }
        }
        for (int find : findID) {
        subtasks.remove(find); // удалили
        }

        Epic epic = epics.get(epicID);
            if (epic != null) {
                epic.status = 0; // если все подзадачи удалены - эпик получает статус NEW
            }
        }

    public void deleteTask(int taskID) {  // удаление одной задачи
        tasks.remove(taskID);
     }

    public void deleteEpic(int taskID) {  // удаление одного эпика
        epics.remove(taskID);
    }

    public void deleteSubtask(int subtaskID) {  // удаление одной подзадачи
        Subtask subtask = subtasks.get(subtaskID);
        int epicID = subtask.epicID;
        subtasks.remove(subtaskID); // удалили подзадачу
        Epic epic = epics.get(epicID);
        changeEpicStatus(epic.taskID); // Проверка на завершенность эпика
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

    public void printAllSubtasks(int epicID) {  //  Вывод всех подзадач одного эпика
        boolean noSubtasks = true;
        for (int subtaskID : subtasks.keySet()) {
            Subtask subtask = subtasks.get(subtaskID);
            if (subtask.epicID == epicID){
                System.out.println(subtask);
                noSubtasks = false;
            }
        }
        if (noSubtasks){
            System.out.println("В данном эпике отсутствуют подзадачи");
        }
    }

    public void printTask(int taskID) {  // Вывод одной задачи по ID
        System.out.println(tasks.get(taskID));
    }

    public void printEpic(int epicID) {  // Вывод одного эпика по ID
        System.out.println("Эпик:");
        System.out.println(epics.get(epicID));
        System.out.println("Подзадачи:"); // Вывод его подзадач
        printAllSubtasks(epicID);
    }

    public void printSubtask(int taskID) {  // Вывод одной подзадачи по ID
        System.out.println(subtasks.get(taskID));
    }
    public void updateTask(int taskID, Task task){ // обновление задачи
        if (task != null) {
            deleteTask(taskID);
            task.taskID = taskID;
            tasks.put(task.taskID, task);
        }
    }

    public void updateEpic(int taskID, Epic epic) { // обновление подзадачи
        if (epic != null) {

            int oldEpicID = epic.taskID;
            deleteEpic(taskID);
            deleteAllSubtasks(taskID);
            epic.taskID = taskID;
            epics.put(epic.taskID, epic);
            System.out.println(oldEpicID);
            for (int subtaskID : subtasks.keySet()) {

                Subtask subtask = subtasks.get(subtaskID);
                if (subtask.epicID == oldEpicID) {
                    subtask.epicID = epic.taskID; // перетащили все подзадачи из старого эпика в обновленный
                }
            }
            if (epic.status == 2) {
                for (int subtaskID : subtasks.keySet()) {
                    Subtask subtask = subtasks.get(subtaskID);
                    if (subtask.epicID == epic.taskID) {
                        subtask.status = 2; // если Эпик завершен, то и все подзадачи завершены
                    }
                }
            }
        }
    }
    public void updateSubtask(int subtaskID, Subtask subtask){
        if (subtask != null) {
            subtasks.remove(subtaskID);
            subtask.taskID = subtaskID;
            subtasks.put(subtaskID, subtask);
        }
        changeEpicStatus(subtask.epicID); // Проверка на завершенность эпика
    }

    public void changeEpicStatus(int epicID){
        Epic epic = epics.get(epicID);
        int statusInProgressCounter = 0;
        int statusDoneCounter = 0;
        for (int subtaskID : subtasks.keySet()) {
            Subtask subtask = subtasks.get(subtaskID);
            if(subtask.epicID == epicID){
                if (subtask.status == 1){
                    statusInProgressCounter++;
                } else if (subtask.status == 2){
                    statusDoneCounter++;
                }
            }
        }
        if (statusInProgressCounter == 0 && statusDoneCounter == 0){
            epic.status = 0;
        } else if (statusInProgressCounter == 0 && statusDoneCounter > 0) {
            epic.status = 2;
        } else {
            epic.status = 1;
        }

    }

    public void testingTask() { // тестируем методы обработки задач
        Task task1 = new Task(0, "Задача № 1", "сдать задание", 0);
        addTask(task1); // проверка метода добавления задачи
        Task task2 = new Task(0, "Задача № 2", "погулять с сыном", 1);
        addTask(task2);
        Task task3 = new Task(0, "Задача № 3", "погулять с женой", 2);
        addTask(task3);
        printTask(9); // проверка метода вывода задачи на экран
        deleteTask(9); // проверка метода удаления задачи
        printTasks();

        updateTask(10, task3); // проверка метода обновления задачи
        printTask(10);
        deleteAllTasks(); // проверка метода удаления всех задач
        printTasks();
        }
    public void testingEpic() { // тестируем методы обработки эпиков и подзадач
        Epic epic1 = new Epic(0, "Эпик № 1", "Жить и радоваться", 0);
        addEpic(epic1); // проверка метода добавления эпика
        Subtask subtask1 = new Subtask(0,"Подзадача № 1", "жить", 1, 1);
        addSubtask(1,subtask1); // проверка метода добавления подзадачи
        Subtask subtask2 = new Subtask(0,"Подзадача № 2", "радоваться", 1, 1);
        addSubtask(1,subtask2);
        Subtask subtask3 = new Subtask(0,"Подзадача № 3", "ну и все, собственно.", 1, 1);
        addSubtask(1,subtask3);

        Epic epic2 = new Epic(0, "Эпик № 2", "Жить и не тужить", 0);
        addEpic(epic2);
        Subtask subtask4 = new Subtask(0,"Подзадача № 1", "жить...", 2, 5);
        addSubtask(5,subtask4);
        Subtask subtask5 = new Subtask(0,"Подзадача № 2", "не тужить", 2, 5);
        addSubtask(5,subtask5);
        Subtask subtask6 = new Subtask(0,"Подзадача № 3", "ну и все, опять же.", 2, 5);
        addSubtask(5,subtask6);

        printEpic(1); // проверка метода вывода эпика и его подзадач
        printEpic(5);
        updateEpic(1, epic2); // проверка метода обновления эпика и его подзадач
        printEpic(1);
        deleteSubtask(7); // проверка метода удаления подзадачи
        updateSubtask(7, subtask1); // проверка метода обновления подзадачи
        printEpic(1);
        printSubtask(8); // проверка метода вывода подзадачи на экран
        deleteAllEpics(); // проверка метода удаления всех эпиков
        printEpics();
    }


}


