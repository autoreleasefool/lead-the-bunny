package ca.josephroque.ld26.entity;

import java.awt.Image;

import ca.josephroque.ld26.Engine;
import ca.josephroque.ld26.Resources;

public class Saw extends Entity {
	
	private int roof;

	public Saw(Engine engine, int x, int y, int roof) {
		super(engine, x, y, 28, 15);
		this.roof = roof;
		this.maxFrames = 7;
	}
	
	Image getImage() {
		return Resources.sprites.getSubimage(32+(frame/2)*width,height*roof,width,height);
	}
	
	public void tick() {
		super.tick();
		if (getRectangle().intersects(engine.getBunny().getRectangle()))
			engine.getBunny().die();
	}
}
