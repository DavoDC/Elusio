
package Entity.Obstacle;

import Components.Structures.Map;
import Components.Structures.Player;
import Entity.Base.Entity;
import Entity.Base.EntityStore;
import Entity.Item.Item;
import Main.Globals;
import java.util.ArrayList;

/**
 *
 * @author David Charkey
 */
public class ObstacleStore extends EntityStore
{

    @Override
    public ArrayList<Entity> getEntities()
    {
        ArrayList<Entity> obstacles = new ArrayList<>();
        
        obstacles.add(new Obstacle("Trees", 32, 9, 5, 5));
        obstacles.add(new Obstacle("TreesClose", 29, 7, 6, 6));
        
        return obstacles;
    }

    @Override
    public String getEntityLayerName()
    {
        return "Obstacles"; 
    }

    @Override
    public boolean getEntityInteractionStatus()
    {
        return false;
    }

    @Override
    public Entity getLastInteractedEntity(Player player)
    {
        return null;
    }

    @Override
    public void switchEntityInteractionStatus()
    {
    }
    
    /**
     * Get item under player
     * @param player
     * @return foundItem, or null
     */
    public final Item getObstUnder(Player player)
    {
//        Item foundItem = null;
//        for (Entity curEnt : super.getEntityList())
//        {
//            if (isObstUnder(player, (Item) curEnt))
//            {
//                foundItem = (Item) curEnt;
//                break;
//            }
//        }
//        return foundItem; 
        return null;
    }
    
    /**
     * Check if an item is under a given player
     * @param player
     * @param item
     * @return 
     */
    public boolean isObstUnder(Player player, Item item)
    {
        // Get player position and adjust
        int xPlayer = player.getX() + Globals.playerXadj;   
        int yPlayer = player.getY() + Globals.playerYadj;
        int playerCol = Map.convertXtoCol(xPlayer);
        int playerRow = Map.convertYtoRow(yPlayer);
        
        // Get entity position
        String[] posPair = item.getGridPosPair(0, 0);
        int itemCol = Integer.parseInt(posPair[0]);
        int itemRow = Integer.parseInt(posPair[1]);

        // Compare positions
        boolean isUnder = (playerCol == itemCol) && (playerRow == itemRow);
        
        // Return result
        return isUnder;
    }
    
}
