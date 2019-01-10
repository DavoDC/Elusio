package code.Entity;

import org.newdawn.slick.tiled.TiledMap;


public class CollUtil
{
    

    public boolean[][] blocked;
    
    public CollUtil(TiledMap map)
    {
       
       
        
      int NUMBEROFTILESINAROW = map.getHeight()-2;
      int NUMBEROFTILESINACOLUMN = map.getWidth()-2; 
      int NUMBEROFLAYERS = map.getLayerCount();
        
        blocked = new boolean[NUMBEROFTILESINAROW][NUMBEROFTILESINACOLUMN];
        
             for (int l = 0; l < NUMBEROFLAYERS; l++) 
             {
             String layerValue = map.getLayerProperty(l, "blocked", "false");
            if (layerValue.equals("true")) {
              
                for (int c = 0; c < NUMBEROFTILESINACOLUMN; c++) 
                {
                    for (int r = 0; r < NUMBEROFTILESINAROW; r++) 
                    {
                        if (map.getTileId(c, r, l) != 0) 
                        {
                            blocked[c][r] = true;
                        }
                    }
                }
            }
        }
    }
    
    
    /**
     * Only for 64x64 tiles
     * @param x
     * @param y
     * @return 
     */
    public boolean canPass(float x, float y) 
    {
        int xBlock = (int) x / 64;
        int yBlock = (int) y / 64;
        return !blocked[xBlock][yBlock];
    }


}
    
    


