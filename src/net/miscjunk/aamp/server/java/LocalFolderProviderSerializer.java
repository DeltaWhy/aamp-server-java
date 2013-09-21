package net.miscjunk.aamp.server.java;

import com.google.gson.*;
import java.lang.reflect.Type;

public class LocalFolderProviderSerializer implements
        JsonSerializer<LocalFolderProvider> {
    @Override
    public JsonElement serialize(LocalFolderProvider src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject el = new JsonObject();
        el.addProperty("id", src.getId());
        el.addProperty("type", src.getClass().getCanonicalName());
        el.addProperty("rootDir", src.rootDir);
        return el;
    }
}
