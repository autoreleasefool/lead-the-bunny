package ca.josephroque.ld26.levels;

import java.awt.Color;
import java.awt.Graphics;

import ca.josephroque.ld26.Engine;

class Particle {
	
	private static final float PARTICLE_GRAVITY = 0.75f;

	private float x, y;
	private float dx, dy;
	
	private int col;
	private int size;
	
	private Engine engine;
	
	public Particle(Engine engine, float x, float y, float dx, int col, int size) {
		this.engine = engine;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.col = col;
		this.size = size;
	}
	
	void render(Graphics g) {
		g.setColor(new Color(col,true));
		g.fillRect((int)x - engine.getXOffset(),(int)y - engine.getYOffset(),size,size);
	}
	
	void tick() {
		x += dx;
		y += dy;
		dy += Particle.PARTICLE_GRAVITY;
		
		if (x < 0 || x +size > engine.getLevel().getWidth() || y +size > engine.getLevel().getHeight())
			engine.getLevel().removeParticle(this);
	}
}
