import manager.Managers;
import manager.TaskManager;

import tasks.*;
import server.KVServer;

import java.io.IOException;
import java.time.Instant;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Instant startTime = Instant.now();
        KVServer kvServer = new KVServer();
        kvServer.start();

        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task(TypeTask.TASK,1, "Описание задачи 1", "Задача 1", Status.NEW,  startTime, 1);
        startTime = startTime.plusSeconds(300);
        Task task2 = new Task(TypeTask.TASK,2, "Описание задачи 2", "Задача 2", Status.NEW,  startTime, 1);
        startTime = startTime.plusSeconds(300);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.save(); //Сохранение на сервер

        Epic epic3 = new Epic(TypeTask.EPIC,3, "Описание эпика 3", "Эпик 3", Status.NEW,  startTime, 1);
        startTime = startTime.plusSeconds(300);
        Epic epic4 = new Epic(TypeTask.EPIC,4, "Описание эпика 4", "Эпик 4", Status.NEW,  startTime, 1);
        startTime = startTime.plusSeconds(300);
        taskManager.addEpic(epic3);
        taskManager.addEpic(epic4);

        Task updatedTask = new Task(TypeTask.TASK,1, "Изменение задачи 1", "Измененная задача 1", Status.NEW,  startTime, 1);
        startTime = startTime.plusSeconds(300);
        taskManager.updateTask(1, updatedTask);

        Subtask subTask5 = new Subtask(TypeTask.SUBTASK,5, "Описание подзадачи 5", "Подзадача 5", Status.NEW,  startTime, 1);
        startTime = startTime.plusSeconds(300);
        Subtask subTask6 = new Subtask(TypeTask.SUBTASK,6, "Описание подзадачи 6", "Подзадача 6", Status.NEW,  startTime, 1);
        startTime = startTime.plusSeconds(300);
        Subtask subTask7 = new Subtask(TypeTask.SUBTASK,7, "Описание подзадачи 7", "Подзадача 7", Status.NEW,  startTime, 1);

        taskManager.addSubtask(3, subTask5);
        taskManager.addSubtask(3, subTask6);
        taskManager.addSubtask(3, subTask7);

        taskManager.printTask(1);
        taskManager.save();

        System.out.println(taskManager.getHistory());
        taskManager.save();
        taskManager.load();


        System.out.println("Проверка данных на сервере");
        for (String data: kvServer.getData().values()) {
            System.out.println(data);
        }

        System.out.println();
        System.out.println("Получение данных: ");
        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
        System.out.println("История: " + taskManager.getHistory());
    }
}