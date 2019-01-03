/*
* Models ground tiles in game
* Has two types of tiles: Blocked and non-blocked.
*/ 

package code.TileMap;

import java.awt.image.BufferedImage;

public class Tile 
{
	// Fields
	private BufferedImage image;
	private int type;
	
	// Tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
        /*
        * Constructor requires an image & type number
        */
	public Tile(BufferedImage image, int type) 
        {
            this.image = image;
            this.type = type;
	}
	
        /*
        * Used to retrieve image from the tile object
        */
	public BufferedImage getImage() 
        {
            return image; 
        }
        
        /*
        * Used to retrieve the type of tile
        */
	public int getType() 
        { 
            return type; 
        }
	
}
