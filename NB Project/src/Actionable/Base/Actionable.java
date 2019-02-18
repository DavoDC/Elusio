package Actionable.Base;

import Components.Structures.Player;
import Components.Structures.TiledMapPlus;
import org.newdawn.slick.Image;

/**
 * Models entities
 *
 * @author David Charkey
 */
public class Actionable
{

    // Info
    private String name;

    // Position
    private int col;
    private int row;
    private int xPos;
    private int yPos;

    // Tile image
    private Image tile;

    /**
     * Create an item
     *
     * @param name
     * @param col
     * @param row
     */
    public Actionable(String name, int col, int row)
    {
        // Input assignments
        this.name = name;

        // Save tile grid pos
        this.col = col;
        this.row = row;
        
        // Calculate co-ordinate pos
        xPos = col * TiledMapPlus.tileSize;
        yPos = row * TiledMapPlus.tileSize;
    }
    
    public String getName()
    {
        return name;
    }

    /**
     * Add an image representing the item
     *
     * @param img
     */
    public void addTileImage(Image img)
    {
        tile = img;
    }

    /**
     * Get grid location of item
     * @return "col-row"
     */
    public String getLocString()
    {
        String loc = col + "-" + row;
        return loc;
    }
    
    /**
     * Get tile grid col
     * @return 
     */
    public int getCol()
    {
        return col;
    }
    
    /**
     * Get tile grid row
     * @return 
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Get X pos
     * @return 
     */
    public int getX()
    {
        return xPos;
    }

    public int getY()
    {
        return yPos;
    }
            

///better suited for another location = e.g. entityprocessing method = check before auto
    /**
     * Returns true if an item is under the player
     *
     * @param player
     * @return
     */
//    public boolean isEntityUnder(Player player)
//    {
//        // Player position
//        int xPlayer = player.getX();
//        int yPlayer = player.getY();
//
//        // Adjust player position
//        yPlayer += 32;
//
//        // Find tile of player
//        int playerCol = TiledMapPlus.convertXtoCol(xPlayer);
//        int playerRow = TiledMapPlus.convertYtoRow(yPlayer);
//
//        // Check condition
//        boolean isUnder = (playerCol == col) && (playerRow == row);
//
//        // Return result
//        return isUnder;
//    }

}
