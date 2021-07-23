package io.github.codeutilities.mod.commands.item.template;

import com.google.gson.JsonObject;
import io.github.codeutilities.CodeUtilities;
import io.github.codeutilities.sys.util.chat.ChatType;
import io.github.codeutilities.sys.util.chat.ChatUtil;
import io.github.codeutilities.sys.util.networking.socket.SocketHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;

public class SendTemplateCommand extends AbstractTemplateCommand {

    @Override
    protected String getName() {
        return "sendtemplate";
    }

    @Override
    protected void withTemplate(ItemStack stack) {
        NbtCompound rawNBT = MinecraftClient.getInstance().player.getMainHandStack().getNbt();
        JsonObject bukkitValues = CodeUtilities.JSON_PARSER.parse(rawNBT.get("PublicBukkitValues").toString()).getAsJsonObject();
        JsonObject templateData = CodeUtilities.JSON_PARSER.parse(bukkitValues.get("hypercube:codetemplatedata").getAsString().replace("\\", "")).getAsJsonObject();
        JsonObject toSend = new JsonObject();
        toSend.addProperty("received", templateData.toString());
        toSend.addProperty("type", "template");
        SocketHandler.getInstance().sendData(toSend.toString());

        CodeUtilities.MC.player.playSound(SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, 200, 1);

        ChatUtil.sendMessage("Sent your current held item to any connected clients!", ChatType.INFO_BLUE);
    }
}
