package io.github.codeutilities.mod.features.newmodules.trigger;

import io.github.codeutilities.mod.features.newmodules.task.Task;

import java.util.*;

public class TriggerHandler {

    private static final HashMap<EventTrigger, Set<Task>> TRIGGERS = new HashMap<>();

    public static HashMap<EventTrigger, Set<Task>> getTriggers() {
        return TRIGGERS;
    }

    public static void register(Trigger[] triggers) {
        for (Trigger trigger : triggers) {
            register(trigger);
        }
    }

    public static void register(Trigger trigger) {
        TRIGGERS.putIfAbsent(trigger.getEventTrigger(), new HashSet<>());
        Set<Task> eventTasks = TRIGGERS.get(trigger.getEventTrigger());
        eventTasks.add(trigger.getTask());
    }
}
