package ca.josephroque.ld26.entity;

import ca.josephroque.ld26.Engine;

public class BrokenCannonball extends Entity {
	
	private int time;
	
	public BrokenCannonball(Engine engine, int x, int y) {
		super(engine, x, y, 32, 32, new int[]{58,59}, 3);
		time = 30;
	}
	
	public void tick() {
		super.tick();
		if (--time == 0)
			engine.getLevel().removeEntity(this);
	}
}
