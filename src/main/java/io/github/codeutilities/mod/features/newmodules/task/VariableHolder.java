package io.github.codeutilities.mod.features.newmodules.task;

import io.github.codeutilities.sys.player.DFInfo;

import java.util.HashMap;

public class VariableHolder extends HashMap<String, String> {

    public VariableHolder putEvent(String key, String value) {
        put("e." + key, value);
        return this;
    }

    public VariableHolder putPlayer(String key, String value) {
        put("p." + key, value);
        return this;
    }

    public void putDefault() {
        //TODO
        /*
        putPlayer("state.mode", DFInfo.currentState.mode.getIdentifier().toLowerCase());
        putPlayer("state.continuous", DFInfo.currentState.mode.getContinuousVerb().toLowerCase());

         */
    }
}
