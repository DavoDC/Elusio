package components.structures;

import entity.base.Entity;
import main.Globals;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Contains the map and handles collision processing
 *
 * @author David Charkey
 */
public class Map extends TiledMap
{

    // Stores the 'hittable' status of every tile
    private final boolean[][] blocked;

    // Determines X collision tightness
    private final int Xfactor = 25;
    private final int Xadjuster = 50;

    // Determines Y collision tightnesss
    private final int Yfactor = 60;
    private final int Yadjuster = 50;

    /**
     * Initializes tile collision array, where "True" means blocked
     *
     * @param filepath
     * @throws SlickException
     */
    public Map(String filepath) throws SlickException
    {
        // Load the map file, ensuring that:
        // - Collision layers have a property called blocked, set to true 
        // - All tilesets are embedded
        // - No probabilities have been changed
        // - No tiles have been rotated/flipped
        super(filepath);

        // Get map properties
        int HorizontalTileNo = getHeight();
        int VerticalTileNo = getWidth();
        int layerCount = getLayerCount();

        // Initialise array
        blocked = new boolean[HorizontalTileNo][VerticalTileNo];

        // Fill array with values using blocked layer information
        for (int l = 0; l < layerCount; l++)
        {
            String layerValue = getLayerProperty(l, "blocked", "false");

            if ("true".equals(layerValue))
            {
                for (int c = 0; c < VerticalTileNo; c++)
                {
                    for (int r = 0; r < HorizontalTileNo; r++)
                    {
                        if (getTileId(c, r, l) != 0)
                        {
                            blocked[c][r] = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Converts an X coordinate to tile column form
     *
     * @param xPos
     * @return
     */
    public static int convertXtoCol(float xPos)
    {
        return getGridFromCoord(xPos);
    }

    /**
     * Convert a Y coordinate to tile row form
     *
     * @param yPos
     * @return Column
     */
    public static int convertYtoRow(float yPos)
    {
        return getGridFromCoord(yPos);
    }

    /**
     * Helps to convert coordinates to row/column values
     */
    private static int getGridFromCoord(float coord)
    {
        return ((int) coord / Globals.tileSize);
    }

    /**
     * Returns true if upward movement is allowed
     *
     * @param playerX
     * @param playerY
     * @param relSpeed Speed as function of delta
     * @return True if up movement allowed
     */
    public boolean isUpAllowed(int playerX, int playerY, float relSpeed)
    {
        // Both True = No blocked tiles upwards
        boolean cond1 = canPass(playerX + Xfactor, playerY - relSpeed);
        boolean cond2 = canPass(playerX + Xadjuster, playerY - relSpeed);

        // Map bounds check = Already handled by Player class
        // Negative Y values (outside top of map) are prevented
        // Return result
        return (cond1 && cond2);

    }

    /**
     * Returns true if downward movement is allowed
     *
     * @param playerX
     * @param playerY
     * @param relSpeed Speed as function of delta
     * @return True if down movement allowed
     */
    public boolean isDownAllowed(int playerX, int playerY, float relSpeed)
    {
        // Both true = No blocked tiles down
        float newY = playerY + Yfactor + relSpeed;
        boolean cond1 = canPass(playerX + Xfactor, newY);
        boolean cond2 = canPass(playerX + Xadjuster, newY);

        // True if within map bounds
        int yLimiter = (getHeight() * getTileHeight()) - Globals.tileSize;
        boolean cond3 = ((playerY + relSpeed) < yLimiter);

        return (cond1 && cond2 && cond3);
    }

    /**
     * Returns true if leftward movement is allowed
     *
     * @param playerX
     * @param playerY
     * @param relSpeed Speed as function of delta
     * @return True if left movement allowed
     */
    public boolean isLeftAllowed(int playerX, int playerY, float relSpeed)
    {
        // True = No blocked tiles to the left
        boolean cond1 = canPass(playerX - relSpeed, playerY + Yadjuster);
        boolean cond2 = canPass(playerX - relSpeed, playerY + Yfactor);

        // Map bounds check = Already handled by Player class
        // Negative X values (outside left of map) are prevented
        // Return result
        return (cond1 && cond2);
    }

    /**
     * Returns true if rightward movement is allowed
     *
     * @param playerX
     * @param playerY
     * @param relSpeed Speed as function of delta
     * @return True if right movement allowed
     */
    public boolean isRightAllowed(int playerX, int playerY, float relSpeed)
    {
        // Both True = No blocked tiles to the right
        float newX = playerX + Xfactor + relSpeed;
        boolean cond1 = canPass(newX, playerY + Yfactor);
        boolean cond2 = canPass(newX, playerY + Yadjuster);

        // True if within map bounds 
        int tileSize = Globals.tileSize;
        int xLimiter = (getWidth() * getTileWidth()) - (tileSize - tileSize / 6);
        boolean cond3 = ((playerX + relSpeed) < xLimiter);

        // Return result
        return (cond1 && cond2 && cond3);
    }

    /**
     * Helps to check if a coordinate is on a blocked tile
     *
     * @param x
     * @param y
     * @return
     */
    private boolean canPass(float x, float y)
    {
        try
        {
            int xBlock = convertXtoCol(x);
            int yBlock = convertYtoRow(y);
            return !blocked[xBlock][yBlock];
        }
        catch (Exception e)
        {
            return true;
        }
    }

    /**
     * Unblocks the given entity
     *
     * @param ent The entity to be blocked
     */
    public void unblockEntity(Entity ent)
    {
        // Retrieve entity positions
        String[][] entPos = ent.getGridPosArray();

        // For every position in the entity
        for (String[] row : entPos)
        {
            for (String pos : row)
            {
                // Get a position in OZ
                String[] posPair = pos.split("-");
                int curEcol = Integer.parseInt(posPair[0]);
                int curErow = Integer.parseInt(posPair[1]);

                // Change blocked array
                blocked[curEcol][curErow] = false;

            }
        }
    }

}
