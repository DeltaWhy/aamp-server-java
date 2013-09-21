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
	String rootDir;
	private Playlist playlist;
	private Map<String, String> names;
	
	public LocalFolderProvider(String basedir, boolean relative) {
		if(relative) {
			this.rootDir = System.getProperty("user.dir") + "/" + basedir;
		}else {
			this.rootDir = basedir;
		}
		playlist = new Playlist();
		File thisDir = new File(basedir);
		names = new HashMap<String, String>();
		int i = 0;
		for(File songFile : thisDir.listFiles(new Mp3FileFilter())) {
			System.out.println("Detected song: " + songFile.getName());
			Song added = new Song("" + i, this);
			added.setTitle(songFile.getName()); //TODO - read ID3 tags
			playlist.addSong(added);
			names.put(added.getId(), songFile.getName());
			i++;
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
	    Playlist p = new Playlist();
	    for (Song s : playlist.getSongs()) {
	        if (s.match(query)) p.addSong(s);
	    }
	    return p;
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
