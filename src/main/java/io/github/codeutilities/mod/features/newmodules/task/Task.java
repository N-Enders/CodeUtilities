package io.github.codeutilities.mod.features.newmodules.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.codeutilities.mod.features.newmodules.Module;
import io.github.codeutilities.mod.features.newmodules.action.Action;
import io.github.codeutilities.mod.features.newmodules.action.ActionQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO make java record in 1.17
public class Task {

    private final String name;
    private final Action[] actions;
    private final Module module;

    public Task(String name, Action[] actions, Module module) {
        this.name = name;
        this.actions = actions;
        this.module = module;
    }

    // Getters ----------------------------------------

    public String getName() {
        return name;
    }

    public String getFullName() {
        return module.getFullId() + "." + getName();
    }

    public Action[] getActions() {
        return actions;
    }

    public ActionQueue getActionQueue() {
        return new ActionQueue(Arrays.asList(actions));
    }

    public Module getModule() {
        return module;
    }

    // ----------------------------------------------

    public void run(VariableHolder variables) {
        TaskExecutor.getInstance().execute(this, variables);
    }

    public static Task[] of(JsonArray tasks, Module module) {
        List<Task> result = new ArrayList<>();

        for (JsonElement task : tasks) {
            result.add(of(task, module));
        }

        return result.toArray(new Task[0]);
    }

    public static Task of(JsonElement task, Module module) {
        return of(task.getAsJsonObject(), module);
    }

    public static Task of(JsonObject task, Module module) {
        return new Task(
                task.get("name").getAsString(),
                Action.of(task.get("actions").getAsJsonArray()),
                module
        );
    }
}
