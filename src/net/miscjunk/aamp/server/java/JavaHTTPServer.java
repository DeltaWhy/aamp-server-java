package net.miscjunk.aamp.server.java;

import org.eclipse.jetty.server.Server;

import net.miscjunk.aamp.common.AppListener;
import net.miscjunk.aamp.common.PlayerHandler;

public class JavaHTTPServer extends AppListener {

	public JavaHTTPServer(PlayerHandler handler) {
		super(handler);
	}

	@Override
	public void start() {
		Server server = new Server(13531);
		server.setHandler(new HttpPlayerHandler(handler));
		try {
			server.start();
			while( server.getAttributeNames().hasMoreElements()) {
				String attr = (String) server.getAttributeNames().nextElement();
				System.out.println(attr + " is " + server.getAttribute(attr));
			}
			System.out.println("Listening on " + 13531);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
