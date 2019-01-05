package code.TileMap;

import java.awt.image.BufferedImage;

/*
* Models ground tiles in game
* A tile holds an image and type number
* Type 0 = Normal
* Type 1 = Blocked
*/ 
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
