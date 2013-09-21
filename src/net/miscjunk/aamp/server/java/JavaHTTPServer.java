package net.miscjunk.aamp.server.java;

import net.miscjunk.aamp.common.AppListener;
import net.miscjunk.aamp.common.Player;

import org.eclipse.jetty.server.Server;

public class JavaHTTPServer extends AppListener {

	public JavaHTTPServer(Player handler) {
		super(handler);
	}

	@Override
	public void start() {
		Server server = new Server(13531);
		server.setHandler(new HttpPlayerHandler(handler));
		try {
			server.start();
			System.out.println("Listening on " + 13531);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
