package manager;

import tasks.Task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager<T> implements HistoryManager{
    public Node<T> head;
    public Node<T> tail;

    LinkedList<Task> history = new LinkedList<>();
    static public final int HISTORY_MAX_LENGTH = 10;

    HashMap historyTable = new HashMap();


    @Override
    public void addRecord(Task task) { // добавление записи в историю прсмотров
        history.add(task);

        if (history.size() > HISTORY_MAX_LENGTH) {
            history.remove(0);
        }
    }

    @Override
    public void removeRecord(int id) {
        history.remove();
    } // удаление записи из истории прсмотров

    @Override
    public List<Task> getHistory() {
        return history;
    } // возврат истории просмотров
}