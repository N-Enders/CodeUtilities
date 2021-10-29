package io.github.codeutilities.mod.mixin.inventory;

import io.github.codeutilities.CodeUtilities;
import io.github.codeutilities.mod.config.Config;
import io.github.codeutilities.mod.events.impl.ReceiveChatMessageEvent;
import io.github.codeutilities.mod.features.keybinds.FlightspeedToggle;
import io.github.codeutilities.mod.features.CPU_UsageText;
import io.github.codeutilities.sys.player.DFInfo;
import io.github.codeutilities.sys.networking.State;
import io.github.codeutilities.sys.hypercube.templates.TemplateStorageHandler;
import io.github.codeutilities.sys.hypercube.templates.TemplateUtils;
import io.github.codeutilities.sys.player.chat.ChatUtil;
import io.github.codeutilities.sys.player.chat.MessageGrabber;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MItemSlotUpdate {
    final MinecraftClient mc = MinecraftClient.getInstance();
    private long lobbyTime = System.currentTimeMillis() - 1000;
    private long lastDevCheck = 0;

    @Inject(method = "onScreenHandlerSlotUpdate", at = @At("HEAD"))
    public void onScreenHandlerSlotUpdate(ScreenHandlerSlotUpdateS2CPacket packet, CallbackInfo ci) {
        if (packet.getSyncId() == 0) {
            ItemStack stack = packet.getItemStack();
            if (TemplateUtils.isTemplate(stack)) {
                TemplateStorageHandler.addTemplate(stack);
            }

            NbtCompound nbt = stack.getOrCreateNbt();
            NbtCompound display = nbt.getCompound("display");
            NbtList lore = display.getList("Lore", 8);
            if (mc.player == null) {
                return;
            }

            if (DFInfo.isOnDF() && stack.getName().getString().contains("◇ Game Menu ◇")
                    && lore.getString(0).contains("\"Click to open the Game Menu.\"")
                    && lore.getString(1).contains("\"Hold and type in chat to search.\"")) {

                DFInfo.currentState.sendLocate();

                // Auto fly
                if (Config.getBoolean("autofly")) {
                    if (System.currentTimeMillis() > lobbyTime) { // theres a bug with /fly running twice this is a temp fix.
                        mc.player.sendChatMessage("/fly");
                        MessageGrabber.hide(1);
                        lobbyTime = System.currentTimeMillis() + 1000;
                    }
                }

                CPU_UsageText.lagSlayerEnabled = false;

                // fs toggle
                FlightspeedToggle.fs_is_normal = true;
            }

            if (DFInfo.isOnDF() && mc.player.isCreative() && stack.getName().getString().contains("Player Event")
                    && lore.getString(0).contains("\"Used to execute code when something\"")
                    && lore.getString(1).contains("\"is done by (or happens to) a player.\"")
                    && lore.getString(2).contains("\"Example:\"")) {

                DFInfo.currentState.sendLocate();
                DFInfo.plotCorner = mc.player.getPos().add(10, -50, -10);

                // Auto LagSlayer
                if (!CPU_UsageText.lagSlayerEnabled && Config.getBoolean("autolagslayer")) {
                    ChatUtil.executeCommandSilently("lagslayer");
                }

                // fs toggle
                FlightspeedToggle.fs_is_normal = true;

                long time = System.currentTimeMillis() / 1000L;
                if (time - lastDevCheck > 1) {

                    new Thread(() -> {
                        try {
                            Thread.sleep(10);
                            if (Config.getBoolean("autoRC")) {
                                mc.player.sendChatMessage("/rc");
                            }
                            if (Config.getBoolean("autotime")) {
                                ChatUtil.executeCommandSilently("time " + Config.getLong("autotimeval"));
                            }
                            if (Config.getBoolean("autonightvis")) {
                                ChatUtil.executeCommandSilently("nightvis");
                            }
                        } catch (Exception e) {
                            CodeUtilities.log(Level.ERROR, "Error while executing the task!");
                            e.printStackTrace();
                        }
                    }).start();

                    lastDevCheck = time;
                }
            }
        }
    }

}
