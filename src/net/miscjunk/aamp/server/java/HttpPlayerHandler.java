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
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        baseRequest.setHandled(true);
        String[] path = target.split("/");
        switch (request.getMethod()) {
        case "GET":
            System.out.println("GET " + target);
            switch (path[1]) {
            case "songs":
                if (path.length < 3 || path[2].equals("")) {
                    Playlist allSongs = player.getAllSongs();
                    String json = gson.toJson(allSongs.getSongs());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print(json);
                }
                break;
            case "playlists":
                if (path.length < 3 || path[2].equals("")) {
                    String json = gson.toJson(player.getPlaylists());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print(json);
                }
                break;
            case "queue":
                if (path.length < 3 || path[2].equals("")) {
                    String json = gson.toJson(player.getQueue());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print(json);
                }
                break;
            case "providers":
                if (path.length < 3 || path[2].equals("")) {
                    String json = gson.toJson(player.getProviders());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print(json);
                }
                break;
            }
            break;
        case "POST":
            System.out.println("POST " + target);
            switch (path[1]) {
            case "control":
                if (path.length < 3 || path[2].equals("")) {
                    BufferedReader bodyReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                    String action = bodyReader.readLine();
                    bodyReader.close();
                    System.out.println("With action " + action);
                    boolean success = false;
                    if("play".equals(action)) {
                    	success = player.play();
                    } else if("pause".equals(action)) {
                    	success = player.pause();
                    } else if(action.startsWith("volume=")) {
                    	success = player.setVolume(Double.parseDouble(action.replace("volume=", "")));
                    } else if(action.startsWith("seek=")) {
                    	success = player.seek(Double.parseDouble(action.replace("seek=", "")));
                    }else if("next".equals(action)) {
                    	success = player.next();
                	}else if ("prev".equals(action)) {
                		success = player.prev();
        			}else if(action.startsWith("skipTo=")) {
        				String id = action.replace("skipTo=", "");
        				success = player.skipTo(id);
        			}
                    if(success) {
                    	response.getWriter().println("SET:" + action);
                    }else {response.getWriter().println("FAILED: SET:" + action);}
                    response.setStatus(HttpServletResponse.SC_OK);
                }
                break;
            }
            break;
        case "PUT":
            System.out.println("PUT " + target);
            break;
        case "DELETE":
            System.out.println("DELETE " + target);
            break;
        }
    }
}
