package net.miscjunk.aamp.server.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.miscjunk.aamp.common.Player;
import net.miscjunk.aamp.common.Playlist;
import net.miscjunk.aamp.common.Song;
import net.miscjunk.aamp.common.SongSerializer;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HttpPlayerHandler extends AbstractHandler {
    private Player player;
    private Gson gson;

    public HttpPlayerHandler(Player appHandler) {
        this.player = appHandler;
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Song.class, new SongSerializer());
        gb.registerTypeAdapter(LocalFolderProvider.class, new LocalFolderProviderSerializer());
        gson = gb.create();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        System.out.println("Got target " + target);
        if(target.startsWith("/control") && "post".equalsIgnoreCase(request.getMethod()) ) {
            BufferedReader bodyReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String action = bodyReader.readLine();
            bodyReader.close();
            System.out.println("With action " + action);
            if("play".equals(action)) {

                player.play();
            } else if("pause".equals(action)) {
                player.pause();
            } else if(action.startsWith("volume=")) {
                player.setVolume(Double.parseDouble(action.replace("volume=", "")));
            } else if(action.startsWith("seek=")) {
                player.seek(Double.parseDouble(action.replace("seek=", "")));
            }
            response.getWriter().println("Running " + target);
        } else if (target.startsWith("/songs") && "get".equalsIgnoreCase(request.getMethod())) {
            Playlist allSongs = player.getAllSongs();
            String json = gson.toJson(allSongs.getSongs());
            response.getOutputStream().print(json);
        } else if ((target.equals("/playlists") || target.equals("/playlists/"))
                && "get".equalsIgnoreCase(request.getMethod())) {
            String json = gson.toJson(player.getPlaylists());
            response.getOutputStream().print(json);
        } else if ((target.equals("/queue") || target.equals("/queue/"))
                && "get".equalsIgnoreCase(request.getMethod())) {
            String json = gson.toJson(player.getQueue());
            response.getOutputStream().print(json);
        } else if ((target.equals("/providers") || target.equals("/providers/"))
                && "get".equalsIgnoreCase(request.getMethod())) {
            String json = gson.toJson(player.getProviders());
            response.getOutputStream().print(json);
        }
    }


}
