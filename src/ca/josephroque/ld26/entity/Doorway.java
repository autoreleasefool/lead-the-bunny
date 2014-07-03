package ca.josephroque.ld26.entity;

import ca.josephroque.ld26.Engine;

public class Doorway extends Entity {
	
	private boolean intersecting = false;

	public Doorway (Engine engine, int x, int y) {
		super(engine, x, y, 32, 32, 19);
	}
	
	public void tick() {
		if (getRectangle().intersects(engine.getBunny().getRectangle()) && !intersecting) {
			setID(new int[]{20});
			intersecting = true;
			engine.getBunny().setIntersecting(true);
		} else if (!getRectangle().intersects(engine.getBunny().getRectangle()) && intersecting) {
			setID(new int[]{19});
			intersecting = false;
			engine.getBunny().setIntersecting(false);
		}
	}
}
