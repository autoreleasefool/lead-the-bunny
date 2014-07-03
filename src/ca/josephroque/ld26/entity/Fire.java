package ca.josephroque.ld26.entity;

import java.awt.Image;

import ca.josephroque.ld26.Engine;
import ca.josephroque.ld26.Resources;

public class Fire extends Entity {

	public Fire(Engine engine, int x, int y) {
		super(engine, x, y, 32, 12, new int[]{0,1,2,3,4,5},17);
	}
	
	public void tick() {
		super.tick();
		
		if (getRectangle().intersects(engine.getBunny().getRectangle()))
			engine.getBunny().die();
	}
	
	Image getImage() {
		return Resources.sprites.getSubimage(32+(frame/3)*width, 52,width,height);
	}
}
