package manager;

import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;
    protected Task createTask() {
        return new Task(TypeTask.TASK, 0, "Description", "Title", Status.NEW, Instant.now(), 0);
    }
    protected Epic createEpic() {

        return new Epic(TypeTask.EPIC, 0, "Description", "Title", Status.NEW, Instant.now(), 0);
    }
    protected Subtask createSubtask(Epic epic) {
        return new Subtask(TypeTask.SUBTASK, 0, "Description", "Title", Status.NEW, Instant.now(), 0);
    }

    @Test
    public void shouldCreateTask() {
        Task task = createTask();
        manager.addTask(task);
        Map<Integer, Task> tasks = manager.getAllTasks();
        assertNotNull(task.getStatus());
        assertEquals(Status.NEW, task.getStatus());
        assertEquals(List.of(task), tasks);
    }

    @Test
    public void shouldCreateEpic() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        Map<Integer,Epic> epics = manager.getAllEpics();
        assertNotNull(epic.getStatus());
        assertEquals(Status.NEW, epic.getStatus());
        assertEquals(List.of(epic), epics);
    }

    @Test
    public void shouldUpdateTaskStatusToInProgress() {
        Task task = createTask();
        manager.addTask(task);
        task.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task.getId(), task);
        assertEquals(Status.IN_PROGRESS, manager.getTaskById(task.getId()).getStatus());
    }

    @Test
    public void shouldUpdateEpicStatusToInProgress() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        epic.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, manager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateSubtaskStatusToInProgress() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        Subtask subtask = createSubtask(epic);
        manager.addSubtask(epic.getId(), subtask);
        subtask.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(epic.getId(), subtask);
        assertEquals(Status.IN_PROGRESS, manager.getSubtaskById(subtask.getId()).getStatus());
        assertEquals(Status.IN_PROGRESS, manager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateTaskStatusToInDone() {
        Task task = createTask();
        manager.addTask(task);
        task.setStatus(Status.DONE);
        manager.updateTask(task.getId(), task);
        assertEquals(Status.DONE, manager.getTaskById(task.getId()).getStatus());
    }

    @Test
    public void shouldUpdateEpicStatusToInDone() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        epic.setStatus(Status.DONE);
        assertEquals(Status.DONE, manager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateSubtaskStatusToInDone() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        Subtask subtask = createSubtask(epic);
        manager.addSubtask(epic.getId(), subtask);
        subtask.setStatus(Status.DONE);
        manager.updateSubtask(subtask.getId(), subtask);
        assertEquals(Status.DONE, manager.getSubtaskById(subtask.getId()).getStatus());
        assertEquals(Status.DONE, manager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldNotUpdateTaskIfNull() {
        Task task = createTask();
        manager.addTask(task);
        manager.updateTask(0, null);
        assertEquals(task, manager.getTaskById(task.getId()));
    }

    @Test
    public void shouldNotUpdateEpicIfNull() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        manager.updateEpic(0, null);
        assertEquals(epic, manager.getEpicById(epic.getId()));
    }

    @Test
    public void shouldNotUpdateSubtaskIfNull() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        Subtask subtask = createSubtask(epic);
        manager.addSubtask(epic.getId(), subtask);
        manager.updateSubtask(0,null);
        assertEquals(subtask, manager.getSubtaskById(subtask.getId()));
    }

    @Test
    public void shouldDeleteAllTasks() {
        Task task = createTask();
        manager.addTask(task);
        manager.deleteAllTasks();
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks());
    }

    @Test
    public void shouldDeleteAllEpics() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        manager.deleteAllEpics();
        assertEquals(Collections.EMPTY_LIST, manager.getAllEpics());
    }

    @Test
    public void shouldDeleteAllSubtasks() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        Subtask subtask = createSubtask(epic);
        manager.addSubtask(epic.getId(), subtask);
        manager.deleteAllSubtasksByEpicId(epic.getId());
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldDeleteAllSubtasksByEpic() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        Subtask subtask = createSubtask(epic);
        manager.addSubtask(epic.getId(), subtask);
        manager.deleteAllSubtasksByEpicId(epic.getId());
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldDeleteTaskById() {
        Task task = createTask();
        manager.addTask(task);
        manager.deleteTask(task.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks());
    }

    @Test
    public void shouldDeleteEpicById() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        manager.deleteEpic(epic.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getAllEpics());
    }

    @Test
    public void shouldNotDeleteTaskIfBadId() {
        Task task = createTask();
        manager.addTask(task);
        manager.deleteTask(999);
        assertEquals(List.of(task), manager.getAllTasks());
    }

    @Test
    public void shouldNotDeleteEpicIfBadId() {
        Epic epic = createEpic();
        manager.addEpic(epic);
        manager.deleteEpic(999);
        assertEquals(List.of(epic), manager.getAllEpics());
    }

    @Test
    public void shouldDoNothingIfTaskHashMapIsEmpty(){
        manager.deleteAllTasks();
        manager.deleteTask(999);
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void shouldDoNothingIfEpicHashMapIsEmpty(){
        manager.deleteAllEpics();
        manager.deleteEpic(999);
        assertTrue(manager.getAllEpics().isEmpty());
    }

    @Test
    public void shouldDoNothingIfSubtaskHashMapIsEmpty(){
        manager.deleteAllEpics();
        manager.deleteSubtask(999);
        assertEquals(0, manager.getAllSubtasks().size());
    }

    @Test
    public void shouldReturnEmptyListTasksIfNoTasks() {
        assertTrue(manager.getAllTasks().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListEpicsIfNoEpics() {
        assertTrue(manager.getAllEpics().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListSubtasksIfNoSubtasks() {
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldReturnNullIfTaskDoesNotExist() {
        assertNull(manager.getTaskById(999));
    }

    @Test
    public void shouldReturnNullIfEpicDoesNotExist() {
        assertNull(manager.getEpicById(999));
    }

    @Test
    public void shouldReturnNullIfSubtaskDoesNotExist() {
        assertNull(manager.getSubtaskById(999));
    }

    @Test
    public void shouldReturnEmptyHistory() {
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void shouldReturnEmptyHistoryIfTasksNotExist() {
        manager.getTaskById(999);
        manager.getSubtaskById(999);
        manager.getEpicById(999);
        assertTrue(manager.getHistory().isEmpty());
    }
}