package io.github.codeutilities.mod.features.newmodules.menus;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.codeutilities.mod.features.newmodules.Module;
import io.github.codeutilities.mod.features.newmodules.ModuleLoader;
import io.github.codeutilities.sys.renderer.IMenu;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import java.util.HashMap;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.text.LiteralText;

public class MainModuleMenu extends LightweightGuiDescription implements IMenu {

    @Override
    public void open(String... args) throws CommandSyntaxException {
        WPlainPanel root = new WPlainPanel();
        root.setSize(210, 240);

        HashMap<String, Module> modules = ModuleLoader.getInstance().getModules();
        WText label = new WText(new LiteralText("§nModule List - " + modules.size() + " loaded."));
        root.add(label, 0, 0, 200, 0);

        WPlainPanel panel = new WPlainPanel();

        int y = 3;
        for (Module m : modules.values()) {
            WLabel row1 = new WLabel(new LiteralText(m.getId() + " v" + m.getVersion()));
            WLabel row2 = new WLabel(new LiteralText("by " + m.getAuthor()));

            panel.add(row1, 0, y, 999, 0);
            panel.add(row2, 0, y + 10, 999, 0);

            WButton editButton = new WButton(new LiteralText("E"));
            panel.add(editButton, 180, y, 20, 20);

            editButton.setOnClick(() -> {
               ModuleEditMenu em = new ModuleEditMenu(m);
               em.scheduleOpenGui(em);
            });

            y += 25;
        }
        WButton newModule = new WButton(new LiteralText("Create new module."));

        panel.add(newModule, 0, y, 200, 0);

        WScrollPanel spanel = new WScrollPanel(panel);
        spanel.setScrollingHorizontally(TriState.FALSE);
        spanel.setScrollingVertically(TriState.TRUE);

        root.add(spanel, 0, 10, 210, 220);
        setRootPanel(root);
        root.validate(this);
    }
}
