package components.modules;

import entity.base.Entity;
import base.Globals;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Contains the map and handles collision processing
 *
 * @author David
 */
public class GameMap extends TiledMap {

    // Stores the 'hittable' status of every tile
    private static boolean[][] blocked;

    /**
     * Initialize tile collision array, where "True" means blocked
     *
     * @param filepath
     * @throws SlickException
     */
    public GameMap(String filepath) throws SlickException {

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

    /**
     * Check if a given coordinate is on a blocked tile
     *
     * @param x
     * @param y
     * @return
     */
    public static boolean canPass(float x, float y) {
        try {

            // Get array position
            int xBlock = convXtoCol(x);
            int yBlock = convYtoRow(y);

            // Return true if coord is not blocked
            return !blocked[xBlock][yBlock];

        } catch (Exception e) {

            // Return true if coord cannot be found
            return true;
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
    public static int convXtoCol(float xPos) {
        return convertCoordToGrid(xPos);
    }

    /**
     * Convert a Y coordinate to tile row form
     *
     * @param yPos
     * @return Row
     */
    public static int convYtoRow(float yPos) {
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
    public static int convColtoX(int col) {
        return convertGridtoCoord(col);
    }

    /**
     * Convert a tile row to a Y coordinate
     *
     * @param row
     * @return Y coordinate
     */
    public static int convRowtoY(int row) {
        return convertGridtoCoord(row);
    }

    /**
     * Return height of map in co-ord units
     *
     * @return
     */
    public int getCoordHeight() {
        return getHeight() * getTileHeight();
    }

    /**
     * Return width of map in co-ord units
     *
     * @return
     */
    public int getCoordWidth() {
        return getWidth() * getTileWidth();
    }

}
