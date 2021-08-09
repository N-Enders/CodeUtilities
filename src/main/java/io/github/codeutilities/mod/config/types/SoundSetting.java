package io.github.codeutilities.mod.config.types;

import io.github.codeutilities.mod.config.ConfigSounds;

public class SoundSetting extends DropdownSetting<ConfigSounds> {
    public SoundSetting() {
        this(null);
    }

    public SoundSetting(String key) {
        super(key, DropdownSetting.fromEnum(ConfigSounds.NONE));
    }
}
