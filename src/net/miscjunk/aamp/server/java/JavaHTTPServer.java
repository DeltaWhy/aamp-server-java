package net.miscjunk.aamp.server.java;

import net.miscjunk.aamp.common.*;

import org.eclipse.jetty.server.Server;

import com.google.gson.GsonBuilder;

public class JavaHTTPServer extends AppListener {

    public JavaHTTPServer(Player handler) {
        super(handler);
    }

    @Override
    public void start() {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Song.class, new SongSerializer());
        gb.registerTypeAdapter(Playlist.class, new PlaylistDeserializer(this.handler));
        gb.registerTypeAdapter(MusicProvider.class, new MusicProviderDeserializer());
        gb.registerTypeAdapter(LocalFolderProvider.class, new LocalFolderProviderSerializer());
        gb.registerTypeAdapter(LocalFolderProvider.class, new LocalFolderProviderDeserializer());
        Server server = new Server(13531);
        server.setHandler(new HttpPlayerHandler(handler, gb.create()));
        try {
            server.start();
            System.out.println("Listening on " + 13531);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
