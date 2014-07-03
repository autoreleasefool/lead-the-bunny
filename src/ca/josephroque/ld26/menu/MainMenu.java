package ca.josephroque.ld26.menu;

import java.awt.Color;
import java.awt.Graphics;

import ca.josephroque.ld26.Constants;
import ca.josephroque.ld26.GameCanvas;
import ca.josephroque.ld26.InputHandler;
import ca.josephroque.ld26.Resources;

public class MainMenu extends Menu {
	
	private String[] options = {"play - press enter"};
	private int selected = 0;
	private int delay = 0;
	private int fading = -1;
	
	public MainMenu(GameCanvas gc) {
		super(gc);
	}

	public void tick(InputHandler input) {
		if (fading > 0) {
			if (--fading == 0) {
				gc.startGame();
			}
			return;
		}
		
		if (delay > 0) {
			delay--;
			return;
		}
		
		if (input.w || input.up) {selected--; delay = 1; }
		if (input.s || input.down) {selected++; delay = 1; }
		
		if (selected == options.length) selected = 0;
		else if (selected == -1) selected = options.length - 1;
		
		if (input.enter) {
			switch(selected) {
				case 0: fading = 45; break;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Resources.title, Constants.WIDTH / 2 - Resources.title.getWidth(null) / 2, Constants.HEIGHT / 2 - Resources.title.getHeight(null) / 2 - 100, null);
		g.setFont(g.getFont().deriveFont(48f));
		for (int i = 0; i<options.length; i++) {
			g.setColor(i == selected ? Color.lightGray:Color.gray);
			g.drawString(options[i], Constants.WIDTH / 2 - g.getFontMetrics().stringWidth(options[i]) / 2, 300 + 60 * i);
		}
		
		if (fading >= 0) {
			g.setColor(new Color(1f,1f,1f,(-fading + 45) / 45f));
			g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		}
	}
}
