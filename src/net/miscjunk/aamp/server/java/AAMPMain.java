package net.miscjunk.aamp.server.java;

import javafx.application.Application;
import javafx.stage.Stage;
import net.miscjunk.aamp.common.AppListener;
import net.miscjunk.aamp.common.Player;

public class AAMPMain extends Application{
	private Player player;
	private Broadcaster broadcaster;
	
	public static void main(String[] args) throws Exception {
		Application.launch();
	}

	@Override
	public void start(Stage unused) throws Exception {
		player = new Player();
		LocalFolderProvider provider = new LocalFolderProvider("songs", true);
		player.addProvider(provider);
		player.addPlaylist(provider.getAllSongs());
		AppListener listener = new JavaHTTPServer(player);
		listener.start();
		broadcaster = new Broadcaster();
		broadcaster.start();
	}
	
	@Override
	public void stop() throws Exception {
	    broadcaster.stop();
	}
}
