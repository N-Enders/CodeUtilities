package io.github.codeutilities.mod.features.newmodules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.codeutilities.mod.config.structure.ConfigSetting;
import io.github.codeutilities.mod.features.newmodules.task.Task;
import io.github.codeutilities.mod.features.newmodules.task.TaskHandler;

import java.util.ArrayList;
import java.util.List;

public class Module {

    // General
    private final JsonObject json;

    // Meta
    private final String id;
    private final String author;
    private final String version;

    // Config
    private final ConfigOption[] config;

    // Translations

    // Triggers

    // Tasks
    private final Task[] tasks;

    // Register module
    public Module(JsonObject json) {
        this.json = json;

        // Sub-objects
        JsonObject meta = json.getAsJsonObject("meta");
        JsonArray config = json.getAsJsonArray("config");
        JsonObject translations = json.getAsJsonObject("translations");
        JsonArray triggers = json.getAsJsonArray("triggers");
        JsonArray tasks = json.getAsJsonArray("tasks");

        // Meta
        this.id = meta.get("id").getAsString();
        this.author = meta.get("author").getAsString();
        this.version = meta.get("version").getAsString();

        // Config
        this.config = ConfigOption.of(config);

        // Translations


        // Triggers


        // Tasks
        this.tasks = Task.of(tasks);
        TaskHandler.getInstance().register(this.tasks);

    }

    // General getters -------------------------------

    public JsonObject getJson() {
        return json;
    }

    // Meta getters ---------------------------------

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    // Config getters ---------------------------------

    public ConfigOption[] getConfig() {
        return config;
    }

    // Translation getters -----------------------------



    // Trigger getters ---------------------------------



    // Task getters -------------------------------------

    public Task[] getTasks() {
        return tasks;
    }

    // --------------------------------------------------

    //TODO: convert to java record in 1.17 (java 16)
    public static class ConfigOption {

        private final String key;
        private final ConfigSetting<?> setting;

        public ConfigOption(String key, ConfigSetting<?> setting) {
            this.key = key;
            this.setting = setting;
        }

        public String getKey() {
            return key;
        }

        public ConfigSetting<?> getSetting() {
            return setting;
        }

        public static ConfigOption[] of(JsonArray json) {
            List<ConfigOption> result = new ArrayList<>();

            for (JsonElement element : json) {
                JsonObject obj = element.getAsJsonObject();
                String optionKey = obj.get("key").getAsString();

                result.add(
                        new ConfigOption(
                                optionKey,
                                ConfigSetting.of(obj.get("type").getAsString(), optionKey)
                        )
                );
            }

            return result.toArray(new ConfigOption[0]);
        }
    }
}
