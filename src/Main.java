import manager.InMemoryTaskManager;
import manager.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();
    public static void main(String[] args) {
        testingTask();
    }

    public static void testingTask() { // тестируем методы обработки задач
        Task task1 = new Task(0, "Задача № 1", "Проснуться", Status.NEW);
        Task task2 = new Task(0, "Задача № 2", "Радоваться жизни", Status.NEW);

        Epic epic1 = new Epic(0, "Большая задача № 1", "Отправиться на работу", Status.NEW);

        Subtask subtask1 = new Subtask(0, "Подзадача № 1", "Дойти до метро", Status.NEW, 0);
        Subtask subtask2 = new Subtask(0, "Подзадача № 2", "Спуститься в метро", Status.NEW, 0);
        Subtask subtask3 = new Subtask(0, "Подзадача № 3", "Дождаться поезд", Status.NEW, 0);

        Epic epic2 = new Epic(0, "Большая задача № 2", "Пустая задача", Status.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(3, subtask1);
        taskManager.addSubtask(3, subtask2);
        taskManager.addSubtask(3, subtask3);
        taskManager.addEpic(epic2);

        taskManager.printTask(1);
        taskManager.printEpic(3);
        taskManager.printSubtask(4);
        taskManager.printTask(2);
        taskManager.printEpic(7);

        System.out.println("Все, что ниже - уже история:");
        System.out.println(taskManager.getHistory());

        taskManager.printTask(1);
        taskManager.printEpic(3);
        taskManager.printSubtask(4);
        taskManager.printTask(2);
        taskManager.printEpic(7);

        System.out.println("Проверка на отсутствие повторов:");
        System.out.println(taskManager.getHistory());

        taskManager.deleteTask(1);
        taskManager.deleteEpic(3);

        System.out.println("Проверка после удаления:");
        System.out.println(taskManager.getHistory());
    }
}

