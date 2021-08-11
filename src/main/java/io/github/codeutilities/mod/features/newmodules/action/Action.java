package io.github.codeutilities.mod.features.newmodules.action;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.codeutilities.mod.features.newmodules.task.VariableHolder;

import java.util.ArrayList;
import java.util.List;

public class Action {

    private final JsonObject json;
    private final ActionExecutor executor;

    public Action(JsonObject json) {
        this.json = json;
        this.executor = ActionExecutor.of(getRawType());
    }

    public JsonObject getJson() {
        return json;
    }

    public String getRawType() {
        return json.get("action").getAsString();
    }

    public ActionExecutor getExecutor() {
        return executor;
    }

    public void run() {
        getExecutor().run(new ActionParameterHolder(getJson()));
    }

    public static Action[] of(JsonArray actions) {
        List<Action> result = new ArrayList<>();

        for (JsonElement action : actions) {
            result.add(of(action));
        }

        return result.toArray(new Action[0]);
    }

    public static Action of(JsonElement action) {
        return of(action.getAsJsonObject());
    }

    public static Action of(JsonObject action) {
        return new Action(action);
    }

}
