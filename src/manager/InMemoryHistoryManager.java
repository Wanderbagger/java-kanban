package manager;

import tasks.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private List<Task> history = new ArrayList<>();

    @Override
    public void addRecord(Task task) { // добавление записи в историю прсмотров
        history.add(task);
        final int MAXIMUM_LENGTH = 10;
        if (history.size() > MAXIMUM_LENGTH) {
            history.remove(0);
        }
    }

    @Override
    public void removeRecord(int id) {
        history.remove(id);
    } // удаление записи из истории прсмотров

    @Override
    public List<Task> getHistory() {
        return history;
    } // возврат истории просмотров
}