package ca.josephroque.ld26.entity;

import ca.josephroque.ld26.Engine;

public class Cannon extends Entity {

	public Cannon(Engine engine, int x, int y, int id, int maxFrames) {
		super(engine,x,y,32,32,id);
		this.maxFrames = maxFrames;
	}
	
	public void tick() {
		super.tick();
		if (frame == maxFrames) {
			int dx = 0, dy = 0;
			switch(spriteID[0]) {
				case 10: dy = 1; break;
				case 11: dy = -1; break;
				case 22: dx = -1; break;
				case 23: dx = 1; break;
			}
			engine.getLevel().addEntity(new Cannonball(engine,x+dx*32,y+dy*32,dx,dy));
		}
	}
}
