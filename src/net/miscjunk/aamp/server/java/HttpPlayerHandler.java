package net.miscjunk.aamp.server.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.miscjunk.aamp.common.Player;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HttpPlayerHandler extends AbstractHandler {
	private Player player;
	
	public HttpPlayerHandler(Player appHandler) {
		this.player = appHandler;
	}
	
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        System.out.println("Got target " + target);
        if(target.startsWith("/control/") && "post".equalsIgnoreCase(request.getMethod()) ) {
        	BufferedReader bodyReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        	String action = bodyReader.readLine();
        	bodyReader.close();
        	System.out.println("With action " + action);
        	if("play".equals(action)) {
        		player.play();
        	}else if("pause".equals(action)) {
        		player.pause();
        	}else if(action.startsWith("volume=")) {
        		player.setVolume(Double.parseDouble(action.replace("volume=", "")));
        	}else if(action.startsWith("seek=")) {
        		player.seek(Double.parseDouble(action.replace("seek=", "")));
    		}
        	response.getWriter().println("Running " + target);
        }
        
	}

	
}
