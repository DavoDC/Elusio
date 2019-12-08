package components.structures;

import entity.base.Entity;
import main.Globals;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Contains the map and handles collision processing
 *
 * @author David
 */
public class Map extends TiledMap {

    // Stores the 'hittable' status of every tile
    private final boolean[][] blocked;

    // Determines X collision tightness
    private final int Xfactor = 25;
    private final int Xadjuster = 50;

    // Determines Y collision tightnesss
    private final int Yfactor = 60;
    private final int Yadjuster = 50;

    /**
     * Initialize tile collision array, where "True" means blocked
     *
     * @param filepath
     * @throws SlickException
     */
    public Map(String filepath) throws SlickException {

        // Load a map with the properties below:
        // - Collision layers have a property called blocked, set to true 
        // - All tilesets are embedded
        // - No probabilities have been changed
        // - No tiles have been rotated/flipped
        super(filepath);

        // Get map aspects
        int HorizontalTileNo = getHeight();
        int VerticalTileNo = getWidth();
        int layerCount = getLayerCount();

        // Initialise array
        blocked = new boolean[HorizontalTileNo][VerticalTileNo];

        // For all layers
        for (int l = 0; l < layerCount; l++) {

            // Get blocked status of layer
            String layerValue = getLayerProperty(l, "blocked", "false");

            // If layer is blocked
            if ("true".equals(layerValue)) {

                // Set all tiles of layer to blocked
                for (int c = 0; c < VerticalTileNo; c++) {
                    for (int r = 0; r < HorizontalTileNo; r++) {
                        if (getTileId(c, r, l) != 0) {
                            blocked[c][r] = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Converts X/Y coordinates to column/row values
     */
    private static int convertCoordToGrid(float coord) {
        return ((int) coord / Globals.tileSize);
    }

    /**
     * Converts an X coordinate to tile column form
     *
     * @param xPos
     * @return Column
     */
    public static int convertXtoCol(float xPos) {
        return convertCoordToGrid(xPos);
    }

    /**
     * Convert a Y coordinate to tile row form
     *
     * @param yPos
     * @return Row
     */
    public static int convertYtoRow(float yPos) {
        return convertCoordToGrid(yPos);
    }

    /**
     * Converts column/row values to X/Y coordinates
     */
    private static int convertGridtoCoord(int grid) {
        return (grid * Globals.tileSize);
    }

    /**
     * Convert a tile column to a X coordinate
     *
     * @param col
     * @return X coordinate
     */
    public static int convertColtoX(int col) {
        return convertGridtoCoord(col);
    }

    /**
     * Convert a tile row to a Y coordinate
     *
     * @param row
     * @return Y coordinate
     */
    public static int convertRowtoY(int row) {
        return convertGridtoCoord(row);
    }

    /**
     * Returns true if upward movement is allowed
     *
     * @param playerX
     * @param playerY
     * @param relSpeed Speed as function of delta
     * @return True if up movement allowed
     */
    public boolean isUpAllowed(int playerX, int playerY, float relSpeed) {

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
    public boolean isDownAllowed(int playerX, int playerY, float relSpeed) {

        // Both true = No blocked tiles down
        float newY = playerY + Yfactor + relSpeed;
        boolean cond1 = canPass(playerX + Xfactor, newY);
        boolean cond2 = canPass(playerX + Xadjuster, newY);

        // True if within map bounds
        int yLimiter = (getHeight() * getTileHeight()) - Globals.tileSize;
        boolean cond3 = ((playerY + relSpeed) < yLimiter);

        // Return true if all are true
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
    public boolean isLeftAllowed(int playerX, int playerY, float relSpeed) {

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
    public boolean isRightAllowed(int playerX, int playerY, float relSpeed) {

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
     * Check if a given coordinate is on a blocked tile
     *
     * @param x
     * @param y
     * @return
     */
    private boolean canPass(float x, float y) {
        try {

            // Get array position
            int xBlock = convertXtoCol(x);
            int yBlock = convertYtoRow(y);

            // Return true if coord is not blocked
            return !blocked[xBlock][yBlock];

        } catch (Exception e) {

            // Return true if coord cannot be found
            return true;
        }
    }

    /**
     * Unblocks the given entity
     *
     * @param ent The entity to be blocked
     */
    public void unblockEntity(Entity ent) {

        // Retrieve information about all entity tiles
        String[][] entPos = ent.getGridPosArray();

        // For every tile in the entity
        for (String[] row : entPos) {
            for (String pos : row) {

                // Get tile position
                String[] posPair = pos.split("-");
                int curEcol = Integer.parseInt(posPair[0]);
                int curErow = Integer.parseInt(posPair[1]);

                // Set tile to unblocked
                blocked[curEcol][curErow] = false;
            }
        }
    }

}
