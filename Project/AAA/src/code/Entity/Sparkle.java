// Simple class that plays animation
// once and is removed.

package code.Entity;

import java.awt.Graphics2D;

import code.Manager.Content;
import code.TileMap.TileMap;

/**
 *
 * @author CHARKEYD
 */
public class Sparkle extends Entity {
	
	private boolean remove;
	
    /**
     *
     * @param tm
     */
    public Sparkle(TileMap tm) {
		super(tm);
		animation.setFrames(Content.SPARKLE[0]);
		animation.setDelay(5);
		width = height = 16;
	}
	
    /**
     *
     * @return
     */
    public boolean shouldRemove() {
		return remove;
	}
	
	public void update() {
		animation.update();
		if(animation.hasPlayedOnce()) remove = true;
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}
