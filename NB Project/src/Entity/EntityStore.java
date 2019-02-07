package Entity;

import Components.Structures.TiledMapPlus;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author CHARKEYD
 */
public abstract class EntityStore
{

    // Stores entities
    private ArrayList<Entity> entityList;

    // Stores information about encountered entities
    private HashMap<String, Image> hiddenEntities;

    // A copy of the game map
    private TiledMapPlus mapCopy;

    // The name of the entity layer
    private String entityLayerName;

    /**
     * Initialise entity store
     *
     * @param map The current game map
     */
    public EntityStore(TiledMapPlus map)
    {
        entityList = new ArrayList<>();
        entityList.addAll(getEntities());

        hiddenEntities = new HashMap<>();

        mapCopy = map;
        this.entityLayerName = getEntityLayerName();

    }

    /**
     * Returns the entity the player is on Returns null if nothing found
     *
     * @param player
     * @return
     */
    public Entity getEntityUnder(Player player)
    {
        Entity foundEntity = null;
        for (Entity curEntity : entityList)
        {
            if (curEntity.isEntityUnder(player))
            {
                foundEntity = curEntity;
                break;
            }
        }
        return foundEntity;
    }

    /**
     * Processes entity interactions
     *
     * @param g
     * @param alien
     */
    public void updateMap(Graphics g, Player alien)
    {
        // Hides entities encountered
        hideEntities(g);

        // Stop if no items have been newly grabbed
        if (!getEntityInteractionStatus())
        {
            return;
        }

        // Processes new encounters
        handleNewInteractions(alien);
    }

    /**
     * Hides entities encountered
     */
    private void hideEntities(Graphics g)
    {
        Image cur;
        for (String posS : hiddenEntities.keySet())
        {
            // Extract position
            String[] posA = posS.split("-");
            int x = Integer.parseInt(posA[0]);
            int y = Integer.parseInt(posA[1]);

            // Get image
            cur = hiddenEntities.get(posS);

            // Draw image
            g.drawImage(cur, x, y);

        }
    }

    /**
     * Process encounters with entities
     *
     * @param player
     */
    private void handleNewInteractions(Player player)
    {
        // Get entity just encountered
        Entity entityEnc = getLastInteractedEntity(player);

        // Get grid location info
        int[] pos = entityEnc.getGridLoc();
        int row = pos[0];
        int col = pos[1];
        int x = col * 64;
        int y = row * 64;

        // Save info required to hide entity
        Image groundTile = mapCopy.getTileImage(col, row, 0);
        String groundPos = "" + x + "-" + y;
        hiddenEntities.put(groundPos, groundTile);

        // Save image of entity
        int entityLayerIndex = mapCopy.getLayerIndex(entityLayerName);
        Image entityTile = mapCopy.getTileImage(col, row, entityLayerIndex);
        entityEnc.addTileImage(entityTile);

        // Update status as entity processing is complete
        switchEntityInteractionStatus();
    }

    public abstract ArrayList<Entity> getEntities();

    public abstract String getEntityLayerName();

    public abstract Entity getLastInteractedEntity(Player player);

    public abstract boolean getEntityInteractionStatus();

    public abstract void switchEntityInteractionStatus();

}
