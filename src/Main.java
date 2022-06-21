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
        Task task2 = new Task(0, "Задача № 2", "Почистить зубы", Status.NEW);
        Task task3 = new Task(0, "Задача № 3", "Позавтракать", Status.NEW);
        Task task4 = new Task(1, "Задача № 4", "Привести себя в порядок", Status.NEW);

        Epic epic1 = new Epic(0, "Большая задача № 1", "Отправиться на работу", Status.NEW);
        Epic epic2 = new Epic(0, "Большая задача № 2", "Хорошенько поработать", Status.NEW);

        Subtask subtask1 = new Subtask(0, "Подзадача № 1", "Дойти до метро", Status.NEW, 0);
        Subtask subtask2 = new Subtask(0, "Подзадача № 2", "Прокатиться на метро", Status.NEW, 0);
        Subtask subtask3 = new Subtask(0, "Подзадача № 3", "Дойти до офиса", Status.NEW, 0);
        Subtask subtask4 = new Subtask(0, "Подзадача № 4", "Сделать рассылку по дирекциям", Status.NEW, 0);
        Subtask subtask5 = new Subtask(0, "Подзадача № 5", "Созвониться с контрагентами", Status.NEW, 0);
        Subtask subtask6 = new Subtask(0, "Подзадача № 6", "Подготовить проект доп.соглашения № 2", Status.NEW, 0);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(5, subtask1);
        taskManager.addSubtask(5, subtask2);
        taskManager.addSubtask(5, subtask3);
        taskManager.addSubtask(6, subtask4);
        taskManager.addSubtask(6, subtask5);
        taskManager.addSubtask(6, subtask6);
        taskManager.printTask(1);
        taskManager.printTask(2);
        taskManager.printTask(3);
        taskManager.printTask(4);
        taskManager.printEpic(5);
        taskManager.printEpic(6);
        taskManager.printSubtask(7);
        taskManager.printSubtask(8);
        taskManager.printSubtask(9);
        taskManager.printSubtask(10);
        taskManager.printSubtask(11);
        taskManager.printSubtask(12);
        // проверим историю просмотров
        System.out.println("Все, что ниже - уже история:");
        System.out.println(taskManager.getHistory());
        // проверим пару методов на выбор
        taskManager.printAllSubtasks(6);
        taskManager.deleteAllSubtasks(6);
        System.out.println("Удалили все подзадачи эпика № 6");
        taskManager.printAllSubtasks(6);
        taskManager.printEpic(6);
        Task task5 = new Task(0, "Обновленная задача", "Поиграть в космических рейнджеров", Status.NEW);
        taskManager.printTask(3);
        taskManager.updateTask(3, task5);
        taskManager.printTask(3);
        // проверим изменение статуса эпика при обновлении всех его подзадач на готовые
        Subtask subtask7 = new Subtask(0, "Подзадача № 1", "Дойти до метро", Status.DONE, 5);
        Subtask subtask8 = new Subtask(0, "Подзадача № 2", "Прокатиться на метро", Status.DONE, 5);
        Subtask subtask9 = new Subtask(0, "Подзадача № 3", "Дойти до офиса", Status.DONE, 5);
        taskManager.updateSubtask(7,subtask7);
        taskManager.updateSubtask(8,subtask8);
        taskManager.updateSubtask(9,subtask9);
        taskManager.printEpic(5);
    }
}

