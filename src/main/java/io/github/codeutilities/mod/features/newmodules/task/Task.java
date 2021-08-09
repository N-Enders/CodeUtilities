package io.github.codeutilities.mod.features.newmodules.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.codeutilities.mod.features.newmodules.action.Action;

import java.util.ArrayList;
import java.util.List;

// TODO make java record in 1.17
public class Task {

    private final String name;
    private final Action[] actions;

    public Task(String name, Action[] actions) {
        this.name = name;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public Action[] getActions() {
        return actions;
    }

    public static Task[] of(JsonArray tasks) {
        List<Task> result = new ArrayList<>();

        for (JsonElement task : tasks) {
            result.add(of(task));
        }

        return result.toArray(new Task[0]);
    }

    public static Task of(JsonElement task) {
        return of(task.getAsJsonObject());
    }

    public static Task of(JsonObject task) {
        return new Task(
                task.get("name").getAsString(),
                Action.of(task.get("actions").getAsJsonArray())
        );
    }
}
