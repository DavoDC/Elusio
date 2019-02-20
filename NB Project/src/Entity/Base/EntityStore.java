package Entity.Base;

import Components.Structures.Player;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

/**
 * Handles a group of entities
 * Creates them
 * Draws them
 * Removes them
 * @author David Charkey
 */
public abstract class EntityStore
{

    // Stores entities
    private ArrayList<Entity> entityList;

    // Stores information about encountered entities
    private ArrayList<Entity> hiddenEntities;

    // The name of the entity layer
    private String entityLayerName;
    

    /**
     * Initialise entity store
     */
    public EntityStore()
    {
        // Initialise fields, where possible
        hiddenEntities = new ArrayList<>();
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
        
        // Get name of entity layer
        this.entityLayerName = getEntityLayerName();
        
        // Initialise entity images
        for(Entity curEnt : entityList)
        {
            curEnt.initTileImages(entityLayerName);
        }
        
        // Custom changes
        customizeEntities(entityList);
    }
    
    
    public void customizeEntities(ArrayList<Entity> entList)
    {
        
    }
    
    /**
     * Get pre-made entity objects
     * @return 
     */
    public abstract ArrayList<Entity> getEntities();
    
    /**
     * Get the name of the layer where the entities are
     * @return 
     */
    public abstract String getEntityLayerName();


    /**
     * Access the entity list
     * @return 
     */
    public final ArrayList<Entity> getEntityList()
    {
        return entityList;
    }
    
    /**
     * Update map according to interactions
     * @param g
     * @param alien
     */
    public final void updateMap(Graphics g, Player alien)
    {
        // Hides entities encountered
        hideEntities(g);

        // Stop if no entitiess have been newly interacted with
        if (!getEntityInteractionStatus()) { return; }

        // Processes new encounters
        handleNewInteractions(alien);
    }
    
    public abstract boolean getEntityInteractionStatus();

    /**
     * Hides entities encountered
     */
    private void hideEntities(Graphics g)
    {
        for (Entity ent : hiddenEntities)
        {
            ent.hideEntity(g);
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
        Entity entEnc = getLastInteractedEntity(player);

        // Add to hidden entities
        hiddenEntities.add(entEnc);

        // Update status as entity processing is complete
        switchEntityInteractionStatus();
    }

    public abstract Entity getLastInteractedEntity(Player player);

    public abstract void switchEntityInteractionStatus();

}
