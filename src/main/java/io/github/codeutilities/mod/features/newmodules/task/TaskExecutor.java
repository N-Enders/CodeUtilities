package io.github.codeutilities.mod.features.newmodules.task;

import io.github.codeutilities.mod.features.newmodules.action.Action;
import io.github.codeutilities.mod.features.newmodules.action.ActionParameterHolder;
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
        new Thread(() -> run(task, variables)).start();
    }

    public void run(Task task, VariableHolder variables) {
        variables.putDefault();

        ActionQueue actions = task.getActionQueue();

        while (actions.whileNext()) {
            Action action = actions.next();
            action.run();
        }
    }
}
