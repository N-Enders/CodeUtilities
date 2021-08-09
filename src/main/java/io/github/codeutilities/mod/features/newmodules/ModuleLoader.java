package io.github.codeutilities.mod.features.newmodules;

import com.google.gson.JsonObject;
import io.github.codeutilities.CodeUtilities;
import io.github.codeutilities.mod.features.modules.actions.json.ModuleJson;
import io.github.codeutilities.mod.features.modules.tasks.Task;
import io.github.codeutilities.mod.features.modules.translations.Translation;
import io.github.codeutilities.mod.features.modules.triggers.Trigger;
import io.github.codeutilities.sys.file.FileUtil;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ModuleLoader {

    private static final File MODULES_FOLDER = FabricLoader.getInstance().getGameDir().resolve("CodeUtilities/Modules").toFile();
    private static ModuleLoader INSTANCE;

    private static final HashMap<String, Module> MODULES = new HashMap<>();

    public ModuleLoader() {
        INSTANCE = this;
    }

    public static ModuleLoader getInstance() {
        return INSTANCE == null ? new ModuleLoader() : INSTANCE;
    }

    public void loadModules() {
        if (!MODULES_FOLDER.exists()) {
            MODULES_FOLDER.mkdir();
        }

        CodeUtilities.log("Loading modules...");

        File[] moduleFiles = MODULES_FOLDER.listFiles();
        if (moduleFiles != null) {
            CodeUtilities.log(String.format("%s module%s found.", moduleFiles.length, moduleFiles.length == 1 ? "" : "s"));

            int successfulLoads = 0;
            for (File file : moduleFiles) {
                // Load file
                JsonObject json;
                try {
                    json = FileUtil.readJson(file);
                } catch (IOException e) {
                    CodeUtilities.log(Level.ERROR, "Error while loading module '" + file.getName() + "'. Stack Trace:");
                    e.printStackTrace();
                    continue;
                }

                Module module = new Module(json);
                MODULES.put(module.getId(), module);

                successfulLoads++;
            }
            CodeUtilities.log(String.format(
                    "Successfully loaded %s (%1$s/%s) module%s!", successfulLoads, moduleFiles.length, successfulLoads == 1 ? "" : "s"
            ));
        } else {
            CodeUtilities.log("No modules found.");
        }

    }

    public HashMap<String, Module> getModules() {
        return MODULES;
    }

}
