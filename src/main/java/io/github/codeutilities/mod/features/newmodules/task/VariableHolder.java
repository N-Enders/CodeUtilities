package io.github.codeutilities.mod.features.newmodules.task;

import java.util.HashMap;

public class VariableHolder extends HashMap<String, String> {

    public VariableHolder putEvent(String key, String value) {
        put("e." + key, value);
        return this;
    }
}
