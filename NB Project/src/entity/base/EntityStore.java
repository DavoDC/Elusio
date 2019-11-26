package entity.base;

import components.structures.Map;
import components.structures.Player;
import main.Globals;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

/**
 * Handles a group of entities = Creates, Draws and Removes
 *
 * @author David 
 */
public abstract class EntityStore
{

    // Stores information about all entities
    private ArrayList<Entity> entityList;

    // Stores information about encountered entities
    private ArrayList<Entity> encEntities;

    /**
     * Initialise entity store
     */
    public EntityStore()
    {
        // Initialise fields, where possible
        encEntities = new ArrayList<>();
        entityList = new ArrayList<>();

        // Do custom initialisations
        specificInit();

    }

    /**
     * Add entity-specific data
     */
    private void specificInit()
    {
        // Populate entity list
        entityList.addAll(getEntities());

        // Get layer names
        String entLS = getEntLS();
        String hideLS = getHideLS();

        // Initialise entity images
        for (Entity curEnt : entityList)
        {
            curEnt.initTileImages(entLS, hideLS);
        }
    }

    /**
     * Get pre-made entity objects
     *
     * @return
     */
    public abstract ArrayList<Entity> getEntities();

    /**
     * Get the name of the layer where the entities are
     *
     * @return
     */
    public abstract String getEntLS();

    /**
     * Get the name of the layer to hide the entity
     *
     * @return
     */
    public String getHideLS()
    {
        return "Ground";
    }

    /**
     * Access the entity list
     *
     * @return
     */
    public final ArrayList<Entity> getEntityList()
    {
        return entityList;
    }

    /**
     * Get first entity under player
     *
     * @param player
     * @return Entity found, or null
     */
    public final Entity getEntityUnder(Player player)
    {
        Entity foundEnt = null;

        for (Entity curEnt : getEntityList())
        {
            if (isEntityUnder(player, curEnt))
            {
                foundEnt = curEnt;
                break;
            }
        }

        return foundEnt;
    }

    /**
     * Check if an entity is under a given player
     *
     * @param player
     * @param ent Entity
     * @return True if underneath
     */
    public boolean isEntityUnder(Player player, Entity ent)
    {
        // Get player position and adjust
        int xPlayer = player.getX() + Globals.playerXadj;
        int yPlayer = player.getY() + Globals.playerYadj;
        int playerCol = Map.convertXtoCol(xPlayer);
        int playerRow = Map.convertYtoRow(yPlayer);

        // Compare positions of entity to player
        String[][] gridPos = ent.getGridPosArray();
        for (String[] row : gridPos)
        {
            for (String pos : row)
            {
                // Extract position
                String[] posPair = pos.split("-");
                int curEntCol = Integer.parseInt(posPair[0]);
                int curEntRow = Integer.parseInt(posPair[1]);

                // Compare to player
                boolean sameCol = (playerCol == curEntCol);
                boolean sameRow = (playerRow == curEntRow);
                boolean samePos = sameCol && sameRow;
                if (samePos)
                {
                    return true;
                }
            }
        }

        // Return default
        return false;
    }

    /**
     * Update map according to interactions
     *
     * @param g
     * @param alien
     */
    public final void updateMap(Graphics g, Player alien)
    {
        for (Entity ent : encEntities)
        {
            ent.hideEntity(g);
        }
    }

    /**
     * Add to list of encounters
     *
     * @param ent
     */
    public void addEncounter(Entity ent)
    {
        encEntities.add(ent);
    }

}
