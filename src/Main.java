import manager.FileBackedTasksManager;
import tasks.*;

import java.io.File;

import static tasks.TypeTask.*;

public class Main {
     public static void main(String[] args) {
        System.out.println("Начало проверки");

        File data = new File("data.csv");
        FileBackedTasksManager dataFileBackedTasksManager = FileBackedTasksManager.loadFromFile(data);
         File save = new File("data.csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(save);

         Task task2 = new Task(TASK, 0, "Задача 1", "Проснуться", Status.NEW);
         fileBackedTasksManager.addTask(task2);

         Task task3 = new Task(TASK, 0, "Задача 2", "Умыться", Status.NEW);
         fileBackedTasksManager.addTask(task3);

         Epic epic3 = new Epic(EPIC, 0, "Эпик 1", "Побриться", Status.NEW);
         fileBackedTasksManager.addEpic(epic3);

         Subtask subtask3 = new Subtask(SUBTASK, 0, "Подзадача 1", "вдруг обнаружить что тебе это снится",
                 Status.NEW, epic3.getId());
         fileBackedTasksManager.addSubtask(epic3.getId(), subtask3);
         fileBackedTasksManager.printTask(task3.getId());
         fileBackedTasksManager.printTask(task2.getId());
         fileBackedTasksManager.printEpic(epic3.getId());
         fileBackedTasksManager.printSubtask(subtask3.getId());
         fileBackedTasksManager.save("save.csv");
         if (dataFileBackedTasksManager.equals(fileBackedTasksManager))
         {
             System.out.println("Изменений не было!");
         } else {
             System.out.println("Сохранение изменений...");
             fileBackedTasksManager.save("data.csv");
         }
        System.out.println("Конец проверки!");
    }
}