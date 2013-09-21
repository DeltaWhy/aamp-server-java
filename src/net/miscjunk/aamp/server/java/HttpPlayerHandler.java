package net.miscjunk.aamp.server.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.miscjunk.aamp.common.PlayerHandler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HttpPlayerHandler extends AbstractHandler {
	private PlayerHandler appHandler;
	
	public HttpPlayerHandler(PlayerHandler appHandler) {
		this.appHandler = appHandler;
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
        	appHandler.onControlEvent(action);
        	response.getWriter().println("Running " + target);
        }
        
	}

	
}
