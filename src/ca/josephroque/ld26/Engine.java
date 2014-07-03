package ca.josephroque.ld26;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import ca.josephroque.ld26.entity.Bunny;
import ca.josephroque.ld26.levels.Level;

public class Engine {
	
	private static final int LEVEL_CHANGE_TIME = 30;
	
	private GameCanvas gc;
	private BufferedImage light = new BufferedImage(Constants.WIDTH,Constants.HEIGHT,BufferedImage.TYPE_INT_ARGB);
	private InputHandler input;
	private Robot robot;
	
	private int curLevel = 0;
	
	private int ovalRadius = 60;
	private int ovalX, ovalY;
	private int xOffset, yOffset;
	private boolean changingLevel;
	private int levelTimer;
	private int levelStart;
	
	private Bunny bunny;
	private Level level;
	
	void tick() {
		if (levelStart > 0) {
			
			if (gc.getFrame() instanceof JFrame) {
				try {
					if (robot==null)
						robot = new Robot();
					robot.mouseMove(gc.getFrame().getLocationOnScreen().x + bunny.getX() + bunny.getWidth() / 2,gc.getFrame().getLocationOnScreen().y+bunny.getY()+bunny.getHeight()/2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			levelStart--;
			return;
		}
		
		if (changingLevel) {
			if(--levelTimer == 0) {
				levelStart = 89;
				if (++curLevel == Constants.TOTAL_LEVELS) {
					gc.winGame();
					return;
				}
				if (curLevel == 12)curLevel++;
				changingLevel = false;
				level = new Level(this,Resources.levelMap[curLevel]);
				xOffset = 0;
				yOffset = 0;
				bunny.show();
			}
			
			level.tickParticles();
			
			return;
		}
		
		bunny.tick(input);
		level.tick();
		
		if (input.mousePressed) {
			xOffset += input.cX - input.mX;
			yOffset += input.cY - input.mY;
			input.cX = input.mX;
			input.cY = input.mY;
			if (xOffset < 0) xOffset = 0;
			else if (xOffset + Constants.WIDTH> level.getWidth()) xOffset = level.getWidth() - Constants.WIDTH;
			if (yOffset < 0) yOffset = 0;
			else if (yOffset + Constants.HEIGHT> level.getHeight()) yOffset = level.getHeight() - Constants.HEIGHT;
		}
	}
	
	void render(Graphics g) {
		ovalX = input.mX;
		ovalY = input.mY;
		
		level.render(g);
		bunny.render(g);
		renderLight(g);
		renderHUD(g);
		
		if (levelStart > 0) {
			int time = levelStart / 30 + 1;
			switch(time) {
				case 1: g.drawImage(Resources.sprites.getSubimage(0,100,20,72),Constants.WIDTH/2-20/2,Constants.HEIGHT/2-72/2,null); break;
				case 2: g.drawImage(Resources.sprites.getSubimage(0,173,40,72),Constants.WIDTH/2-40/2,Constants.HEIGHT/2-72/2,null); break;
				case 3: g.drawImage(Resources.sprites.getSubimage(0,246,42,74),Constants.WIDTH/2-42/2,Constants.HEIGHT/2-74/2,null); break;
			}
			return;
		}
		
		if (changingLevel) {
			g.setColor(new Color(1f,1f,1f,(-levelTimer + Engine.LEVEL_CHANGE_TIME) / (float)Engine.LEVEL_CHANGE_TIME));
			g.fillRect(0,0,Constants.WIDTH,Constants.HEIGHT);
			return;
		}
		
		if (bunny.isIntersecting())
			g.drawImage(Resources.sprites.getSubimage(188,288,132,32),Constants.WIDTH / 2 - 132 / 2, Constants.HEIGHT / 2 - 32 / 2, null);
		else if (bunny.shouldShowSign())
			renderSign(g);
	}
	
	public void resetLevel() {
		curLevel--;
		nextLevel();
		bunny.hide();
	}
	
	public void nextLevel() {
		changingLevel = true;
		bunny.setIntersecting(false);
		levelTimer = Engine.LEVEL_CHANGE_TIME;
		bunny.resetMovement();
	}
	
	public Rectangle getMouseRect() {
		return new Rectangle(input.mX - ovalRadius - 3, input.mY - ovalRadius - 3, ovalRadius * 2 + 6, ovalRadius * 2 + 6);
	}
	
	private void renderSign(Graphics g) {
		switch(curLevel) {
			case 0: g.drawImage(Resources.sprites.getSubimage(130,254,190,34),Constants.WIDTH/2 - 190/2, Constants.HEIGHT/2-34/2,null); break;
			case 1: g.drawImage(Resources.sprites.getSubimage(130,220,190,34),Constants.WIDTH/2-190/2, Constants.HEIGHT/2-34/2,null); break;
			case 2: g.drawImage(Resources.sprites.getSubimage(130,186,190,34),Constants.WIDTH/2-190/2,Constants.HEIGHT/2-34/2,null); break;
			case 3: g.drawImage(Resources.sprites.getSubimage(188,152,132,34),Constants.WIDTH/2-132/2,Constants.HEIGHT/2-34/2,null); break;
			case 4: g.drawImage(Resources.sprites.getSubimage(130,118,190,34),Constants.WIDTH/2-190/2,Constants.HEIGHT/2-34/2,null); break;
		}
	}
	
	private void renderLight(Graphics g) {
		Graphics2D g2d = light.createGraphics();
		Color transparent = new Color(0, 0, 0, 0);
        g2d.setColor(transparent);
        g2d.setComposite(AlphaComposite.Src);
        g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		g2d.setColor(new Color(0f,0f,0f,0.8f));
		g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setComposite(AlphaComposite.Clear);
		g2d.fillRect(ovalX - ovalRadius, ovalY - ovalRadius, ovalRadius * 2, ovalRadius * 2);
		g2d.setComposite(AlphaComposite.SrcOver);
		g2d.drawImage(Resources.hole, ovalX - ovalRadius, ovalY - ovalRadius, null);
		g2d.dispose();
		g.drawImage(light, 0, 0, null);
	}
	
	private void renderHUD(Graphics g) {
		g.setColor(Color.white);
		g.setFont(g.getFont().deriveFont(48f));
		g.drawString("Level: " + (curLevel+1), Constants.WIDTH - 210, Constants.HEIGHT - 20);
		g.setFont(g.getFont().deriveFont(24f));
		g.drawString("Deaths: " + bunny.getDeaths(), 5, Constants.HEIGHT - 45);
		g.drawImage(Resources.hud.getSubimage(96,0,147,28), 5, Constants.HEIGHT - 39, null);	//STAMINA
		g.drawImage(Resources.hud.getSubimage(0,72,254,40),155,Constants.HEIGHT - 45,null);		//GRAY BAR
		g.drawImage(Resources.hud.getSubimage(2,34,(int)(((-bunny.getBurstTimer()+Bunny.BURST_TIME) / (float)Bunny.BURST_TIME) * 250+1), 36),157,Constants.HEIGHT - 43,null);	//BLUE BAR
	}
	
	public void resetGame() {
		curLevel = 0;
		bunny = new Bunny(this,0,0);
		level = new Level(this,Resources.levelMap[curLevel]);
		levelStart = 89;
	}
	
	public Engine(GameCanvas gc, InputHandler input) {
		this.gc = gc;
		this.input = input;
		bunny = new Bunny(this,0,0);
		level = new Level(this,Resources.levelMap[curLevel]);
		levelStart = 89;
	}
	
	public GameCanvas getCanvas() {return gc;}
	public int getXOffset() {return xOffset;}
	public int getYOffset() {return yOffset;}
	public Bunny getBunny() {return bunny;}
	public Level getLevel() {return level;}
}
