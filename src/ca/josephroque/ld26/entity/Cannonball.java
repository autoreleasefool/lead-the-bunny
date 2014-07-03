package ca.josephroque.ld26.entity;

import ca.josephroque.ld26.Engine;

public class Cannonball extends Entity {
	
	private static final int SPEED = 10;

	public Cannonball (Engine engine, int x, int y, int dx, int dy) {
		super(engine, x, y, 32, 32, 8);
		this.dx = dx*Cannonball.SPEED;
		this.dy = dy*Cannonball.SPEED;
	}
	
	public void tick() {
		x += dx;
		y += dy;
		
		if(!engine.getLevel().canMove(x/32,y/32) || !engine.getLevel().canMove((x+width-1)/32, y/32) || !engine.getLevel().canMove(x/32, (y+height-1)/32) || !engine.getLevel().canMove((x+width-1)/32, (y+height-1)/32))
			destroy();
		
		if (getRectangle().intersects(engine.getBunny().getRectangle()))
			engine.getBunny().die();
	}
	
	private void destroy() {
		engine.getLevel().addEntity(new BrokenCannonball(engine,x,y));
		engine.getLevel().removeEntity(this);
	}
}
