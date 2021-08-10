package io.github.codeutilities.mod.features.social.chat.message.finalizers;

import io.github.codeutilities.mod.features.newmodules.task.VariableHolder;
import io.github.codeutilities.mod.features.newmodules.trigger.EventTrigger;
import io.github.codeutilities.mod.features.social.chat.message.Message;
import io.github.codeutilities.mod.features.social.chat.message.MessageFinalizer;

public class ModuleFinalizer extends MessageFinalizer {

    @Override
    protected void receive(Message message) {
        EventTrigger.MESSAGE_RECEIVED.run(
                new VariableHolder()
                        .putEvent("message.stripped", message.getStripped())
        );
    }
}
