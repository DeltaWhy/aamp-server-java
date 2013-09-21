package net.miscjunk.aamp.server.java;

import javafx.application.Application;
import javafx.stage.Stage;
import net.miscjunk.aamp.common.AppListener;
import net.miscjunk.aamp.common.Player;

public class AAMPMain extends Application{
	private Player player;
	
	public static void main(String[] args) throws Exception {
		Application.launch();
	}

	@Override
	public void start(Stage unused) throws Exception {
		player = new Player();
		player.addProvider(new LocalFolderProvider("songs", true));
		AppListener listener = new JavaHTTPServer(player);
		listener.start();
	}
}
