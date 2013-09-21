package net.miscjunk.aamp.server.java;

import net.miscjunk.aamp.common.AppListener;
import net.miscjunk.aamp.common.PlayerHandler;

public class JavaTestAppListener extends AppListener {

	public JavaTestAppListener(PlayerHandler handler) {
		super(handler);
	}

	@Override
	public void start() {
		handler.onControlEvent("play");
		System.out.println("Playing..");
		try {Thread.sleep(5000);}catch(Exception e) {}
		System.out.println("Pausing..");
		handler.onControlEvent("pause");	
		try {Thread.sleep(3000);}catch(Exception e) {}
		System.out.println("Playing..");
		handler.onControlEvent("play");
	}

}
