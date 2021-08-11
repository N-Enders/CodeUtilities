package io.github.codeutilities.sys.renderer;

import io.github.codeutilities.CodeUtilities;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import net.minecraft.client.gui.screen.Screen;

public class CLightweightGuiDescription extends LightweightGuiDescription {

    public void onClose() {
        CodeUtilities.MC.openScreen((Screen)null);
    }
}
