package test;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @BeforeEach
    void init() {
        manager = new InMemoryTaskManager();
    }
    @Test
    void inMemoryTaskManagerTest(){
        manager=new InMemoryTaskManager();
        assertEquals(0,manager.getAllTasks().size());
        assertEquals(0,manager.getAllEpics().size());
        assertEquals(0,manager.getAllSubtasks().size());
    }
}