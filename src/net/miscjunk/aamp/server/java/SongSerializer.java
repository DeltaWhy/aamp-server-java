package net.miscjunk.aamp.server.java;

import net.miscjunk.aamp.common.Song;

import com.google.gson.*;
import java.lang.reflect.Type;

public class SongSerializer implements JsonSerializer<Song> {
    @Override
    public JsonElement serialize(Song src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject el = new JsonObject();
        el.addProperty("id", src.getId());
        el.addProperty("track", src.getTrack());
        el.addProperty("title", src.getTitle());
        el.addProperty("album", src.getAlbum());
        el.addProperty("artist", src.getArtist());
        el.add("genres", context.serialize(src.getGenres()));
        return el;
    }
}
