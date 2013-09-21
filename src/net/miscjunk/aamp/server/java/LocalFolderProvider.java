package net.miscjunk.aamp.server.java;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import net.miscjunk.aamp.common.MusicProvider;
import net.miscjunk.aamp.common.PlayableSong;
import net.miscjunk.aamp.common.Playlist;
import net.miscjunk.aamp.common.SimpleQuery;
import net.miscjunk.aamp.common.Song;

public class LocalFolderProvider implements MusicProvider {
	private String rootDir;
	private Playlist playlist;
	private Map<Integer, String> names;
	
	public LocalFolderProvider(String basedir, boolean relative) {
		if(relative) {
			this.rootDir = System.getProperty("user.dir") + relative;
		}
		this.rootDir = basedir;
		playlist = new Playlist();
		File thisDir = new File(basedir);
		names = new HashMap<Integer, String>();
		int i = 0;
		for(File songFile : thisDir.listFiles(new Mp3FileFilter())) {
			playlist.addSong(new Song("" + i, this));
			names.put(i, songFile.getName());
		}
	}
	
	@Override
	public String getId() {
		return "local-" + rootDir;
	}

	@Override
	public Playlist getAllSongs() {
		return playlist;
	}

	@Override
	public Playlist getSongs(SimpleQuery query) {
		return new Playlist();
	}

	@Override
	public boolean update() {
		return false;
	}

	@Override
	public PlayableSong inflate(Song song) {
		return new JavaFXLocalSong(this.rootDir, names.get(song.getId()));
	}

}
