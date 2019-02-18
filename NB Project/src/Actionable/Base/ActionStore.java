package Actionable.Base;

import Components.Structures.Player;
import Components.Structures.TiledMapPlus;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Handles a group of entities
 * Creates them
 * Draws them
 * Removes them
 * @author David Charkey
 */
public abstract class ActionStore
{

    // Stores entities
    private ArrayList<Actionable> entityList;

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
    public ActionStore(TiledMapPlus map)
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
    public final Actionable getEntityUnder(Player player)
    {
        Actionable foundEntity = null;
        for (Actionable curEntity : entityList)
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
    public final void updateMap(Graphics g, Player alien)
    {
        // Hides entities encountered
        hideEntities(g);

        // Stop if no entitiess have been newly interacted with
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
        Actionable entityEnc = getLastInteractedEntity(player);

        // Get grid location info
        int[] pos = entityEnc.getGridLoc();
        int row = pos[0];
        int col = pos[1];
        int x = col * TiledMapPlus.tileSize;
        int y = row * TiledMapPlus.tileSize;

        // Save info required to hide entity
        Image groundTile = mapCopy.getTileImage(col, row, 0);
        String groundPos = "" + x + "-" + y;
        hiddenEntities.put(groundPos, groundTile);

        // Save image of entity
        int entityLayerIndex = mapCopy.getLayerIndex(entityLayerName);
        Image entityTile = mapCopy.getTileImage(col, row, entityLayerIndex);
        entityEnc.addTileImage(entityTile);
        
        // Do custom actions
        doCustomInteraction(entityEnc);

        // Update status as entity processing is complete
        switchEntityInteractionStatus();
    }

    public abstract ArrayList<Actionable> getEntities();

    public abstract String getEntityLayerName();

    public abstract Actionable getLastInteractedEntity(Player player);

    public abstract boolean getEntityInteractionStatus();
    
    public void doCustomInteraction(Actionable ent)
    {
        
    }

    public abstract void switchEntityInteractionStatus();

}
