package code.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import code.Manager.Content;
import code.TileMap.TileMap;

/**
 * Represents diamonds, which are a type of entity
 * 
 * Contain a list of tileChanges.
 * These tileChanges are used to modify the tile map upon collection.
 * 
 * @author David Charkey Charkey
 */
public class Diamond extends Entity {
	
        // Sprites of diamond
	private final BufferedImage[] sprites;
	
        // Tile changes
	private ArrayList<int[]> tileChanges;
	
    /**
     *
     * @param tm
     */
    public Diamond(TileMap tm) {
		
		super(tm);
		
		width = 16;
		height = 16;
		cwidth = 12;
		cheight = 12;
		
		sprites = Content.DIAMOND[0];
		animation.setFrames(sprites);
		animation.setDelay(10);
		
		tileChanges = new ArrayList<>();
		
	}
	
    /**
     *
     * @param i
     */
    public void addChange(int[] i) {
		tileChanges.add(i);
	}

    /**
     *
     * @return
     */
    public ArrayList<int[]> getChanges() {
		return tileChanges;
	}
	
    /**
     *
     */
        @Override
    public void update() {
		animation.update();
	}
	
    /**
     *
     * @param g Graphics
     */
        @Override
    public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}
