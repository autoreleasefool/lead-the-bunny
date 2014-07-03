package ca.josephroque.ld26;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Resources {
	
	public static final Image hole = loadImage("hole.png");
	public static final Image title = loadImage("title.png");
	public static final BufferedImage hud = loadBufferedImage("hud.png");
	public static final BufferedImage sprites = loadBufferedImage("sprites.png");
	public static final BufferedImage[] levelMap = loadBufferedImageArray("levels/level",Constants.TOTAL_LEVELS);
	
	//public static final BufferedImage bunnyTiled = loadBufferedImage("bunny.png");
	
	public static Image loadImage (String path) {
		Image img = null;
		try {
			img = ImageIO.read(Resources.class.getResource("/ca/josephroque/ld26/_images/" + path));
		} catch (Exception e) {e.printStackTrace(); }
		
		return img;
	}
	
	public static BufferedImage[] loadBufferedImageArray(String path, int num) {
		BufferedImage[] array = new BufferedImage[num];
		for (int i = 1; i<=num; i++)
			array[i - 1] = (BufferedImage) loadImage(path + i + ".png");
		return array;
	}
	
	public static BufferedImage loadBufferedImage(String path) {
		return (BufferedImage) loadImage(path);
	}
}
