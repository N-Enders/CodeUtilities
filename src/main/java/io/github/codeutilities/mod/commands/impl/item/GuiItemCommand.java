package io.github.codeutilities.mod.commands.impl.item;

import static io.github.codeutilities.mod.commands.arguments.ArgBuilder.argument;
import static io.github.codeutilities.mod.commands.arguments.ArgBuilder.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.codeutilities.CodeUtilities;
import io.github.codeutilities.mod.commands.Command;
import io.github.codeutilities.sys.player.chat.ChatType;
import io.github.codeutilities.sys.player.chat.ChatUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;

public class GuiItemCommand extends Command {

    MinecraftClient mc = CodeUtilities.MC;

    @Override
    public void register(MinecraftClient mc, CommandDispatcher<FabricClientCommandSource> cd) {
        cd.register(literal("guiitem")
            .then(literal("texture")
                .then(argument("id", StringArgumentType.greedyString())
                    .executes(ctx -> {
                        if (checks()) {
                            return -1;
                        }
                        String id = ctx.getArgument("id", String.class);

                        if (id.startsWith("#")) {
                            File folder = new File(getClass().getResource("/assets/codeutilities/gui").getFile());
                            List<String> contents = Arrays.stream(folder.listFiles()).map(e -> e.getName().replaceAll("\\.png","")).collect(Collectors.toList());
                            if (contents.contains(id.substring(1))) {
                                ItemStack item = mc.player.getMainHandStack();
                                item.getOrCreateSubTag("CodeUtilitiesGui").put("id", StringTag.of(id));
                                setHand(item);
                                ChatUtil.sendMessage("Set texture to " + id.substring(1),ChatType.SUCCESS);
                            } else {
                                ChatUtil.sendMessage("Unknown Local Texture!", ChatType.FAIL);
                                ChatUtil.sendMessage("Available ones:\n"+String.join(", ",contents), ChatType.INFO_YELLOW);
                            }
                        } else if (id.matches("\\w+")) {
                            ItemStack item = mc.player.getMainHandStack();
                            item.getOrCreateSubTag("CodeUtilitiesGui").put("id", StringTag.of(id));
                            setHand(item);
                            ChatUtil.sendMessage("Set texture to https://i.imgur.com/" + id + ".png",ChatType.SUCCESS);
                        } else {
                            ChatUtil.sendMessage("Invalid Texture",ChatType.FAIL);
                            ChatUtil.sendMessage("Example using a pre-made texture: #arrow_right",ChatType.INFO_YELLOW);
                            ChatUtil.sendMessage("Example using an imgur link: fMlsLJE",ChatType.INFO_YELLOW);
                        }

                        return 1;
                    })
                )
            )
        );
        regNum("width",cd,false);
        regNum("height",cd,false);
        regNum("offx",cd,true);
        regNum("offy",cd,true);
    }

    private void regNum(String name, CommandDispatcher<FabricClientCommandSource> cd, boolean infinite) {
        float min = infinite ? -99999 : 0.01f;
        float max = infinite ? 99999 : 10;
        cd.register(literal("guiitem")
            .then(literal(name)
                .then(argument(name,FloatArgumentType.floatArg(min,max))
                    .executes(ctx -> {
                        if (checks()) {
                            return -1;
                        }
                        double num = ctx.getArgument(name,Float.class);
                        ItemStack item = mc.player.getMainHandStack();
                        item.getOrCreateSubTag("CodeUtilitiesGui").put(name, DoubleTag.of(num));
                        setHand(item);
                        ChatUtil.sendMessage("Set " + name + " to " + num,ChatType.SUCCESS);
                        return 1;
                    })
                )
            )
        );
    }

    private void setHand(ItemStack item) {
        mc.interactionManager.clickCreativeStack(item, mc.player.inventory.selectedSlot + 36);
    }

    private boolean checks() {
        if (mc.player.getMainHandStack().getItem() == Items.AIR) {
            ChatUtil.sendMessage("You need to hold an item!", ChatType.FAIL);
            return true;
        }
        if (!mc.player.isCreative()) {
            ChatUtil.sendMessage("You need to be in creative mode!", ChatType.FAIL);
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "";//todo: add description to /guiitem
    }

    @Override
    public String getName() {
        return "/guiitem";
    }
}
