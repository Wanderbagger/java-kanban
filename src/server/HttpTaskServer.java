package server;

import com.google.gson.Gson;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer  {

    public static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final HttpServer httpServer;
    private final Gson gson;
    private final TaskManager taskManager;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        this.gson = Managers.getGson();
        this.httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void start() {
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту.");
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("HTTP-сервер на порту " + PORT + " остановлен.");
    }

    class TaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) {

            int id;
            String method = httpExchange.getRequestMethod();

            try {
            String path = httpExchange.getRequestURI().getPath();
                String[] splitStrings = path.split("/");
                String query = httpExchange.getRequestURI().getRawQuery();
                String[] splitId = new String[]{""};

                if (query != null) {
                    splitId = query.split("=");
                }

                switch (method) {
                    case "GET":

                        if (splitStrings[splitStrings.length - 1].equals("task") && (query == null)) { // Список Задач
                            String taskJson = gson.toJson(taskManager.getAllTasks());
                            httpExchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(taskJson.getBytes());
                            }
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("epic") && (query == null)) { // Список Эпиков
                            String epicJson = gson.toJson(taskManager.getAllEpics());
                            httpExchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(epicJson.getBytes());
                            }
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("subtask") && (query == null)) { // Список Подзадач
                            String subtaskJson = gson.toJson(taskManager.getAllSubtasks());
                            httpExchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(subtaskJson.getBytes());
                            }
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("task") && (query != null)) { // Вывод задачи
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            String taskIdJson = gson.toJson(taskManager.getTaskById(id));
                            httpExchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(taskIdJson.getBytes());
                            }
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("epic") && (query != null)) { // Вывод Эпика
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            String epicIdJson = gson.toJson(taskManager.getEpicById(id));
                            httpExchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(epicIdJson.getBytes());
                            }
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("subtask") && (query != null)) { // Вывод подзадачи
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            String subtaskIdJson = gson.toJson(taskManager.getSubtaskById(id));
                            httpExchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(subtaskIdJson.getBytes());
                            }
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("history")) { // Вывод истории
                            String history = gson.toJson(taskManager.getHistory());
                            httpExchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(history.getBytes());
                            }
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("priority")) { // Список приоритетов
                            String history = gson.toJson(taskManager.getPrioritizedTasks());
                            httpExchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(history.getBytes());
                            }
                            break;
                        }


                    case "POST":

                        if (splitStrings[splitStrings.length - 1].equals("task") && (query == null)) { // Добавление Задачи
                            InputStream inputStream = httpExchange.getRequestBody();
                            String newTask = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            inputStream.close();

                            Task task = gson.fromJson(newTask, Task.class);
                            taskManager.addTask(task);
                            httpExchange.sendResponseHeaders(201, 0);
                        }

                        else if (splitStrings[splitStrings.length - 1].equals("task") && (query != null)) { // Изменение Задачи
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            InputStream inputStream = httpExchange.getRequestBody();
                            String newTask = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            inputStream.close();

                            Task task = gson.fromJson(newTask, Task.class);
                            taskManager.updateTask(id, task);
                            httpExchange.sendResponseHeaders(201, 0);
                        }

                        else if (splitStrings[splitStrings.length - 1].equals("epic") && (query == null)) { // Добавление Эпика
                            InputStream inputStream = httpExchange.getRequestBody();
                            String newEpic = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            inputStream.close();

                            Epic epic = gson.fromJson(newEpic, Epic.class);
                            taskManager.addEpic(epic);
                            httpExchange.sendResponseHeaders(201, 0);
                        }

                        else if (splitStrings[splitStrings.length - 1].equals("epic") && (query != null)) { // Изменение Эпика
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            InputStream inputStream = httpExchange.getRequestBody();
                            String newEpic = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            inputStream.close();

                            Epic epic = gson.fromJson(newEpic, Epic.class);
                            taskManager.updateTask(id, epic);
                            httpExchange.sendResponseHeaders(201, 0);
                        }

                        else if (splitStrings[splitStrings.length - 1].equals("subtask") && (query == null)) { // Добавление Подзадачи
                            InputStream inputStream = httpExchange.getRequestBody();
                            String newSubtask = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            inputStream.close();

                            Subtask subtask = gson.fromJson(newSubtask, Subtask.class);
                            taskManager.addSubtask(subtask.getEpicId(), subtask);
                            httpExchange.sendResponseHeaders(201, 0);
                        }

                        else if (splitStrings[splitStrings.length - 1].equals("subtask") && (query != null)) { // Изменение подзадачи
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            InputStream inputStream = httpExchange.getRequestBody();
                            String newSubtask = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            inputStream.close();

                            Subtask subTask = gson.fromJson(newSubtask, Subtask.class);
                            taskManager.updateSubtask(id, subTask);
                            httpExchange.sendResponseHeaders(201, 0);

                        } else {
                            httpExchange.sendResponseHeaders(404, 0);
                        }

                        break;


                    case "DELETE":

                        if (splitStrings[splitStrings.length - 1].equals("task") && (query != null)) { // удаление одной задачи
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            taskManager.deleteTask(id);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("epic") && (query != null)) { // Удаление одного эпика
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            taskManager.deleteEpic(id);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("subtask") && (query != null)) { // Удаление одной подзадачи
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            taskManager.deleteSubtask(id);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("task") && (query == null)) { // Удаление всех задач
                            taskManager.deleteAllTasks();
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("epic") && (query == null)) { // Удаление всех эпиков и подзадач с ними
                            taskManager.deleteAllEpics();
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }

                        if (splitStrings[splitStrings.length - 1].equals("subtask") && (query == null)) { // Удаление всех подзадач
                            taskManager.deleteAllSubtasks();
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                }
                httpExchange.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}