import manager.HistoryManager;
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

        taskManager.addTask(task1);

        taskManager.addEpic(epic1);
        taskManager.addSubtask(2, subtask1);
        taskManager.addTask(task2);
        taskManager.printTask(1);

        taskManager.printEpic(2);
        taskManager.printSubtask(3);
        taskManager.printTask(4);


        // проверим историю просмотров
        System.out.println("Все, что ниже - уже история:");
        System.out.println(taskManager.getHistory());
        // проверим пару методов на выбор


        taskManager.deleteTask(1);
        taskManager.deleteEpic(2);
        taskManager.deleteSubtask(3);


        System.out.println("Проверка после удаления:");
        System.out.println(taskManager.getHistory());
        taskManager.printAllSubtasks(2);


    }
}

