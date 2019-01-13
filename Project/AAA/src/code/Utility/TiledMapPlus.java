package code.Utility;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Handles map rendering
 * Handles collisions
 * Handles camera
 * 
 * //can handle creating "blocked" array
 * 
 * // has XY -> tile system
 * // can handle an object layer
 * 
 * // 4 methods = move up, down, L, R
 * // map.render() (use playerX and playerY)
 * // render next when you get to edge
 * // also render rectangle triggers that cause movement to next location
 * 
 * @author David
 */
public class TiledMapPlus extends TiledMap
{
    
    public TiledMapPlus(String ref) throws SlickException 
    {
        super(ref);
    }
    
    
    //// This will keep a list of Tiles that are blocked
//private boolean blocked[][];

// For collision detection, we have a list of Rectangles you can use to test against
//private ArrayList<Rectangle> blocks;
    
    
//    
//    
//    // This will create an Array with all the Tiles in your map. When set to true, it means that Tile is blocked.
//blocked = new boolean[this.getWidth()][this.getHeight()];
//
//// Loop through the Tiles and read their Properties
//
//// Set here the Layer you want to Read. In your case, it'll be layer 1,
//// since the objects are on the second layer.
//int layer = 1; 
//
//for(int i = 0; i < this.getWidth(); i++) {
//    for(int j = 0; j < this.getHeight(); j++) {
//
//        // Read a Tile
//        int tileID = getTileId(i, j, layer);
//
//        // Get the value of the Property named "blocked"
//        String value = getTileProperty(tileID, "blocked", "false");
//
//        // If the value of the Property is "true"...
//        if(value.equals("true")) {
//
//            // We set that index of the TileMap as blocked
//            blocked[i][j] = true;
//
//            // And create the collision Rectangle
//            blocks.add(new Rectangle((float)i * tileSize, (float)j * tileSize, tileSize, tileSize));
//        }
//    }
//}
    
    
//    ///boolean isInCollision = false;
//for(Rectangle ret in yourTiledMap.getBlocks()) {
//    if(player.bounds.intersects(ret.bounds)) {
//        isInCollision = true;
//    }
//}
////    
    
    
//    public boolean[][] blocked;
//    
//    public CollUtil(TiledMap map)
//    {
//       
//       
//        
//      int NUMBEROFTILESINAROW = map.getHeight()-2;
//      int NUMBEROFTILESINACOLUMN = map.getWidth()-2; 
//      int NUMBEROFLAYERS = map.getLayerCount();
//        
//        blocked = new boolean[NUMBEROFTILESINAROW][NUMBEROFTILESINACOLUMN];
//        
//             for (int l = 0; l < NUMBEROFLAYERS; l++) 
//             {
//             String layerValue = map.getLayerProperty(l, "blocked", "false");
//            if (layerValue.equals("true")) {
//              
//                for (int c = 0; c < NUMBEROFTILESINACOLUMN; c++) 
//                {
//                    for (int r = 0; r < NUMBEROFTILESINAROW; r++) 
//                    {
//                        if (map.getTileId(c, r, l) != 0) 
//                        {
//                            blocked[c][r] = true;
//                        }
//                    }
//                }
//            }
//        }
//    }
//    
//    
//    /**
//     * Only for 64x64 tiles
//     * @param x
//     * @param y
//     * @return 
//     */
//    public boolean canPass(float x, float y) 
//    {
//        try 
//        {
//            int xBlock = (int) x / 64;
//            int yBlock = (int) y / 64;
//            return !blocked[xBlock][yBlock];
//        }
//        catch (Exception e)
//        {
//            return false;
//        }
//    }

    
    
}
