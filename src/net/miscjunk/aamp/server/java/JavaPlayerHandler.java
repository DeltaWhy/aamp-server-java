package net.miscjunk.aamp.server.java;

import net.miscjunk.aamp.common.MusicQueue;
import net.miscjunk.aamp.common.PlayerHandler;
import net.miscjunk.aamp.common.Song;
import net.miscjunk.aamp.common.SongAdapter;

public class JavaPlayerHandler implements PlayerHandler{
	private MusicQueue queue;
	
	public JavaPlayerHandler() {
		queue = new MusicQueue(new SongAdapter(new Song("ACDC Thunderstruck"), new JavaFXLocalFilePlayStrategy("songs/thunderstruck", "mp3")));
	}
	
	@Override
	public void onControlEvent(String event) {
		if("play".equalsIgnoreCase(event)) {
			queue.play();
		}
		if("pause".equalsIgnoreCase(event)) {
			queue.pause();
		}
		
	}
	
	

}
