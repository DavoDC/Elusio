
package code.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import code.Manager.Content;
import code.Manager.JukeBox;
import code.TileMap.TileMap;

/**
 * Represents the player, which is an Entity
 * This fully utilizes the entity class to enable movement
 * 
 * Contains all the gameplay associated with the Player.
 * @author David Charkey
 */
public class Player extends Entity {
	
	// Sprites
	private final BufferedImage[] downSprites;
	private final BufferedImage[] leftSprites;
	private final BufferedImage[] rightSprites;
	private final BufferedImage[] upSprites;
	
	// Sprite numbers
	private final int DOWN = 0;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	
	// Game variables
	private int numDiamonds;
	private int totalDiamonds;
	private boolean hasBoat;
	private boolean hasAxe;
	private boolean onWater;
	private long ticks;
	
    /**
     *
     * @param tm
     */
    public Player(TileMap tm) {
		
		super(tm);
		
		width = 16;
		height = 16;
		cwidth = 12;
		cheight = 12;
		
		moveSpeed = 2;
		
		numDiamonds = 0;
		
		downSprites = Content.PLAYER[0];
		leftSprites = Content.PLAYER[1];
		rightSprites = Content.PLAYER[2];
		upSprites = Content.PLAYER[3];

		animation.setFrames(downSprites);
		animation.setDelay(15);
		
                moveSpeed = 3;
	}
    
    
	/**
         * Alter animation
         * @param i
         * @param bi
         * @param d 
         */
	private void setAnimation(int i, BufferedImage[] bi, int d) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(d);
	}
	

    public void collectedDiamond() { numDiamonds++; }


    public int numDiamonds() { return numDiamonds; }


    public int getTotalDiamonds() { return totalDiamonds; }


    public void setTotalDiamonds(int i) { totalDiamonds = i; }
	

    public void gotBoat() { hasBoat = true; tileMap.replace(22, 4); }
    public void gotAxe() { hasAxe = true; }


    public boolean hasBoat() { return hasBoat; }
    public boolean hasAxe() { return hasAxe; }

    /**
     * Used to update time.
     * @return
     */
    public long getTicks() { return ticks; }
	
    // Keyboard input. Moves the player.
    @Override
    public void setDown() {super.setDown(); }
    @Override
    public void setLeft() { super.setLeft(); }
    @Override
    public void setRight() { super.setRight(); }
    @Override
    public void setUp() { super.setUp(); }

    /**
     * If Player has the axe, dead trees in front of the Player will be removed
     */
	public void setAction() {
		if(hasAxe) {
			if(currentAnimation == UP && tileMap.getIndex(rowTile - 1, colTile) == 21) {
				tileMap.setTile(rowTile - 1, colTile, 1);
				JukeBox.play("tilechange");
			}
			if(currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 21) {
				tileMap.setTile(rowTile + 1, colTile, 1);
				JukeBox.play("tilechange");
			}
			if(currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 21) {
				tileMap.setTile(rowTile, colTile - 1, 1);
				JukeBox.play("tilechange");
			}
			if(currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 21) {
				tileMap.setTile(rowTile, colTile + 1, 1);
				JukeBox.play("tilechange");
			}
		}
	}
	
        /**
         * Update sprites
         * 
         */
        @Override
	public void update() {
		
		ticks++;

		// set animation
		if(down) { setAnimation(DOWN, downSprites, 10); }
                else if(left) { setAnimation(LEFT, leftSprites, 10); }
                else if(right) { setAnimation(RIGHT, rightSprites, 10); }
                else if(up) { setAnimation(UP, upSprites, 10);}
		
		// update position
		super.update();
		
	}
	
	/**
         * Draw method
         * @param g 
         */
        @Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}