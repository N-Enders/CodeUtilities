package io.github.codeutilities.commands;

import com.mojang.brigadier.CommandDispatcher;
import io.github.codeutilities.feature.Feature;
import net.minecraft.commands.SharedSuggestionProvider;

public interface Command extends Feature {

    void register(CommandDispatcher<SharedSuggestionProvider> dispatcher);

}