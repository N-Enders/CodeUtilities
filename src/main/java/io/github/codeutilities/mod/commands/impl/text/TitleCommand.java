package io.github.codeutilities.mod.commands.impl.text;

import static io.github.codeutilities.mod.commands.arguments.ArgBuilder.argument;
import static io.github.codeutilities.mod.commands.arguments.ArgBuilder.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.codeutilities.mod.commands.Command;
import io.github.codeutilities.sys.util.TextUtil;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class TitleCommand extends Command {

    @Override
    public void register(MinecraftClient mc, CommandDispatcher<FabricClientCommandSource> cd) {
        reg("previewtitle",mc,cd);
        reg("titlepreview",mc,cd);
    }

    @Override
    public String getDescription() {
        return "[blue]/previewtitle [text][reset]\n"
                + "[blue]/titlepreview [text][reset]\n"
                + "\n"
                + "Previews the title text. If no text is specified, the name of the item you are holding will show up.";
    }

    @Override
    public String getName() {
        return "/previewtitle";
    }

    public void reg(String name, MinecraftClient mc, CommandDispatcher<FabricClientCommandSource> cd) {
        cd.register(literal(name)
            .then(argument("message", StringArgumentType.greedyString())
                .executes(ctx -> {
                    Text msg = TextUtil.colorCodesToTextComponent(
                        ctx.getArgument("message", String.class)
                            .replace("&", "§"));

                    mc.inGameHud.setTitle(msg);
                    mc.inGameHud.setTitleTicks(20, 60, 20);
                    return 1;
                })
            )
            .executes(ctx -> {
                mc.inGameHud.setTitle(mc.player.getMainHandStack().getName());
                mc.inGameHud.setTitleTicks(20, 60, 20);
                return 1;
            })
        );
    }
}
