package io.github.codeutilities.mod.features.newmodules;

import com.google.gson.JsonObject;

public class Module {

    private final String id;

    public Module(JsonObject json) {
        JsonObject meta = json.getAsJsonObject("meta");

        this.id = meta.get("id").getAsString();
    }

    public String getId() {
        return id;
    }
}
