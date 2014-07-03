package ca.josephroque.ld26;

import javax.swing.JFrame;

public class MinimalismGame {
	public static void main(String[] args) {
		GameCanvas gc;
		
		JFrame f = new JFrame(Constants.NAME);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.add(gc = new GameCanvas(f));
		f.pack();
		f.setIconImage(Resources.loadImage("icon.png"));
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		gc.start();
	}
}
