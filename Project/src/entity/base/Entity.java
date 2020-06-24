package entity.base;

import base.Globals;
import base.Globals;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Models entities on the map
 *
 * @author David
 */
public abstract class Entity {

    // The name
    private final String name;

    // A grid of strings that hold tile position information
    private final String[][] gridPosS;

    // Tiles of entity
    private final Image[][] entImages;

    // Tiles of ground underneath entity
    private final Image[][] undImages;

    /**
     * Create an entity
     *
     * @param name The entity's name
     * @param tlc Top left tile mapCol
     * @param tlr Top left tile mapRow
     * @param w Tiles across
     * @param h Tiles down
     */
    public Entity(String name, int tlc, int tlr, int w, int h) {

        // Save variables
        this.name = name;

        // Initialise arrays
        entImages = new Image[h][w];
        undImages = new Image[h][w];
        gridPosS = new String[h][w];

        // Populate grid position strings
        // For all rows
        for (int r = 0; r < gridPosS.length; r++) {

            // Calculate actual row
            int actualRow = r + tlr;

            // For all columns
            for (int c = 0; c < gridPosS[r].length; c++) {

                // Calculate actual column
                int actualCol = c + tlc;

                // Save string with absolute and relative position info
                gridPosS[r][c] = actualCol + "-" + actualRow + "-" + c + "-" + r;
            }
        }
    }

    /**
     * Initialise the images of the entity and its ground tiles
     *
     * @param entLS
     * @param undLS
     */
    public void initTileImages(String entLS, String undLS) {

        // Get index of layers
        int entLayerIndex = Globals.gameMap.getLayerIndex(entLS);
        int undLayerIndex = Globals.gameMap.getLayerIndex(undLS);

        // If entity indices are invalid
        if (entLayerIndex == -1 || undLayerIndex == -1) {

            // Throw error
            throw new IllegalArgumentException(
                    "LayerNameIssue: " + entLS + " ," + undLS);
        }

        // For all tiles 
        for (String[] gridRow : gridPosS) {
            for (String locString : gridRow) {

                // Extract position info
                String[] locPair = locString.split("-");
                int mapCol = Integer.parseInt(locPair[0]);
                int mapRow = Integer.parseInt(locPair[1]);
                int arrCol = Integer.parseInt(locPair[2]);
                int arrRow = Integer.parseInt(locPair[3]);

                // Get and save entity image
                Image entImage = Globals.gameMap.getTileImage(mapCol, mapRow, entLayerIndex);
                entImages[arrRow][arrCol] = entImage;

                // Get and save ground image
                Image undImage = Globals.gameMap.getTileImage(mapCol, mapRow, undLayerIndex);
                undImages[arrRow][arrCol] = undImage;
            }
        }
    }

    /**
     * Get the name of the entity
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get grid position string parts using array indexing
     *
     * @param c
     * @param r
     * @return
     */
    public String[] getGridPosNums(int c, int r) {
        return ((gridPosS[r][c]).split("-"));
    }

    /**
     * Get array of position info strings
     *
     * @return
     */
    public String[][] getGridPosArray() {
        return gridPosS;
    }

    /**
     * Get entity image(s)
     *
     * @param r Row of array
     * @param c Column of array
     * @return
     */
    public Image getImage(int r, int c) {
        return entImages[r][c];
    }

    /**
     * Draw an entity
     *
     * @param g
     */
    public void hideEntity(Graphics g) {

        // Draw ground images
        drawImageArray(undImages, g);
    }

    /**
     * Draw an entity image array
     *
     * @param imgArr
     */
    private void drawImageArray(Image[][] imgArr, Graphics g) {

        // For all tiles
        for (String[] gridRow : gridPosS) {
            for (String locString : gridRow) {

                // Extract position
                String[] locPair = locString.split("-");
                int mapCol = Integer.parseInt(locPair[0]);
                int mapRow = Integer.parseInt(locPair[1]);
                int xPos = mapCol * Globals.tileSize;
                int yPos = mapRow * Globals.tileSize;
                int arrCol = Integer.parseInt(locPair[2]);
                int arrRow = Integer.parseInt(locPair[3]);

                // Get image underneath
                Image image = imgArr[arrRow][arrCol];

                // If not null, draw it
                if (image != null) {
                    g.drawImage(image, xPos, yPos);
                }
            }
        }
    }
}
