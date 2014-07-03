package ca.josephroque.ld26.levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ca.josephroque.ld26.Constants;
import ca.josephroque.ld26.Engine;
import ca.josephroque.ld26.Resources;
import ca.josephroque.ld26.entity.Cannon;
import ca.josephroque.ld26.entity.Doorway;
import ca.josephroque.ld26.entity.Entity;
import ca.josephroque.ld26.entity.Fire;
import ca.josephroque.ld26.entity.Saw;
import ca.josephroque.ld26.entity.Sign;
import ca.josephroque.ld26.entity.Spikes;

public class Level {
	
	private Engine engine;
	private BufferedImage map;
	private boolean[][] layout;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public void tick() {
		for (int i = 0; i<entities.size(); i++)
			entities.get(i).tick();
	}
	
	public void render(Graphics g) {
		g.drawImage(map.getSubimage(engine.getXOffset(),engine.getYOffset(),Constants.WIDTH,Constants.HEIGHT), 0, 0, null);
		for (int i = 0; i<entities.size(); i++)
			entities.get(i).render(g);
		for (int i = 0; i<particles.size(); i++)
			particles.get(i).render(g);
	}

	public Level(Engine engine, BufferedImage mini) {
		this.engine = engine;
		this.map = createMap(mini);
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		entities.remove(e);
	}
	
	public int getWidth(){return map.getWidth();}
	public int getHeight(){return map.getHeight();}
	
	private BufferedImage createMap(BufferedImage mini) {
		BufferedImage bi = new BufferedImage(mini.getWidth() * 32, mini.getHeight() * 32, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.setColor(new Color(249,249,249));
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		layout = new boolean[mini.getWidth()][mini.getHeight()];
		
		for (int x = 0; x<mini.getWidth(); x++) {
			for (int y = 0; y<mini.getHeight(); y++) {
				int col = mini.getRGB(x, y);
				switch (col) {
					case Constants.TILE_BLANK:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = true;
						break;
					case Constants.TILE_WALL:
						g.drawImage(Resources.sprites.getSubimage(0,0,32,32),x*32,y*32,null);
						layout[x][y] = false;
						break;
					case Constants.TILE_SPIKES:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = true;
						entities.add(new Spikes(engine,x*32,y*32,31));
						break;
					case Constants.TILE_SPIKES_ROOF:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = true;
						entities.add(new Spikes(engine,x*32,y*32,32));
						break;
					case Constants.TILE_SPIKES_WALL_LEFT:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = true;
						entities.add(new Spikes(engine,x*32,y*32,33));
						break;
					case Constants.TILE_SPIKES_WALL_RIGHT:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = true;
						entities.add(new Spikes(engine,x*32,y*32,34));
						break;
					case Constants.TILE_CANNON:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = false;
						entities.add(new Cannon(engine,x*32,y*32,11,45));
						break;
					case Constants.TILE_CANNON_ROOF:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = false;
						entities.add(new Cannon(engine,x*32,y*32,10,45));
						break;
					case Constants.TILE_CANNON_WALL_LEFT:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = false;
						entities.add(new Cannon(engine,x*32,y*32,23,45));
						break;
					case Constants.TILE_CANNON_WALL_RIGHT:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = false;
						entities.add(new Cannon(engine,x*32,y*32,22,45));
						break;
					case Constants.TILE_SAW:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = true;
						entities.add(new Saw(engine,x*32+2,y*32+17,0));
						break;
					case Constants.TILE_SAW_ROOF:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = true;
						entities.add(new Saw(engine,x*32+2,y*32,1));
						break;
					case Constants.TILE_FIRE:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = true;
						entities.add(new Fire(engine,x*32,y*32+20));
						break;
					case Constants.TILE_EXIT:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						entities.add(new Doorway(engine,x*32,y*32));
						layout[x][y] = true;
						break;
					case Constants.TILE_SIGN:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = true;
						entities.add(new Sign(engine,x*32,y*32));
						break;
					case Constants.TILE_BUNNY:
						g.drawImage(Resources.sprites.getSubimage(0,32,32,32),x*32,y*32,null);
						layout[x][y] = true;
						engine.getBunny().setPosition(x*32,y*32+3);
						break;
				}
			}
		}
		
		g.dispose();
		return bi;
	}
	
	public void tickParticles() {
		for (int i = 0; i<particles.size();i++)
			particles.get(i).tick();
	}
	
	void removeParticle(Particle p) {
		particles.remove(p);
	}
	
	public void createParticles(BufferedImage bi) {
		for (int x = 0; x<bi.getWidth(); x++) {
			for (int y = 0; y<bi.getHeight(); y++) {
				if (bi.getRGB(x, y) != 0x00000000 && Math.random() < 0.5) {
					int col = bi.getRGB(x,y);
					if (col == 0xff000000) col = 0xffff0000;
					particles.add(new Particle(engine,engine.getBunny().getX()+x,engine.getBunny().getY()+y,(float)(Math.random()*6-3),col,1));
				}
			}
		}
	}
	
	public boolean canMove(int x, int y) {return layout[x][y];}
}
