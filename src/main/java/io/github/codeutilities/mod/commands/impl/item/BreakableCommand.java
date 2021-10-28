package io.github.codeutilities.mod.commands.impl.item;

import com.mojang.brigadier.CommandDispatcher;
import io.github.codeutilities.mod.commands.Command;
import io.github.codeutilities.mod.commands.arguments.ArgBuilder;
import io.github.codeutilities.sys.player.chat.ChatType;
import io.github.codeutilities.sys.player.chat.ChatUtil;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

public class BreakableCommand extends Command {

    @Override
    public void register(MinecraftClient mc, CommandDispatcher<FabricClientCommandSource> cd) {
        cd.register(ArgBuilder.literal("breakable")
                .executes(ctx -> {
                    if (this.isCreative(mc)) {
                        ItemStack item = mc.player.getMainHandStack();
                        if (item.getItem() != Items.AIR) {
                            NbtCompound nbt = item.getOrCreateNbt();
                            nbt.putBoolean("Unbreakable", false);
                            item.setNbt(nbt);
                            mc.interactionManager.clickCreativeStack(item, 36 + mc.player.getInventory().selectedSlot);
                            ChatUtil.sendMessage("The item you're holding is now breakable!", ChatType.SUCCESS);
                        } else {
                            ChatUtil.sendMessage("You need to hold an item in your main hand!", ChatType.FAIL);
                        }
                    } else {
                        return -1;
                    }
                    return 1;
                })
        );
    }

    @Override
    public String getDescription() {
        return "[blue]/breakable[reset]\n"
                + "\n"
                + "Opposite of /unbreakable - Removes the Unbreakable tag from the item you are holding.";
    }

    @Override
    public String getName() {
        return "/breakable";
    }
}
