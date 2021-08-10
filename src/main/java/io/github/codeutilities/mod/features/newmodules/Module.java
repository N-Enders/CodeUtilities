package io.github.codeutilities.mod.features.newmodules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.codeutilities.mod.config.structure.ConfigSetting;
import io.github.codeutilities.mod.features.newmodules.task.Task;
import io.github.codeutilities.mod.features.newmodules.task.TaskHandler;
import io.github.codeutilities.mod.features.newmodules.trigger.Trigger;
import io.github.codeutilities.mod.features.newmodules.trigger.TriggerHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private final JsonObject rawTranslations;

    // Triggers
    private final JsonArray rawTriggers;
    private Trigger[] triggers;

    // Tasks
    private final JsonArray rawTasks;
    private Task[] tasks;

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
        this.rawTranslations = translations;

        // Triggers
        this.rawTriggers = triggers;

        // Tasks
        this.rawTasks = tasks;

        // Register
        register();
    }

    public void register() {
        // Register translations TODO

        // Register tasks
        this.tasks = Task.of(rawTasks, this);
        TaskHandler.getInstance().register(tasks);

        // Register triggers
        this.triggers = Trigger.of(this, rawTriggers);
        TriggerHandler.register(triggers);
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

    public String getFullId() {
        return getAuthor() + "." + getId();
    }

    public String getVersion() {
        return version;
    }

    // Config getters ---------------------------------

    public ConfigOption[] getConfig() {
        return config;
    }

    // Translation getters -----------------------------

    public JsonObject getRawTranslations() {
        return rawTranslations;
    }

    // Trigger getters ---------------------------------

    public JsonArray getRawTriggers() {
        return rawTriggers;
    }

    public Trigger[] getTriggers() {
        return triggers;
    }

    // Task getters -------------------------------------

    public JsonArray getRawTasks() {
        return rawTasks;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public Task getTask(String name) {
        return Arrays.stream(tasks)
                .filter(e -> e.getName().equals(name))
                .collect(Collectors.toList())
                .get(0);
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
