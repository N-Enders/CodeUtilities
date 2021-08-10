package io.github.codeutilities.mod.features.newmodules.menus;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.codeutilities.mod.features.newmodules.Module;
import io.github.codeutilities.mod.features.newmodules.task.Task;
import io.github.codeutilities.sys.renderer.IMenu;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.text.LiteralText;

public class ModuleTaskMenu extends LightweightGuiDescription implements IMenu {

    Module module;
    Task[] tasks;

    public ModuleTaskMenu(Module m) {
        module = m;
        tasks = m.getTasks();
    }

    @Override
    public void open(String... args) throws CommandSyntaxException {
        WPlainPanel root = new WPlainPanel();
        root.setSize(110,115);
        WPlainPanel panel = new WPlainPanel();

        WLabel label = new WLabel(new LiteralText("§nTask List - " + tasks.length + " Tasks"));
        root.add(label,0,0);

        int y = 0;
        for (Task t : tasks) {
            WLabel row1 = new WLabel(new LiteralText(t.getName()));
            WLabel row2 = new WLabel(new LiteralText(t.getActions().length + " Actions"));
            panel.add(row1,0,y);
            panel.add(row2, 0, y+10);

            WButton editBtn = new WButton(new LiteralText("E"));
            panel.add(editBtn, 80, y, 20, 20);
            y+=25;
        }

        WScrollPanel spanel = new WScrollPanel(panel);
        spanel.setScrollingHorizontally(TriState.FALSE);
        spanel.setScrollingVertically(TriState.TRUE);
        root.add(spanel,0,15,110,100);
        setRootPanel(root);
        root.validate(this);
    }
}
