package Entity;

import Utility.TiledMapPlus;
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
    private String desc;

    // Position
    private int xPos;
    private int yPos;
    private int row;
    private int col;

    // Tile image
    private Image tile;

    /**
     * Create an item
     *
     * @param name
     * @param desc
     * @param xPos
     * @param yPos
     */
    public Entity(String name, String desc, int xPos, int yPos)
    {
        // Input assignments
        this.name = name;
        this.desc = desc;

        this.xPos = xPos;
        this.yPos = yPos;

        // Calculate row and column
        col = TiledMapPlus.convertXtoCol(xPos);
        row = TiledMapPlus.convertYtoRow(yPos);
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
