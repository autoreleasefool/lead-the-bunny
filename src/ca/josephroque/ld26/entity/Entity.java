package ca.josephroque.ld26.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import ca.josephroque.ld26.Engine;
import ca.josephroque.ld26.Resources;

public abstract class Entity {
	
	Engine engine;
	int x, y, width, height;
	float dx, dy;
	int frame = 0;
	int[] spriteID;
	int maxFrames;
	
	public Entity(Engine engine, int x, int y, int width, int height, int spriteID) {
		this(engine,x,y,width,height,new int[]{spriteID},30);
	}
	
	public Entity(Engine engine, int x, int y, int width, int height) {
		this(engine,x,y,width,height,null,30);
	}
	
	public Entity (Engine engine, int x, int y, int width, int height, int[] spriteID, int maxFrames) {
		this.engine = engine;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.spriteID = spriteID;
		this.maxFrames = maxFrames;
	}
	
	public void tick() {
		if (++frame > maxFrames)
			frame = 0;
	}
	
	public void render(Graphics g) {
		g.drawImage(getImage(), x - engine.getXOffset(), y - engine.getYOffset(), null);
	}
	
	Image getImage() {
		if (spriteID != null) {
			if (spriteID.length == 1)
				return Resources.sprites.getSubimage((spriteID[0] % 12) * 32, (spriteID[0] / 12) * 32, 32, 32);
			else
				return Resources.sprites.getSubimage((spriteID[frame / spriteID.length] % 12) * 32, (spriteID[frame/spriteID.length] / 12)*32, 32, 32);
		} else
			return null;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
	public void setID(int[] spriteID) {
		this.spriteID = spriteID;
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
}
