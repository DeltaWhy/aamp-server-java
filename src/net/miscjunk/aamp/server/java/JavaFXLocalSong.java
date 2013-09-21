package net.miscjunk.aamp.server.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import net.miscjunk.aamp.common.PlayableSong;

public class JavaFXLocalSong implements PlayableSong {
	private String loc;
	private Media media;
	private MediaPlayer player;
	
	public JavaFXLocalSong(String rootDirAbsolute, String filename) {
		this.loc = new File(rootDirAbsolute + "/" + filename).toURI().toASCIIString();
		
		this.media = new Media(loc);
		player = new MediaPlayer(media);
	}
	
	public JavaFXLocalSong(String filename) {
		this(System.getProperty("user.dir") , filename);
	}

	@Override
	public boolean fetch() {
		return true;
	}

	@Override
	public boolean play() {
		player.play();
		return player.getStatus().equals(MediaPlayer.Status.PLAYING);
	}

	@Override
	public boolean pause() {
		player.pause();
		return player.getStatus().equals(MediaPlayer.Status.PAUSED);
	}

	@Override
	public boolean seek(double position) {
		player.seek(Duration.seconds(position));
		return true;
	}

	@Override
	public double getPosition() {
		return player.getBufferProgressTime().toSeconds();
	}

	@Override
	public void setVolume(double volume) {
		player.setVolume(volume);
	}

	@Override
	public void stop() {
		player.stop();
	}

	@Override
	public Runnable getOnFinishedListener() {
		return player.getOnEndOfMedia();
	}

	@Override
	public void setOnFinishedListener(Runnable action) {
		player.setOnEndOfMedia(action);
	}

}
