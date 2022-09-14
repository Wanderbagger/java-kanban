package manager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node last;
    private Node first;

    @Override
    public void addRecord(Task task) { // добавление записи в историю прсмотров
        if (nodeMap.containsKey(task.getId())){
            removeRecord(task.getId());
        }
        linkLast(task);
    }

    public void linkLast(Task task) { // добавление записи в конец CustomLinkedList
        final Node oldLast = last; // извлекли из поля значение старого последнего нода
        final Node newNode = new Node(task, oldLast, null);  // создали новый нод
        last = newNode; // сделали новый нод последним
        nodeMap.put(task.getId(), newNode);
        if (oldLast == null) {
            first = newNode; // если нет последнего, значит новый нод - первый
        } else {
            oldLast.next = newNode;
        }
    }

    public void removeNode(Node node) { // удаление нода из CustomLinkedList
        if (node != null) {
            final Node next = node.next;
            final Node prev = node.prev;
            node.task = null;

            if (first == node && last == node) { // если в CustomLinkedList нет других нот, сотрем данные о предыдущей и следующей
                first = null;
                last = null;
            } else if (first == node) { // если удаляем первый - приравняем поле first ко второму ноду и сотрем его указание на предудыщий
                first = next;
                first.prev = null;
            } else if (last == node) { // если удаляем последний - приравняем поле last к предпоследнему и обрубим его указание на следующий
                last = prev;
                last.next = null;
            } else {  // свяжем хвосты
                prev.next = next;
                next.prev = prev;
            }
        }
    }

    @Override
    public void removeRecord(int id) { // удаление записи из истории прсмотров
        removeNode(nodeMap.get(id));
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node currentNode = first;
        while (currentNode != null) {
            tasks.add(currentNode.task);
            currentNode = currentNode.next;
        }
        return tasks;
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
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
