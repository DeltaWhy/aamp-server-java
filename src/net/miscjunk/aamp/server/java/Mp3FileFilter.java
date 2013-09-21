package net.miscjunk.aamp.server.java;

import java.io.File;
import java.io.FilenameFilter;

public class Mp3FileFilter implements FilenameFilter {

	@Override
	public boolean accept(File file, String name) {
		return name.endsWith("mp3");
	}

}
