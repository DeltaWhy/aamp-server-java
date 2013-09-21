package net.miscjunk.aamp.server.java;

import net.miscjunk.aamp.common.PlayStrategy;
import net.miscjunk.aamp.common.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class JavaFXLocalFilePlayStrategy implements PlayStrategy {
	private String loc;
	private Media media;
	private MediaPlayer player;
	
	public JavaFXLocalFilePlayStrategy(String rootDirAbsolute, String filename, String extension) {
		this.loc = "file://" + rootDirAbsolute + "/" + filename + "." + extension;
		this.media = new Media(this.loc);
		player = new MediaPlayer(media);
	}
	
	public JavaFXLocalFilePlayStrategy(String filename, String extension) {
		this(System.getProperty("user.dir") , filename, extension);
	}
	
	public JavaFXLocalFilePlayStrategy(String songName) {
		this(songName, "mp3");
	}
	
	@Override
	public void playSong(Song s) {
		player.play();
	}

	@Override
	public void pause(Song s) {
		player.pause();
	}

	@Override
	public void seek(double time) {
		player.seek(Duration.seconds(time));
	}

	@Override
	public void setVolume(double volume) {
		player.setVolume(volume);
	}

}
