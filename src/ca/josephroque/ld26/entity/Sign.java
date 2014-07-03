package ca.josephroque.ld26.entity;

import ca.josephroque.ld26.Engine;

public class Sign extends Entity{
	
	private boolean intersecting = false;
	
	public Sign(Engine engine, int x, int y) {
		super(engine, x, y, 32, 32, 9);
	}
	
	public void tick() {
		if (getRectangle().intersects(engine.getBunny().getRectangle()) && !intersecting) {
			intersecting = true;
			engine.getBunny().setShowSign(true);
		} else if (!getRectangle().intersects(engine.getBunny().getRectangle()) && intersecting) {
			intersecting = false;
			engine.getBunny().setShowSign(false);
		}
	}
}
