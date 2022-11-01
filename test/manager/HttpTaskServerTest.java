package manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import server.HttpTaskServer;
import tasks.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class HttpTaskServerTest {

    private HttpTaskServer httpTaskServer;
    private TaskManager taskManager;
    private final Gson gson = Managers.getGson();

    private Task task;
    private Epic epic;
    private Subtask subtask;
    Map<Integer, Task> tasks;
    Map<Integer, Epic> epics;
    Map<Integer, Subtask> subtasks;

    @BeforeEach
    void setUp() throws IOException {
        Instant startTime = Instant.now();
        taskManager = new FileBackedTasksManager();
        httpTaskServer = new HttpTaskServer(taskManager);

        task = new Task(TypeTask.TASK,1, "Описание задачи 1", "Задача 1", Status.NEW,  startTime, 1);
        taskManager.addTask(task);
        startTime = startTime.plusSeconds(300);
        epic = new Epic(TypeTask.EPIC,2, "Описание эпика 2", "Эпик 2", Status.NEW,  startTime, 1);
        taskManager.addEpic(epic);
        startTime = startTime.plusSeconds(300);
        subtask = new Subtask(TypeTask.SUBTASK,3, "Описание подзадачи 3", "Подзадача 3", Status.NEW,  startTime, 1);
        taskManager.addSubtask(2, subtask);
        startTime = startTime.plusSeconds(300);

        taskManager.printTask(task.getId());
        taskManager.printEpic(epic.getId());
        taskManager.printSubtask(subtask.getId());

        Task task2 = new Task(TypeTask.TASK,4, "Описание задачи 4", "Задача 4", Status.NEW,  startTime, 1);
        taskManager.addTask(task2);
        startTime = startTime.plusSeconds(300);

        Epic epic2 = new Epic(TypeTask.EPIC,5, "Описание эпика 5", "Эпик 5", Status.NEW,  startTime, 1);
        taskManager.addEpic(epic2);
        startTime = startTime.plusSeconds(300);

        Subtask subtask2 = new Subtask(TypeTask.SUBTASK,6, "Описание подзадачи 6", "Подзадача 6", Status.NEW,  startTime, 1);
        httpTaskServer.getTaskManager().addSubtask(5, subtask2);
        startTime = startTime.plusSeconds(300);

        Subtask subtask3 = new Subtask(TypeTask.SUBTASK,7, "Описание подзадачи 7", "Подзадача 7", Status.NEW,  startTime, 1);
        taskManager.addSubtask(5, subtask3);
        taskManager.printTask(task2.getId());
        taskManager.printEpic(epic2.getId());
        taskManager.printSubtask(subtask2.getId());

        tasks = taskManager.getAllTasks();
        epics = taskManager.getAllEpics();
        subtasks = taskManager.getAllSubtasks();

        httpTaskServer.start();

    }

    @AfterEach
    void tearDown() {
        httpTaskServer.stop();
    }


    /*__________________Task_Tests__________________*/

    @Test
    void shouldGetAllTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type typeTasks = new TypeToken<Map<Integer, Task>>() {
        }.getType();
        Map<Integer, Task> tasksReceived = gson.fromJson(response.body(), typeTasks);

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertNotNull(tasksReceived, "Список задач не получен");
        assertEquals(tasks.size(), tasksReceived.size(), "Размер полученого списка не соответствует " +
                "количеству задач");
        assertEquals(tasks, tasksReceived, "Получен неверный список задач");
    }

    @Test
    void shouldGetTaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type typeTask = new TypeToken<Task>() {
        }.getType();
        Task taskReceived = gson.fromJson(response.body(), typeTask);

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertNotNull(taskReceived, "Задача не получена");
        assertEquals(task, taskReceived, "Получена неверная задача");
    }


    @Test
    void shouldAddTask() throws IOException, InterruptedException {
        Instant startTime = Instant.now();
        startTime = startTime.plusSeconds(5000);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Task newTask = new Task(TypeTask.TASK,4, "Описание задачи 4", "Задача 4", Status.NEW,  startTime, 1);
        String json = gson.toJson(newTask);

        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 201");
        assertEquals(3, tasks.size(), "Размер списка задач неверен");
        }

    @Test
    void shouldDelAllTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertTrue(tasks.isEmpty(), "Список задач не пуст");
    }

    @Test
    void shouldDelTaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertNull(tasks.get(1), "Задача с id:1 не удалена из списка");
    }

    @Test
    void shouldGetAllSubtasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type typeSubtasks = new TypeToken<Map<Integer, Subtask>>() {
        }.getType();
        Map<Integer, Subtask> subtasksReceived = gson.fromJson(response.body(), typeSubtasks);

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertNotNull(subtasksReceived, "Список подзадач не получен");
        assertEquals(subtasks.size(), subtasksReceived.size(), "Размер полученого списка не соответствует " +
                "количеству подзадач");
        assertEquals(subtasks, subtasksReceived, "Получен неверный список подзадач");
    }

    @Test
    void shouldGetSubtaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type typeSubtask = new TypeToken<Subtask>() {
        }.getType();
        Subtask subtaskReceived = gson.fromJson(response.body(), typeSubtask);

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertNotNull(subtaskReceived, "Задача не получена");
        assertEquals(subtask, subtaskReceived, "Получена неверная задача");
    }

    @Test
    void shouldAddSubtask() throws IOException, InterruptedException {
        Instant startTime = Instant.now();
        startTime = startTime.plusSeconds(56000);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        Subtask newSubtask = new Subtask(TypeTask.SUBTASK,9, "Описание подзадачи 9", "Подзадача 9", Status.NEW,  startTime, 1);
        String json = gson.toJson(newSubtask);

        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 201");
        assertEquals(4, subtasks.size(), "Размер списка подзадач неверен");
    }

    @Test
    void shouldDelAllSubtasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertTrue(subtasks.isEmpty(), "Список подзадач не пуст");
    }

    @Test
    void shouldDelSubtaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertNull(subtasks.get(3), "Подадача с id:3 не удалена из списка");
    }

    @Test
    void shouldGetAllEpics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type typeEpics = new TypeToken<Map<Integer, Epic>>() {
        }.getType();
        Map<Integer, Epic> epicsReceived = gson.fromJson(response.body(), typeEpics);

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertNotNull(epicsReceived, "Список эпиков не получен");
        assertEquals(epics.size(), epicsReceived.size(), "Размер полученого списка не соответствует " +
                "количеству эпиков");
        assertEquals(epics, epicsReceived, "Получен неверный список эпиков");
    }

    @Test
    void shouldGetEpicById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type typeEpic = new TypeToken<Epic>() {
        }.getType();
        Epic epicReceived = gson.fromJson(response.body(), typeEpic);

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertNotNull(epicReceived, "Эпик не получен");
        assertEquals(epic, epicReceived, "Получен неверный эпик");
    }


    @Test
    void shouldAddEpic() throws IOException, InterruptedException {
        Instant startTime = Instant.now();
        startTime = startTime.plusSeconds(58000);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        Epic newEpic = new Epic(TypeTask.EPIC,10, "Описание эпика 10", "Эпик 10", Status.NEW,  startTime, 1);

        String json = gson.toJson(newEpic);

        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 201");
        assertEquals(3, epics.size(), "Размер списка эпиков неверен");
    }

    @Test
    void shouldDelAllEpics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");
        assertTrue(epics.isEmpty(), "Список эпиков не пуст");
    }

    @Test
    void shouldDelEpicById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Код ответа не совпадает с ожидаемым кодом - 200");

        assertNull(epics.get(2), "Эпик с id:2 не удален из списка");
    }
}