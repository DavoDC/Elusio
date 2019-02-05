package Entity.Item;

import Entity.Entity;
import Entity.EntityStore;
import Entity.Player;
import Main.Globals;
import Utility.TiledMapPlus;
import java.util.ArrayList;

/**
 * Handles items
 *
 * @author David Charkey
 */
public class ItemStore extends EntityStore
{

    public ItemStore(TiledMapPlus map)
    {
        super(map);
    }

    @Override
    public ArrayList<Entity> getEntities()
    {
        // Create list
        ArrayList<Entity> itemList = new ArrayList<>();

        // Add to list
        itemList.add(new Item("Gold",
                "Conductive material",
                94,
                1739));
        itemList.add(new Item("Gold",
                "Conductive material",
                94,
                1800));
        // Return list
        return itemList;

    }

    @Override
    public String getEntityLayerName()
    {
        return "Items";
    }

    @Override
    public Entity getLastInteractedEntity(Player player)
    {
        return player.getLastAddedItem();
    }

    @Override
    public boolean getEntityInteractionStatus()
    {
        return Globals.itemGrabbed;
    }

    @Override
    public void switchEntityInteractionStatus()
    {
        Globals.itemGrabbed = !Globals.itemGrabbed;
    }

}
