package Entity.Base;

import Main.Globals;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Models entities
 *
 * @author David Charkey
 */
public abstract class Entity
{

    // Info
    private String name;

    // Grid Positions
    private String[][] gridPos;

    // Tiles of entity
    private Image[][] entImages;

    // Tiles of ground underneath
    private Image[][] undImages;

    /**
     * Create an item
     *
     * @param name
     * @param tlc top left tile mapCol
     * @param tlr top left tile mapRow
     * @param w tiles across
     * @param h tiles down
     */
    public Entity(String name, int tlc, int tlr, int w, int h)
    {
        // Save variables
        this.name = name;

        // Initialise arrays
        entImages = new Image[h][w];
        undImages = new Image[h][w];
        gridPos = new String[h][w];

        // Populate grid positions
        for (int r = 0; r < gridPos.length; r++)
        {
            int actualRow = r + tlr;
            for (int c = 0; c < gridPos[r].length; c++)
            {
                int actualCol = c + tlc;
                gridPos[r][c] = actualCol + "-" + actualRow + "-" + c + "-" + r;
            }
        }

    }

    /**
     * Initialise the images of the entity and its ground tiles
     *
     * @param entLS
     * @param undLS
     */
    public void initTileImages(String entLS, String undLS)
    {
        int entLayerIndex = Globals.map.getLayerIndex(entLS);
        int undLayerIndex = Globals.map.getLayerIndex(undLS);

        for (String[] gridRow : gridPos)
        {
            for (String locString : gridRow)
            {
                // Extract position
                String[] locPair = locString.split("-");
                int mapCol = Integer.parseInt(locPair[0]);
                int mapRow = Integer.parseInt(locPair[1]);
                int arrCol = Integer.parseInt(locPair[2]);
                int arrRow = Integer.parseInt(locPair[3]);

                // Get and save entity image
                Image entImage = Globals.map.getTileImage(mapCol, mapRow, entLayerIndex);
                entImages[arrRow][arrCol] = entImage;

                // Get and save ground image
                Image undImage = Globals.map.getTileImage(mapCol, mapRow, undLayerIndex);
                undImages[arrRow][arrCol] = undImage;
            }
        }
    }

    /**
     * Get the name of the entity
     *
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get grid position pair Uses array values
     *
     * @param c
     * @param r
     * @return
     */
    public String[] getGridPosPair(int c, int r)
    {
        return ((gridPos[r][c]).split("-"));
    }

    /**
     * Get array of positions
     *
     * @return
     */
    public String[][] getGridPosArray()
    {
        return gridPos;
    }

//    /**
//     * Replace all under images with input
//     * @param rep
//     */
//    public void replaceUndImages(Image rep)
//    {
//        for (int r = 0; r < undImages.length; r++) 
//        {
//            for (int c = 0; c < undImages[r].length; c++)
//             {
//                 undImages[r][c] = null;
//                 undImages[r][c] = rep;
//             }
//        }
//    }
    /**
     * Hide an entity
     *
     * @param g
     */
    public void hideEntity(Graphics g)
    {
        for (String[] gridRow : gridPos)
        {
            for (String locString : gridRow)
            {
                // Extract position
                String[] locPair = locString.split("-");
                int mapCol = Integer.parseInt(locPair[0]);
                int mapRow = Integer.parseInt(locPair[1]);
                int xPos = mapCol * Globals.tileSize;
                int yPos = mapRow * Globals.tileSize;
                int arrCol = Integer.parseInt(locPair[2]);
                int arrRow = Integer.parseInt(locPair[3]);

                // Get ground image
                Image undImage = undImages[arrRow][arrCol];

                // If not null, draw it
                if (undImage != null)
                {
                    g.drawImage(undImage, xPos, yPos);
                }
            }
        }
    }

}
