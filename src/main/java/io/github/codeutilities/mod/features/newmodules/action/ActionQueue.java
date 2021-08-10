package io.github.codeutilities.mod.features.newmodules.action;

import java.util.ArrayList;
import java.util.List;

public class ActionQueue extends ArrayList<Action> {

    private int i = 0;

    public ActionQueue(List<Action> asList) {
        super(asList);
    }
}
