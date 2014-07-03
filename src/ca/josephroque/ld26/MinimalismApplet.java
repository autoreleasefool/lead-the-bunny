package ca.josephroque.ld26;

import java.applet.Applet;
import java.awt.BorderLayout;

public class MinimalismApplet extends Applet {

	private static final long serialVersionUID = 1L;
	 
	private GameCanvas gc = new GameCanvas(this);
	
	public void init() {
		setLayout(new BorderLayout());
		add(gc,BorderLayout.CENTER);
	}
	
	public void start() {
		gc.start();
	}
	
	public void stop() {
		gc.stop();
	}
}
