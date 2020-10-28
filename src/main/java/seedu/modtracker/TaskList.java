package seedu.modtracker;

import java.util.ArrayList;

/**
 * Contains the task list and operations to manipulate the tasks.
 */
public class TaskList {
    public static ArrayList<Task> tasks = new ArrayList<>();
    public Ui ui = new Ui();
    private ModuleList modList;
    public static final String MARKED_DONE_PREVIOUSLY = "Task is already marked as done previously.";
    public static final String MARKED_DONE = "Nice! I've marked this task as done:";
    public static final String TASK_DELETED = "Noted. I've removed this task:";

    public TaskList(ModuleList modList) {
        this.modList = modList;
    }

    /**
     * Marks a selected task as done.
     * Can only take in one task number at a time.
     *
     * @param str input entered by user
     */
    public void setDone(String str, boolean toPrint, Storage storage) {
        try {
            String[] digit = str.trim().split(" ", 2);
            int num = Integer.parseInt(digit[1]); //change string to int
            if (tasks.size() == 0) {
                assert toPrint : "toPrint should be true";
                ui.printTaskList(this);
            } else if (num <= tasks.size() && num > 0) {
                if (tasks.get(num - 1).getDoneStatus()) {
                    assert toPrint : "toPrint should be true";
                    System.out.println(MARKED_DONE_PREVIOUSLY + System.lineSeparator());
                } else {
                    tasks.get(num - 1).setAsDone();
                    if (toPrint) {
                        System.out.println(MARKED_DONE);
                        System.out.println(tasks.get(num - 1) + System.lineSeparator());
                        storage.appendToFile(str);
                    }
                }
            } else {
                assert toPrint : "toPrint should be true";
                ui.printInvalidTaskNumber(this);
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            assert toPrint : "toPrint should be true";
            ui.printErrorMessage(Parser.COMMAND_DONE);
        }
    }

    /**
     * Deletes a selected task from the task list.
     * Can only take in one task number at a time.
     *
     * @param str input entered by user
     */
    public void deleteTasks(String str, boolean toPrint, Storage storage) {
        try {
            String[] digit = str.trim().split(" ", 2);
            int num = Integer.parseInt(digit[1]); //change string to int
            if (tasks.size() == 0) {
                assert toPrint : "toPrint should be true";
                ui.printTaskList(this);
            } else if (num <= tasks.size() && num > 0) {
                if (toPrint) {
                    System.out.println(TASK_DELETED);
                    System.out.println(tasks.get(num - 1));
                }
                tasks.remove(num - 1);
                if (toPrint) {
                    ui.printNumberOfTasks(this);
                    storage.appendToFile(str);
                }
            } else {
                assert toPrint : "toPrint should be true";
                ui.printInvalidTaskNumber(this);
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            assert toPrint : "toPrint should be true";
            ui.printErrorMessage(Parser.COMMAND_DELETETASK);
        }
    }

    /**
     * Adds a task to the task list.
     *
     * @param str input entered by user
     */
    public void addTask(String str, boolean toPrint, Storage storage) {
        try {
            String[] split = str.trim().split(" ", 3);
            String modCode = split[1];
            modCode = modCode.toUpperCase();
            if (!modList.checkIfModuleValid(modCode, true)) {
                return;
            }
            if (!modList.checkIfModuleExist(modCode)) {
                assert toPrint : "toPrint should be true";
                /*Module currentModule = new Module(modCode);
                modList.modList.add(currentModule);*/
                ui.printNotExist(modCode);
                return;
            }
            tasks.add(new Task(modCode, split[2]));
            if (toPrint) {
                ui.printTaskIsAdded(this, modCode);
                ui.printNumberOfTasks(this);
                storage.appendToFile(str);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            assert toPrint : "toPrint should be true";
            ui.printErrorMessage(Parser.COMMAND_ADDTASK);
        }
    }

    public ArrayList<Task> getTaskData() {
        return tasks;
    }

    public void clear() {
        tasks = new ArrayList<>();
    }
}
