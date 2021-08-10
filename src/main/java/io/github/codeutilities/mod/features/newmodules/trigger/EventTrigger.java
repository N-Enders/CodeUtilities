package io.github.codeutilities.mod.features.newmodules.trigger;

import io.github.codeutilities.mod.features.newmodules.task.Task;
import io.github.codeutilities.mod.features.newmodules.task.VariableHolder;

import java.util.Set;

public enum EventTrigger {

    MESSAGE_RECEIVED("messageReceived"),
    ;

    private final String id;

    EventTrigger(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Set<Task> getTasks() {
        return TriggerHandler.getTriggers().get(this);
    }

    public void run(VariableHolder variables) {
        getTasks().forEach(t -> {
            t.run(variables);
        });
    }

    public static EventTrigger of(String key) {
        for (EventTrigger trigger : values()) {
            if (trigger.getId().equals(key)) {
                return trigger;
            }
        }
        throw new IllegalArgumentException();
    }

}
