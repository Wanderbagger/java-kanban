import manager.InMemoryTaskManager;
import tasks.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();
    public static void main(String[] args) {
        testingTask();
    }

    public static void testingTask() { // тестируем методы обработки задач




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

