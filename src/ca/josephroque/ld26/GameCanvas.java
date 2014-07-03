package ca.josephroque.ld26;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import ca.josephroque.ld26.menu.GameOverMenu;
import ca.josephroque.ld26.menu.MainMenu;
import ca.josephroque.ld26.menu.Menu;
import ca.josephroque.ld26.menu.WinMenu;

public class GameCanvas extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private Container f;
	private BufferStrategy bs;
	private InputHandler input;
	private Menu menu;
	private Engine engine;
	private Cursor emptyCursor;
	
	private boolean running = false;
	private Thread gameThread;
	private String fpsString = "";
	private boolean showFPS = false;
	
	private void tick() {
		if (!input.focused) return;
		
		if (input.escape && f instanceof JFrame) System.exit(0);
		
		if (menu != null) {
			menu.tick(input);
		} else {
			engine.tick();
		}
	}
	
	private void render() {
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		
		if (menu != null) {
			menu.render(g);
		} else {
			engine.render(g);
		}
		
		if (!input.focused) {
			g.setColor(new Color(0f,0f,0f,0.3f));
			g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
			g.setColor(new Color(0f,0f,0f,0.7f));
			g.fillRect(0,250,Constants.WIDTH,100);
			if (System.currentTimeMillis() / 1000 % 2 == 0) {
				g.setColor(Color.white);
				g.setFont(g.getFont().deriveFont(48f));
				String s = "Click to Focus!";
				g.drawString(s, Constants.WIDTH / 2 - g.getFontMetrics().stringWidth(s) / 2, Constants.HEIGHT / 2 + g.getFontMetrics().getHeight() / 4);
			}
		}
		
		g.dispose();
		bs.show();
	}
	
	public void startGame() {
		engine = new Engine(this, input);
		menu = null;
	}
	
	public void endGame() {
		menu = new GameOverMenu(this);
	}
	
	public void winGame() {
		menu = new WinMenu(this);
	}
	
	synchronized void start() {
		createBuffers(2);
		input = new InputHandler(this);
		menu = new MainMenu(this);
		
		if (!running || gameThread == null) {
			running = true;
			gameThread = new Thread(this);
			gameThread.start();
		}
	}
	
	synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			gameThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createBuffers(int buffers) {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(buffers);
			createBuffers(buffers);
		}
	}
	
	public void run() {
		long time = System.nanoTime();
		double ns = 1000000000 / Constants.TICKS_PER_SECOND;
		double un = 0.0;
		int frames = 0;
		int ticks = 0;
		long timer = System.currentTimeMillis();
		
		requestFocus();
		
		while (running) {
			long now = System.nanoTime();
			un += (now - time) / ns;
			time = now;
			while(un > 1) {
				tick();
				ticks++;
				un -= 1;
			}
			
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fpsString = "FPS: " + frames + " TICKS: " + ticks;
				frames = 0;
				ticks = 0;
				
				if (showFPS) System.out.println(fpsString);
			}
		}
	}

	public GameCanvas(Container f) {
		this.f = f;
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		setFocusable(true);
		
		emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "empty");
		if (f instanceof JFrame) setCursor(emptyCursor);
	}
	
	public Container getFrame() {return f;}
}
