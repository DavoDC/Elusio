package entity.base;

import components.structures.Map;
import components.structures.Player;
import main.Globals;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

/**
 * Handle a group of entities (Creation, Drawing and Removal)
 *
 * @author David
 */
public abstract class EntityStore {

    // A list of all entities
    private final ArrayList<Entity> entityList;

    // A list of all encountered entities
    private final ArrayList<Entity> encEntities;

    /**
     * Initialise entity store
     */
    public EntityStore() {

        // Initialise fields, where possible
        encEntities = new ArrayList<>();
        entityList = new ArrayList<>();

        // Do custom initialisations
        specificInit();
    }

    /**
     * Add entity-specific data
     */
    private void specificInit() {

        // Populate entity list
        entityList.addAll(getEntities());

        // Get layer names
        String entLS = getEntLS();
        String hideLS = getHideLS();

        // Initialise entity images
        for (Entity curEnt : entityList) {
            curEnt.initTileImages(entLS, hideLS);
        }
    }

    /**
     * Get entity objects
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
    public String getHideLS() {
        return "Ground";
    }

    /**
     * Retrieve the list of all entities
     *
     * @return
     */
    public final ArrayList<Entity> getEntityList() {
        return entityList;
    }

    /**
     * Get first entity under player
     *
     * @param player
     * @return Entity found, or null
     */
    public final Entity getEntityUnder(Player player) {

        // For all entities
        for (Entity curEnt : getEntityList()) {

            // If entity is under player
            if (isEntityUnder(player, curEnt)) {

                // Return entity
                return curEnt;
            }
        }

        // If nothing found, return null
        return null;
    }

    /**
     * Check if an entity is under a given player
     *
     * @param player
     * @param ent Entity
     * @return True if underneath
     */
    public boolean isEntityUnder(Player player, Entity ent) {

        // Get player position and adjust
        int xPlayer = player.getX() + Globals.playerXadj;
        int yPlayer = player.getY() + Globals.playerYadj;
        int playerCol = Map.convertXtoCol(xPlayer);
        int playerRow = Map.convertYtoRow(yPlayer);

        // Get information of entity tiles
        String[][] gridPos = ent.getGridPosArray();

        // For all tiles
        for (String[] row : gridPos) {
            for (String pos : row) {

                // Extract position of entity tiles
                String[] posPair = pos.split("-");
                int curEntCol = Integer.parseInt(posPair[0]);
                int curEntRow = Integer.parseInt(posPair[1]);

                // Compare entity tile to player
                boolean sameCol = (playerCol == curEntCol);
                boolean sameRow = (playerRow == curEntRow);
                if (sameCol && sameRow) {

                    // Return true if the column and row match
                    return true;
                }
            }
        }

        // Return default
        return false;
    }

    /**
     * Hide all encountered entities
     *
     * @param g
     */
    public final void updateMap(Graphics g) {

        // For all encountered entities
        for (Entity ent : encEntities) {

            // Hide from map
            ent.hideEntity(g);
        }
    }

    /**
     * Add an entity to the list of encountered entities
     *
     * @param ent
     */
    public void addEncounter(Entity ent) {

        // Add to list
        encEntities.add(ent);
    }

}
