package ca.josephroque.ld26;

public interface Constants {
	
	public static final String NAME = "Lead the Bunny";
	public static final int TICKS_PER_SECOND = 30;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public static final int TILE_BLANK = 0xffffffff;
	public static final int TILE_WALL = 0xff000000;
	public static final int TILE_SPIKES = 0xffff0000;
	public static final int TILE_SPIKES_ROOF = 0xffdd0000;
	public static final int TILE_SPIKES_WALL_LEFT = 0xffbb0000;
	public static final int TILE_SPIKES_WALL_RIGHT = 0xff990000;
	public static final int TILE_CANNON = 0xff00ff00;
	public static final int TILE_CANNON_ROOF = 0xff00dd00;
	public static final int TILE_CANNON_WALL_LEFT = 0xff00bb00;
	public static final int TILE_CANNON_WALL_RIGHT = 0xff009900;
	public static final int TILE_SAW = 0xff0000ff;
	public static final int TILE_SAW_ROOF = 0xff0000dd;
	public static final int TILE_FIRE = 0xffffff00;
	public static final int TILE_EXIT = 0xffff00ff;
	public static final int TILE_SIGN = 0xff7f3300;
	public static final int TILE_BUNNY = 0xffff7fed;
	public static final int TILE_LIFE = 0xff0094ff;
	
	public static final int TOTAL_LEVELS = 14;
}
