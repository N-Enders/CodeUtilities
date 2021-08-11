package io.github.codeutilities.mod.features.newmodules.action;

import com.google.gson.JsonObject;
import io.github.codeutilities.mod.features.newmodules.action.executor.MessageActionExecutor;

public abstract class ActionExecutor {

    private static final ActionExecutor[] ACTION_EXECUTORS = new ActionExecutor[]{
            new MessageActionExecutor()
    };

    public static ActionExecutor[] getExecutors() {
        return ACTION_EXECUTORS;
    }

    public static ActionExecutor of(JsonObject action) {
        return of(action.get("action").getAsString());
    }

    public static ActionExecutor of(String key) {
        for (ActionExecutor executor : ACTION_EXECUTORS) {
            if (executor.getKey().equals(key)) {
                return executor;
            }
        }
        throw new IllegalArgumentException();
    }

    public abstract String getKey();

    public abstract void run(ActionParameterHolder parameters);
}
