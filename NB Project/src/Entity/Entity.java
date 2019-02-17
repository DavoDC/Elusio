package Entity;

import Components.Structures.Player;
import Components.Structures.TiledMapPlus;
import org.newdawn.slick.Image;

/**
 * Models entities
 *
 * @author David Charkey
 */
public class Entity
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
     * @param desc
     * @param col
     * @param row
     */
    public Entity(String name, int col, int row)
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
     *
     * @return Row, Column
     */
    public int[] getGridLoc()
    {
        int[] pos = new int[2];
        pos[0] = row;
        pos[1] = col;
        return pos;
    }

    /**
     * Returns true if an item is under the player
     *
     * @param player
     * @return
     */
    public boolean isEntityUnder(Player player)
    {
        // Player position
        int xPlayer = player.getX();
        int yPlayer = player.getY();

        // Adjust player position
        yPlayer += 32;

        // Find tile of player
        int playerCol = TiledMapPlus.convertXtoCol(xPlayer);
        int playerRow = TiledMapPlus.convertYtoRow(yPlayer);

        // Check condition
        boolean isUnder = (playerCol == col) && (playerRow == row);

        // Return result
        return isUnder;
    }

}
