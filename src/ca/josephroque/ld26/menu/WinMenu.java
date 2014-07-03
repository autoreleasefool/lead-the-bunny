package ca.josephroque.ld26.menu;

import java.awt.Color;
import java.awt.Graphics;

import ca.josephroque.ld26.Constants;
import ca.josephroque.ld26.GameCanvas;
import ca.josephroque.ld26.InputHandler;
import ca.josephroque.ld26.Resources;

public class WinMenu extends Menu {
	
	private int timer = 120;

	public WinMenu(GameCanvas gc) {
		super(gc);
	}
	
	public void tick(InputHandler input) {
		if (timer > 0)
			timer--;
	}
	
	public void render(Graphics g) {
		g.drawImage(Resources.sprites.getSubimage(43,153,142,25),Constants.WIDTH/2-142/2,Constants.HEIGHT/2-25/2,null);
		if (timer <= 90)
			g.setColor(new Color(1f,1f,1f,timer/(float)90));
		else
			g.setColor(Color.white);
		g.fillRect(0,0,Constants.WIDTH,Constants.HEIGHT);
	}
}
