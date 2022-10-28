package manager;

import com.google.gson.Gson;
import httpclient.KVTaskClient;

import org.junit.jupiter.api.BeforeEach;
import server.HttpTaskServer;
import server.KVServer;

import org.junit.jupiter.api.*;

import tasks.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {

    KVServer kvServer;
    HttpTaskServer httpTaskServer;
    HttpClient client;
    KVTaskClient kvTaskClient;
    Gson gson = new Gson();

    @BeforeEach
    public void createManger() {
        try {
            kvServer = new KVServer();
            kvServer.start();
            httpTaskServer = new HttpTaskServer();
            httpTaskServer.start();
            kvTaskClient = new KVTaskClient();
            client = HttpClient.newHttpClient();
            valueTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Тестовые данные")
    public void valueTest() {
        Instant startTime = Instant.now();
        Task task = new Task(TypeTask.TASK,1, "Описание задачи 1", "Задача 1", Status.NEW,  startTime, 1);
        httpTaskServer.getTaskManager().addTask(task);
        startTime = startTime.plusSeconds(300);
        Epic epic = new Epic(TypeTask.EPIC,2, "Описание эпика 2", "эпик 2", Status.NEW,  startTime, 1);
        httpTaskServer.getTaskManager().addEpic(epic);
        startTime = startTime.plusSeconds(300);
        Subtask subTask = new Subtask(TypeTask.SUBTASK,3, "Описание подзадачи 3", "подзадача 3", Status.NEW,  startTime, 1);
        httpTaskServer.getTaskManager().addSubtask(2, subTask);

    }


    @Test
    @DisplayName("Создание TASK")
    public void createTask() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8079/tasks/task/");
        Instant startTime = Instant.now();
        Task newTask = new Task(TypeTask.TASK,4, "Описание задачи 4", "Задача 4", Status.NEW,  startTime, 1);
        String json = gson.toJson(newTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> task = httpTaskServer.getTaskManager().getAllTasks();
        assertEquals(task.size(), 2);
    }

    @Test
    @DisplayName("Создание EPIC")
    public void createEpic() throws IOException, InterruptedException {

        URI url = URI.create("http://localhost:8079/tasks/epic/");
        Instant startTime = Instant.now();
        Epic newEpic = new Epic(TypeTask.EPIC,5, "Описание эпика 5", "эпик 5", Status.NEW,  startTime, 1);
        String json = gson.toJson(newEpic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Epic> epics = httpTaskServer.getTaskManager().getAllEpics();
        assertEquals(epics.size(), 2);
    }

    @Test
    @DisplayName("Создание SubTask")
    public void createSubTask() throws IOException, InterruptedException {

        URI url = URI.create("http://localhost:8079/tasks/subtask/");
        Instant startTime = Instant.now();
        Subtask newSubTask = new Subtask(TypeTask.SUBTASK,6, "Описание подзадачи 6", "подзадача 3", Status.NEW,  startTime, 1);
        String json = gson.toJson(newSubTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Subtask> subTasks = httpTaskServer.getTaskManager().getAllSubtasks();
        assertEquals(subTasks.size(), 2);
    }

    @Test
    @DisplayName("Получение TASK ID")
    public void getTaskId() throws IOException, InterruptedException {
        String taskJson = gson.toJson(httpTaskServer.getTaskManager().getTaskById(1));

        URI url = URI.create("http://localhost:8079/tasks/task/?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.body(), taskJson);
    }

    @Test
    @DisplayName("Получение Epic ID")
    public void getEpicId() throws IOException, InterruptedException {
        String epicJson = gson.toJson(httpTaskServer.getTaskManager().getEpicById(2));

        URI url = URI.create("http://localhost:8079/tasks/epic/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.body(), epicJson);
    }

    @Test
    @DisplayName("Получение SubTask ID")
    public void getSubTaskId() throws IOException, InterruptedException {
        String subTaskJson = gson.toJson(httpTaskServer.getTaskManager().getSubtaskById(3));

        URI url = URI.create("http://localhost:8079/tasks/subtask/?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.body(), subTaskJson);
    }

    @Test
    @DisplayName("Удаление TASK")
    public void deleteTask() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8079/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> task = httpTaskServer.getTaskManager().getAllTasks();
        assertEquals(task.size(), 0);
    }

    @Test
    @DisplayName("Удаление EPIC")
    public void deleteEpic() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8079/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Epic> epics = httpTaskServer.getTaskManager().getAllEpics();
        assertEquals(epics.size(), 0);
    }

    @Test
    @DisplayName("Удаление SUBTASK")
    public void deleteSubTask() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8079/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Subtask> subTasks = httpTaskServer.getTaskManager().getAllSubtasks();
        assertEquals(subTasks.size(), 0);
    }

    @Test
    @DisplayName("Удаление TASK ID")
    public void deleteTaskID() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8079/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.body(), "");
    }

    @Test
    @DisplayName("Удаление EPIC ID")
    public void deleteEpicID() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8079/tasks/epic/?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.body(), "");
    }

    @Test
    @DisplayName("Удаление SubTask ID")
    public void deleteSubTaskID() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8079/tasks/subtask/?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.body(), "");
    }

    @Test
    @DisplayName("Получение истории")
    public void getHistory() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8079/tasks/history/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.body(),"[]");

         httpTaskServer.getTaskManager().getTaskById(1);
         httpTaskServer.getTaskManager().getEpicById(2);
         httpTaskServer.getTaskManager().getSubtaskById(3);

        String str = gson.toJson(httpTaskServer.getTaskManager().getHistory());
        HttpRequest newRequest = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> newResponse = client.send(newRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(newResponse.body(),str);
    }
}