package seedu.modtracker;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TaskListTest {
    @Test
    public void addTask_nonExistingModule_expectTaskNotAdded() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TaskList taskList = new TaskList(new ModuleList());
        taskList.addTask("addtask CS2101 study", true, null);
        String expected = "CS2101 does not exist." + System.lineSeparator();
        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void addTask_invalidModule_expectTaskNotAdded() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TaskList taskList = new TaskList(new ModuleList());
        taskList.addTask("addtask cs21 study", true, null);
        String expected = "Please check module code again. The module code should have "
                + "6 - 8 characters without any spacing." + System.lineSeparator();
        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

}
