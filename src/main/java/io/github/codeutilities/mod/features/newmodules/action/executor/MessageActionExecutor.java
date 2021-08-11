package io.github.codeutilities.mod.features.newmodules.action.executor;

import io.github.codeutilities.mod.features.newmodules.action.ActionExecutor;
import io.github.codeutilities.mod.features.newmodules.action.ActionParameterHolder;
import io.github.codeutilities.sys.player.chat.ChatUtil;

public class MessageActionExecutor extends ActionExecutor {

    @Override
    public String getKey() {
        return "message";
    }

    @Override
    public void run(ActionParameterHolder parameters) {
        ChatUtil.sendMessage(parameters.get("message"));
    }
}
