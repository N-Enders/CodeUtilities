package io.github.codeutilities.mod.features.newmodules.menus;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.codeutilities.mod.features.newmodules.Module;
import io.github.codeutilities.sys.renderer.IMenu;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.LiteralText;

public class ModuleEditMenu extends LightweightGuiDescription implements IMenu {

    Module module;

    public ModuleEditMenu(Module m) {
        module = m;
    }

    @Override
    public void open(String... args) throws CommandSyntaxException {
        WPlainPanel root = new WPlainPanel();
        root.setSize(150, 90);

        WTextField idf = new WTextField();
        WTextField authorf = new WTextField();
        WTextField versionf = new WTextField();
        idf.setText(module.getId());
        authorf.setText(module.getAuthor());
        versionf.setText(module.getVersion());

        root.add(idf, 0,0, 150, 30);
        root.add(authorf, 0,25, 100, 30);
        root.add(versionf, 100,25, 50, 30);

        //trigger, task, config, translation

        WButton triggerb = new WButton(new LiteralText("Triggers"));
        WButton taskb = new WButton(new LiteralText("Tasks"));
        WButton configb = new WButton(new LiteralText("Config"));
        WButton translationb = new WButton(new LiteralText("Translations"));

        root.add(triggerb, 0,50,75,0);
        root.add(taskb, 80,50,75,0);
        root.add(configb, 0,70,75,0);
        root.add(translationb, 80,70,75,0);

        taskb.setOnClick(() -> {
            ModuleTaskMenu menu = new ModuleTaskMenu(module);
            menu.scheduleOpenGui(menu);
        });

        setRootPanel(root);
        root.validate(this);
    }
}
