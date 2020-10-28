package seedu.modtracker;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModuleListTest {
    private static final String TEST_FILEPATH = "test/data/modlist.txt";
    Storage storage = new Storage(TEST_FILEPATH);
    ModuleList modulesTest = new ModuleList();

    @Test
    public void addExp_emptyModuleList_newModuleWithExpTimeAdded() {
        ArrayList<Module> testList = new ArrayList<>();
        Module testMod = new Module("CS3030", "4");
        testList.add(testMod);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        modulesTest.addExp("addExp CS3030 4", true, storage);
        String expected = "CS3030, Expected Workload: 4h is added." + System.lineSeparator();
        assertArrayEquals(testList.toArray(), modulesTest.getData().toArray());
        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void addModule_emptyModuleList_newModuleAdded() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "CS5000 is added." + System.lineSeparator();

        modulesTest.addMod("addMod CS5000", true, storage);
        assertTrue(modulesTest.checkIfModuleExist("CS5000"));
        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void addModule_invalidInput_printErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "Please type addmod <module code>" + System.lineSeparator();
        modulesTest.addMod("addMod", true, storage);
        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void addModule_invalidModuleSpacing_printErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "Please type module code without any spacing." + System.lineSeparator();
        modulesTest.addMod("addmod cs 1010", true, storage);
        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void addModule_invalidModuleChar_printErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "Please check module code again. The module code should have 6 - 8 "
                + "characters without any spacing." + System.lineSeparator();
        modulesTest.addMod("addmod cs1234567", true, storage);
        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void deleteModule_removeModule_ModuleRemoved() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "CS5000 is removed." + System.lineSeparator()
                + "All tasks under CS5000 are deleted." + System.lineSeparator();

        modulesTest.deleteMod("deleteMod CS5000", true, storage, new TaskList(modulesTest));
        assertFalse(modulesTest.checkIfModuleExist("CS5000"));
        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void deleteModule_removeNonExistingModule_printErrorMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "CS0000 does not exist." + System.lineSeparator();

        modulesTest.deleteMod("deleteMod CS0000", true, storage,  new TaskList(modulesTest));
        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void deleteExp_ModuleWithExpTime_ModuleWithExpTimeRemoved() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        assertEquals(true, modulesTest.checkIfModuleExist("CS3030"));
        modulesTest.deleteExp("deleteExp CS3030", true, storage);
        String expected = "Expected Workload of CS3030 is removed." + System.lineSeparator();
        assertEquals(expected + System.lineSeparator(), outContent.toString());
    }

}