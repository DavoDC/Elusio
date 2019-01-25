package Utility;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Handles map rendering
 * Handles collisions
 * Handles camera
 * 
 * Note:
 * The collision layer must have a property called blocked, set to true
 * 
 * @author David
 */
public class TiledMapPlus extends TiledMap
{
    // Tile array
    private boolean[][] blocked;  
    
    // Determine X collision tightness
    private final int Xfactor = 25;
    private final int Xadjuster = 50;
    
    // Determines Y collision tightnesss
    private final int Yfactor = 60;
    private final int Yadjuster = 50;
    
    // Tile size
    private final int tileSize = 64;
            
    
    /**
     * Initializes array of "tile cells"
     * Each cell represents a tile
     * "True" means blocked
     * 
     * @param ref
     * @throws SlickException 
     */
    public TiledMapPlus(String ref) throws SlickException 
    {
        super(ref);
        
        int NUMBEROFTILESINAROW = getHeight()-2;
        int NUMBEROFTILESINACOLUMN = getWidth()-2; 
        int NUMBEROFLAYERS = getLayerCount();
        
        blocked = new boolean[NUMBEROFTILESINAROW][NUMBEROFTILESINACOLUMN];
        
             for (int l = 0; l < NUMBEROFLAYERS; l++) 
             {
                String layerValue = getLayerProperty(l, "blocked", "false");
                if (layerValue.equals("true")) 
                {

                    for (int c = 0; c < NUMBEROFTILESINACOLUMN; c++) 
                    {
                        for (int r = 0; r < NUMBEROFTILESINAROW; r++) 
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
     * Checks if a point is on a blocked tile
     * @param x
     * @param y
     * @return 
     */
    private boolean canPass(float x, float y) 
    {
        try 
        {
            int xBlock = (int) x / tileSize;
            int yBlock = (int) y / tileSize;
            return !blocked[xBlock][yBlock];
        }
        catch (Exception e)
        {
            return false;
        }
    }

    
    
    /**
     * Checks tile collision and map bounds
     * @param playerX
     * @param playerY
     * @param relSpeed Speed as function of delta
     * @param cam Current camera object
     * @return True if up movement allowed 
     */
    public boolean isUpAllowed(int playerX, int playerY, float relSpeed, Camera cam)
    {
        // True = No blocked tiles upwards
        boolean cond1 = canPass(playerX + Xfactor, playerY - relSpeed);
        boolean cond2 = canPass(playerX + Xadjuster, playerY - relSpeed);

        // True if not at edge
        boolean cond3 = !cam.isTooUp();

        return (cond1 && cond2 && cond3);
    }

    /**
     * Checks tile collision and map bounds
     * @param playerX
     * @param playerY
     * @param relSpeed Speed as function of delta
     * @param cam Current camera object
     * @return True if down movement allowed 
     */
    public boolean isDownAllowed(int playerX, int playerY, float relSpeed, Camera cam)
    {        
        // True if there are no blocked tiles
        float newY = playerY + Yfactor + relSpeed;
        boolean cond1 = canPass(playerX + Xfactor, newY );
        boolean cond2 = canPass(playerX + Xadjuster, newY );

        // True if not at edge
        boolean cond3 = !cam.isTooDown();

        return (cond1 && cond2 && cond3);  
    }
    
    /**
     * Checks tile collision and map bounds
     * @param playerX
     * @param playerY
     * @param relSpeed Speed as function of delta
     * @param cam Current camera object
     * @return True if left movement allowed 
     */
    public boolean isLeftAllowed(int playerX, int playerY, float relSpeed, Camera cam)
    {
        // True if there are no blocked tiles
        boolean cond1 = canPass(playerX - relSpeed, playerY + Yadjuster);
        boolean cond2 = canPass(playerX - relSpeed, playerY + Yfactor);
        
        // True if not at edge
        boolean cond3 = !cam.isTooLeft();

        return (cond1 && cond2 && cond3); 
    }

    /**
     * Checks tile collision and map bounds
     * @param playerX
     * @param playerY 
     * @param relSpeed Speed as function of delta
     * @param cam Current camera object
     * @return True if right movement allowed
     */
    public boolean isRightAllowed(int playerX, int playerY, float relSpeed, Camera cam)
    {
        // True if there are no blocked tiles
        float newX = playerX + Xfactor + relSpeed;
        boolean cond1 = canPass(newX, playerY + Yfactor);
        boolean cond2 = canPass(newX, playerY + Yadjuster);
        
        
        // True if not at edge
        boolean cond3 = !cam.isTooRight();

        return (cond1 && cond2 && cond3);      
    }
    
    
}
