package net.miscjunk.aamp.server.java;

import java.lang.reflect.Type;

import com.google.gson.*;

public class LocalFolderProviderDeserializer implements JsonDeserializer<LocalFolderProvider> {
    @Override
    public LocalFolderProvider deserialize(JsonElement src, Type typeOfSrc,
            JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = src.getAsJsonObject();
        String rootDir = jo.getAsJsonPrimitive("rootDir").getAsString();
        return new LocalFolderProvider(rootDir, false);
    }
}
