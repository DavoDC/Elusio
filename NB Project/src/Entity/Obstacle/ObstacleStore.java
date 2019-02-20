
package Entity.Obstacle;

import Components.Structures.Map;
import Components.Structures.Player;
import Entity.Base.Entity;
import Entity.Base.EntityStore;
import Entity.Item.Item;
import Main.Globals;
import java.util.ArrayList;
import org.newdawn.slick.Image;

/**
 * Handles obstacles
 * @author David Charkey
 */
public class ObstacleStore extends EntityStore
{

    @Override
    public ArrayList<Entity> getEntities()
    {
        ArrayList<Entity> obstacles = new ArrayList<>();
        
        Obstacle trees = new Obstacle("Trees", 32, 9, 5, 5);
        obstacles.add(trees); 
        obstacles.add(new ObstacleZone("Trees", "Cryocap", 29, 7, 6, 6));
        
        return obstacles;
    }
    
    @Override
    public void customizeEntities(ArrayList<Entity> entList)
    {
        for(Entity curEnt : entList)
        {
            if (curEnt instanceof Obstacle)
            {
                if (curEnt.getName().equals("Trees"))
                {
                    addIceTiles(curEnt);
                }
            }
        }
    }
    
    private void addIceTiles(Entity ent)
    {
        int iceCol = 23;
        int iceRow = 24;
        int groundIndex = Globals.map.getLayerIndex("Ground");
        Image iceTile = Globals.map.getTileImage(iceCol, iceRow, groundIndex);

        ent.replaceUndImages(iceTile);
    }

    @Override
    public String getEntityLayerName()
    {
        return "Obstacles"; 
    }

    @Override
    public boolean getEntityInteractionStatus()
    {
        return Globals.obstEnc;
    }

    @Override
    public Entity getLastInteractedEntity(Player player)
    {
        return player.getLastObstacle();
    }

    @Override
    public void switchEntityInteractionStatus()
    {
        Globals.obstEnc = !Globals.obstEnc;
    }
    
    /**
     * Get obstacle zone under player
     * @param player
     * @return foundOZ, or null
     */
    public final ObstacleZone getCurObZone(Player player)
    {
        ObstacleZone foundOZ = null;
        
        for (Entity curEnt : super.getEntityList())
        {
            if (curEnt instanceof ObstacleZone)
            {               
                if (isObstUnder(player, (ObstacleZone) curEnt))
                {
                    foundOZ = (ObstacleZone) curEnt;
                    break;
                }
            }
        }
        
        return foundOZ;
    }
    
    /**
     * Check if an Obstacle Zone is under a given player
     * @param player
     * @param OZ
     * @return 
     */
    public boolean isObstUnder(Player player, ObstacleZone OZ)
    {
        // Get player position and adjust
        int xPlayer = player.getX() + Globals.playerXadj;   
        int yPlayer = player.getY() + Globals.playerYadj;
        int playerCol = Map.convertXtoCol(xPlayer);
        int playerRow = Map.convertYtoRow(yPlayer);
        
        // Compare positions of OZ to player
        String[][] obZonePos = OZ.getGridPosArray();
        for (String[] row : obZonePos)
        {
            for (String pos : row)
            {
                // Get a position in OZ
                String[] posPair = pos.split("-");
                int curOZcol = Integer.parseInt(posPair[0]);
                int curOZrow = Integer.parseInt(posPair[1]);
                
                // Compare to player
                boolean sameCol = (playerCol == curOZcol);
                boolean sameRow = (playerRow == curOZrow);
                boolean samePos = sameCol && sameRow;
                if (samePos) { return true; }
            }
        }
        
        // Return default
        return false;
    }
    
    public Obstacle getMatchOfZone(String zoneName)
    {
        Obstacle foundObst = null;
        
        // For all obstacles
        for (Entity curEnt : super.getEntityList())
        {
            // If is an obstacle, not zone
            if (curEnt instanceof Obstacle)
            {
                // And names match
                if (zoneName.equals(curEnt.getName()))
                {
                    foundObst = (Obstacle) curEnt;
                    break;
                }
            }
        }
        
        return foundObst;
    }
}
