package manager;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import httpclient.KVTaskClient;

import tasks.Epic;
import tasks.Task;
import tasks.Subtask;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HttpTaskManager extends FileBackedTasksManager{

    private final InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    private final KVTaskClient kvTaskClient = new KVTaskClient();
    private final Gson gson = Managers.getGson();

    public HttpTaskManager() throws IOException {
        super(null);
    }

    @Override
    public void save(){ //Сохранение на сервер

        String jsonTask = gson.toJson(tasks);
        kvTaskClient.save("tasks",jsonTask);

        String jsonEpics = gson.toJson(epics);
        kvTaskClient.save("epics",jsonEpics);

        String jsonSubtask = gson.toJson(subtasks);
        kvTaskClient.save("subtasks",jsonSubtask);


        List<Task> history =  getHistory();
        String jsonHistory = gson.toJson(history);
        kvTaskClient.save("history", jsonHistory);
    }



    public boolean load() { // Загрузка с сервера

        String tasksJson = kvTaskClient.load("tasks");
        String epicsJson = kvTaskClient.load("epics");
        String subtasksJson = kvTaskClient.load("subtasks");
        String historyJson = kvTaskClient.load("history");

        tasks = gson.fromJson(tasksJson, new TypeToken<HashMap<Integer, Task>>(){}.getType());
        epics= gson.fromJson(epicsJson, new TypeToken<HashMap<Integer, Epic>>(){}.getType());
        subtasks = gson.fromJson(subtasksJson, new TypeToken<HashMap<Integer, Subtask>>(){}.getType());
        ArrayList<Task> tasksHistoryJson = gson.fromJson(historyJson, new TypeToken<ArrayList<Task>>(){}.getType());

        for (Task taskhistory : tasksHistoryJson){ //
         Task task = tasks.get(taskhistory.getId());
         Epic epic = epics.get(taskhistory.getId());
         Subtask subTask = subtasks.get(taskhistory.getId());
            if(task != null){
                inMemoryHistoryManager.addRecord(task);
            }else if(epic != null){
                inMemoryHistoryManager.addRecord(epic);
            }else {
                inMemoryHistoryManager.addRecord(subTask);
            }
        }
        return false;
    }
}