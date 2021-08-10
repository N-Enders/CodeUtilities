package io.github.codeutilities.mod.features.newmodules.task;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskHandler {

    private static TaskHandler INSTANCE;
    private static final HashMap<String, Task> TASKS = new HashMap<>();

    public TaskHandler() {
        INSTANCE = this;
    }

    public static TaskHandler getInstance() {
        return INSTANCE == null ? new TaskHandler() : INSTANCE;
    }

    public void register(Task... tasks) {
        for (Task task : tasks) {
            register(task);
        }
    }

    public void register(Task task) {
        TASKS.put(task.getFullName(), task);
    }

    public Task getTask(String name) {
        return TASKS.get(name);
    }
}
