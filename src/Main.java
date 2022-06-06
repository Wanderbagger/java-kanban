import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

public class Main {
    static TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {

        testingEpic();
        testingTask();
    }

    public static void testingTask() { // тестируем методы обработки задач
        Task task1 = new Task(0, "Задача № 1", "сдать задание", 0);
        taskManager.addTask(task1); // проверка метода добавления задачи
        Task task2 = new Task(0, "Задача № 2", "отдохнуть", 0);
        taskManager.addTask(task2); // проверка метода добавления задачи
        Task task3 = new Task(0, "Задача № 3", "выспаться", 0);
        taskManager.addTask(task3); // проверка метода добавления задачи
        taskManager.printTasks();
        taskManager.updateTask(9, task3);
        taskManager.printTasks();
        taskManager.deleteTask(10);
        taskManager.printTask(9);
        taskManager.deleteAllTasks();
        taskManager.printTasks();
    }
    public static void testingEpic() { // тестируем методы обработки эпиков и подзадач
        Epic epic1 = new Epic(0, "Эпик № 1", "Жить и радоваться", 0);
        taskManager.addEpic(epic1); // проверка метода добавления эпика
        Subtask subtask1 = new Subtask(0,"Подзадача № 1", "жить", 2, 2);
        taskManager.addSubtask(1,subtask1); // проверка метода добавления подзадачи
        Subtask subtask2 = new Subtask(0,"Подзадача № 2", "радоваться", 2, 1);
        taskManager.addSubtask(1,subtask2);
        Subtask subtask3 = new Subtask(0,"Подзадача № 3", "ну и все, собственно.", 2, 1);
        taskManager.addSubtask(1,subtask3);
        Epic epic2 = new Epic(0, "Эпик № 2", "Жить и не тужить", 0);
        taskManager.addEpic(epic2);
        Subtask subtask4 = new Subtask(0,"Подзадача № 1", "жить...", 2, 1);
        taskManager.addSubtask(5,subtask4);
        Subtask subtask5 = new Subtask(0,"Подзадача № 2", "не тужить", 2, 1);
        taskManager.addSubtask(5,subtask5);
        Subtask subtask6 = new Subtask(0,"Подзадача № 3", "ну и все, опять же.", 2, 1);
        taskManager.addSubtask(5,subtask6);

       taskManager.updateSubtask(2,subtask5);

        taskManager.printEpics();
        taskManager.updateEpic(1, epic2);
        taskManager.deleteSubtask(6);
        taskManager.printEpics();
        taskManager.printSubtask(8);
        taskManager.deleteAllEpics();
        taskManager.printEpics();




    }


    }

