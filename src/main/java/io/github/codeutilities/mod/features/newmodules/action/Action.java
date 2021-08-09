package io.github.codeutilities.mod.features.newmodules.action;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Action {

    public Action() {

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
        //TODO
        return null;
    }
}
