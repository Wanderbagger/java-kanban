package manager;

import tasks.Task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager<T> implements HistoryManager{
    private Map<Integer, Node> nodeMap = new HashMap<>();
    private Node last;
    private Node first;

    LinkedList<Task> history = new LinkedList<>();
    static public final int HISTORY_MAX_LENGTH = 10;

    HashMap historyTable = new HashMap();


    @Override
    public void addRecord(Task task) { // добавление записи в историю прсмотров
        linkedLast(task);
        nodeMap.put(task.getId(), last);
        history.add(task);

        if (history.size() > HISTORY_MAX_LENGTH) {
            history.remove(0);
        }
    }

    private void linkedLast(Task task) {
       Node newNode = new Node(task, last, null);

    }

    @Override
    public void removeRecord(int id) {
        history.remove();
    } // удаление записи из истории прсмотров

    @Override
    public List<Task> getHistory() {
        return history;
    } // возврат истории просмотров

    private class Node{
        Task task;
        Node prev;
        Node next;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "task=" + task +
                    ", prev=" + (prev != null ? prev.task : null) +
                    ", next=" + (next != null ? next.task : null) +
                    '}';
        }
    }
}
