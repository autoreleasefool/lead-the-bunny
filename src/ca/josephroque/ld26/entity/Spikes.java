package ca.josephroque.ld26.entity;

import ca.josephroque.ld26.Engine;

public class Spikes extends Entity {

	public Spikes(Engine engine, int x, int y, int id) {
		super(engine,x,y,32,32,id);
	}
	
	public void tick() {
		if (getRectangle().intersects(engine.getBunny().getRectangle()))
			engine.getBunny().die();
	}
}
