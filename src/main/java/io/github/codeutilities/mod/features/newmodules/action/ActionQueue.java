package io.github.codeutilities.mod.features.newmodules.action;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ActionQueue extends ArrayList<Action> {

    private int i = 0;

    public ActionQueue(List<Action> asList) {
        super(asList);
    }

    public int getIndex() {
        return i;
    }

    public boolean whileNext() {
        return i < size();
    }

    public Action next() {
        i++;
        return get(i - 1);
    }

    public Action jump(int index) {
        i = index;
        return get(index);
    }
}
