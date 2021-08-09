package io.github.codeutilities.sys.file;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static String readFile(String path) throws IOException {
        return readFile(path, Charset.defaultCharset());
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static JsonObject readJson(File file) throws IOException {
        return readJson(file.toPath());
    }

    public static JsonObject readJson(Path path) throws IOException {
        return readJson(path.toString());
    }

    public static JsonObject readJson(String path) throws IOException {
        return new JsonParser().parse(readFile(path)).getAsJsonObject();
    }
}
