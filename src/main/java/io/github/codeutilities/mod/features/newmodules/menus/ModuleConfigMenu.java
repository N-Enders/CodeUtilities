package io.github.codeutilities.mod.features.newmodules.menus;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.codeutilities.mod.features.newmodules.Module;
import io.github.codeutilities.mod.features.newmodules.Module.ConfigOption;
import io.github.codeutilities.mod.features.newmodules.task.Task;
import io.github.codeutilities.sys.renderer.CLightweightGuiDescription;
import io.github.codeutilities.sys.renderer.IMenu;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.text.LiteralText;

public class ModuleConfigMenu extends CLightweightGuiDescription implements IMenu {

    Module module;
    ConfigOption[] options;

    public ModuleConfigMenu(Module m) {
        module = m;
        options = m.getConfig();
    }

    @Override
    public void open(String... args) throws CommandSyntaxException {
        WPlainPanel root = new WPlainPanel();
        root.setSize(200,165);

        WLabel label = new WLabel(new LiteralText("§nConfig - " + options.length + " Settings"));
        root.add(label,0,0);

        WPlainPanel panel = new WPlainPanel();

        int y = 0;
        for (ConfigOption o : options) {
            WTextField row1 = new WTextField(new LiteralText(o.getKey()));

//            doesnt work yet as ryan ignores the default value so this gives a npe
//            also i didnt check how this gui looks yet, probs ugly lol
//            WTextField row2 = new WTextField(new LiteralText(o.getSetting().getDefaultValue().toString()));
            WTextField row2 = new WTextField(new LiteralText("error"));
            panel.add(row1,0,y, 95, 0);
            panel.add(row2, 95, y, 95, 0);
            y+=25;
        }


        WScrollPanel spanel = new WScrollPanel(panel);
        spanel.setScrollingHorizontally(TriState.FALSE);
        spanel.setScrollingVertically(TriState.TRUE);
        root.add(spanel,0,15,200,150);

        setRootPanel(root);
        root.validate(this);
    }

    @Override
    public void onClose() {
        ModuleEditMenu em = new ModuleEditMenu(module);
        em.scheduleOpenSubGui(em);
    }
}
