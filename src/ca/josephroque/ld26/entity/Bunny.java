package ca.josephroque.ld26.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import ca.josephroque.ld26.Constants;
import ca.josephroque.ld26.Engine;
import ca.josephroque.ld26.InputHandler;
import ca.josephroque.ld26.Resources;

public class Bunny extends Entity {
	
	public static final int BURST_TIME = 30;
	
	private int burstTimer;
	private int deaths;
	private boolean intersecting = false;
	private boolean showSign = false;
	private boolean jumping = false;
	private int safeBlock;
	private int direction;
	private boolean hide;

	public Bunny(Engine engine, int x, int y) {
		super(engine, x, y, 27, 28);
		this.maxFrames = 23;
	}
	
	public void render(Graphics g) {
		if (!hide)
			g.drawImage(getImage(),x-engine.getXOffset(),y-engine.getYOffset()+1,null);
	}
	
	public void tick(InputHandler input) {
		super.tick();
		
		if (burstTimer > 0) {
			burstTimer--;
			if (burstTimer > 15)
				dx -= 0.5f * ((dx < 0) ? -1:1);
		}
		
		if (!engine.getMouseRect().intersects(getRectangle()) && burstTimer < 15) {
			double l = (Math.sqrt(Math.pow((x + width / 2) - (input.mX+engine.getXOffset()), 2) + Math.pow((y + height / 2) - (input.mY+engine.getYOffset()), 2)) / 400.0);
			if (l > 1) l = 1;
			if ((input.mX+engine.getXOffset()) < x + width / 2) l *= -1;
			if (x +width/2< (input.mX+engine.getXOffset()) - 5 || x +width/2> (input.mX+engine.getXOffset()) + 5)
				dx = (float)(l * 7);
			else
				dx = 0;
		} else if (burstTimer < 15) {
			dx = 0;
		}
		
		if (input.d && burstTimer == 0) {
			burstTimer = Bunny.BURST_TIME;
			dx = 15;
		} else if (input.a && burstTimer == 0) {
			burstTimer = Bunny.BURST_TIME;
			dx = -15;
		}
		
		if (input.s) {
			dx = 0;
			if (burstTimer >= 15) burstTimer = 14;
		}
		
		if (intersecting && input.w) {
			engine.nextLevel();
			return;
		}
		
		if(input.w && !jumping) {
			jumping = true;
			dy = -12;
		}
		
		int newX = (int)(x + dx);
		
		if (engine.getLevel().canMove(newX / 32, y/32) && engine.getLevel().canMove((newX+width) / 32, y/32) && engine.getLevel().canMove(newX / 32, (y+height)/32) && engine.getLevel().canMove((newX+width) / 32, (y+height)/32))
			x += dx;
		
		y += dy;
		
		if (x + width > engine.getLevel().getWidth()) x = engine.getLevel().getWidth() - width;
		else if (x < 0) x = 0;
		if (y + height + 32 > Constants.HEIGHT) die();
		else if (y < 0) y = 0;

		if((!engine.getLevel().canMove(x / 32, (y + height) / 32) || !engine.getLevel().canMove((x+width) / 32, (y + height) / 32)) && dy > 0 && (y + height) / 32 != safeBlock) {
			int hitBlock = (y+height) / 32;
			dy = 0;
			if (jumping) jumping = false;
			while ((y+height) / 32 == hitBlock)
				y-=1;
		} else if ((engine.getLevel().canMove(x/32, (y + height + 1) / 32) && engine.getLevel().canMove((x+width)/32, (y + height + 1) / 32)) || jumping) {
			dy = Math.min(dy + 1, 20);
			safeBlock = (y+height) / 32;
			if (!jumping) {
				jumping=true;
				safeBlock = (y+height+1)/32;
			}
		}
		
		if (dx > 0) direction=0;
		else if (dx < 0) direction=1;
	}
	
	public void hide() {
		hide = true;
	}
	
	public void show() {
		hide = false;
	}
	
	public void die() {
		if (hide) return;

		deaths++;
		engine.getLevel().createParticles((BufferedImage)getImage());
		engine.resetLevel();
	}
	
	public void setIntersecting(boolean intersecting) {
		this.intersecting = intersecting;
	}
	
	public void setShowSign(boolean showSign) {
		this.showSign = showSign;
	}
	
	public void resetMovement() {
		burstTimer = 0;
		dx = 0;
		dy = 0;
	}
	
	public boolean isIntersecting(){return intersecting;}
	public boolean shouldShowSign() {return showSign;}
	public int getDeaths() {return deaths;}
	public int getBurstTimer() {return burstTimer;}
	public void setPosition(int x, int y) {this.x = x; this.y = y;}
	
	Image getImage() {
		if (dx != 0 || dy != 0)
			return Resources.sprites.getSubimage(width*(frame/6) + direction * 108,64,width,height);
		else
			return Resources.sprites.getSubimage(direction*108,64,width,height);
	}
}

