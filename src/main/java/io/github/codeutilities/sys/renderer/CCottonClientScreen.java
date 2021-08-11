package io.github.codeutilities.sys.renderer;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;

public class CCottonClientScreen extends CottonClientScreen {

    CLightweightGuiDescription desc;

    public CCottonClientScreen(CLightweightGuiDescription description) {
        super(description);
        desc = description;
    }

    @Override
    public void onClose() {
        desc.onClose();
    }
}
