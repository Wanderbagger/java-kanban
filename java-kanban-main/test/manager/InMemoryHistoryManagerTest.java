package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;
import tasks.TypeTask;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    HistoryManager manager;
    private int id = 0;

    public int generateId() {
        return ++id;
    }

    protected Task createTask() {
        return new Task(TypeTask.TASK, 0, "Description", "Title", Status.NEW, Instant.now(), 0);
    }

    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    public void shouldAddTasksToHistory() {
        Task task1 = createTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = createTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = createTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.addRecord(task1);
        manager.addRecord(task2);
        manager.addRecord(task3);
        assertEquals(List.of(task1, task2, task3), manager.getHistory());
    }

    @Test
    public void shouldRemoveTask() {
        Task task1 = createTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = createTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = createTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.addRecord(task1);
        manager.addRecord(task2);
        manager.addRecord(task3);
        manager.removeRecord(task2.getId());
        assertEquals(List.of(task1, task3), manager.getHistory());
    }

    @Test
    public void shouldRemoveOnlyOneTask() {
        Task task = createTask();
        int newTaskId = generateId();
        task.setId(newTaskId);
        manager.addRecord(task);
        manager.removeRecord(task.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void shouldHistoryIsEmpty() {
        Task task1 = createTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = createTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = createTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.removeRecord(task1.getId());
        manager.removeRecord(task2.getId());
        manager.removeRecord(task3.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void shouldNotRemoveTaskWithBadId() {
        Task task = createTask();
        int newTaskId = generateId();
        task.setId(newTaskId);
        manager.addRecord(task);
        manager.removeRecord(0);
        assertEquals(List.of(task), manager.getHistory());
    }
}