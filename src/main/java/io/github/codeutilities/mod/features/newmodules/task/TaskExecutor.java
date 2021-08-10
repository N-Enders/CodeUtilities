package io.github.codeutilities.mod.features.newmodules.task;

import io.github.codeutilities.mod.features.newmodules.action.ActionQueue;

public class TaskExecutor {

    private static TaskExecutor INSTANCE;

    public TaskExecutor() {
        INSTANCE = this;
    }

    public static TaskExecutor getInstance() {
        return INSTANCE == null ? new TaskExecutor() : INSTANCE;
    }

    public void execute(Task task, VariableHolder variables) {
        System.out.println("executing task "+task.getFullName());

        ActionQueue actions = task.getActionQueue();
        //TODO execute actions
    }
}
