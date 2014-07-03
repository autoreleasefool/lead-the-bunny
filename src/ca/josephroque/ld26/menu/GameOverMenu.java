package ca.josephroque.ld26.menu;

import java.awt.Color;
import java.awt.Graphics;

import ca.josephroque.ld26.Constants;
import ca.josephroque.ld26.GameCanvas;
import ca.josephroque.ld26.InputHandler;
import ca.josephroque.ld26.Resources;

public class GameOverMenu extends Menu {

	private int timer = 120;

	public GameOverMenu(GameCanvas gc) {
		super(gc);
	}
	
	public void tick(InputHandler input) {
		if (timer > 0)
			timer--;
		if (input.enter) gc.startGame();
	}
	
	public void render(Graphics g) {
		g.drawImage(Resources.sprites.getSubimage(291,103,88,13),Constants.WIDTH/2-88/2,Constants.HEIGHT/2-13/2,null);
		g.drawImage(Resources.sprites.getSubimage(122,103,158,12),Constants.WIDTH/2-158/2,Constants.HEIGHT/2-12/2+15,null);
		if (timer <= 90)
			g.setColor(new Color(1f,1f,1f,timer/(float)90));
		else
			g.setColor(Color.white);
		g.fillRect(0,0,Constants.WIDTH,Constants.HEIGHT);
	}
}
