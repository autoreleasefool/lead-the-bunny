package ca.josephroque.ld26.menu;

import java.awt.Graphics;

import ca.josephroque.ld26.GameCanvas;
import ca.josephroque.ld26.InputHandler;

public abstract class Menu {
	
	GameCanvas gc;
	
	
	public Menu(GameCanvas gc) {
		this.gc = gc;
	}
	
	public abstract void tick(InputHandler input);
	public abstract void render(Graphics g);
}
