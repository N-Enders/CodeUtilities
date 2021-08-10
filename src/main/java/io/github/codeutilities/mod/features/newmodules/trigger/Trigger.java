package io.github.codeutilities.mod.features.newmodules.trigger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.codeutilities.mod.features.newmodules.task.Task;
import io.github.codeutilities.mod.features.newmodules.Module;

import java.util.ArrayList;
import java.util.List;

// TODO make java record in 1.17
public class Trigger {

    private final Module module;
    private final EventTrigger eventTrigger;
    private final Task task;

    public Trigger(Module module, EventTrigger eventTrigger, Task task) {
        this.module = module;
        this.eventTrigger = eventTrigger;
        this.task = task;
    }

    public Module getModule() {
        return module;
    }

    public EventTrigger getEventTrigger() {
        return eventTrigger;
    }

    public Task getTask() {
        return task;
    }

    public static Trigger[] of(Module module, JsonArray triggers) {
        List<Trigger> result = new ArrayList<>();

        for (JsonElement element : triggers) {
            JsonObject trigger = element.getAsJsonObject();
            result.add(of(module, trigger));
        }

        return result.toArray(new Trigger[0]);
    }

    public static Trigger of(Module module, JsonObject trigger) {
        return of(
                module,
                trigger.get("event").getAsString(),
                trigger.get("task").getAsString()
        );
    }

    public static Trigger of(Module module, String eventTrigger, String task) {
        return new Trigger(
                module,
                EventTrigger.of(eventTrigger),
                module.getTask(task)
        );
    }
}
