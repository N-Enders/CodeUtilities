package io.github.codeutilities.mod.features.newmodules.action;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class ActionParameterHolder extends HashMap<String, String> {

    public ActionParameterHolder(JsonObject action) {
        action.remove("action");

        action.entrySet().forEach(e -> {
            put(e.getKey(), e.getValue().toString());
        });
    }
}
