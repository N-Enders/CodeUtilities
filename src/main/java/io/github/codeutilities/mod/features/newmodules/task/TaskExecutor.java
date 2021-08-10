package io.github.codeutilities.mod.features.newmodules.task;

public class TaskExecutor {

    private static TaskExecutor INSTANCE;

    public TaskExecutor() {
        INSTANCE = this;
    }

    public static TaskExecutor getInstance() {
        return INSTANCE == null ? new TaskExecutor() : INSTANCE;
    }

    public void execute(Task task, VariableHolder variables) {
        //TODO
    }
}
