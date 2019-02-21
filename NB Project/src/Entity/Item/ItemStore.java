package Entity.Item;

import Components.Popups.Popup;
import Components.Structures.Player;
import Entity.Base.Entity;
import Entity.Base.EntityStore;
import java.util.ArrayList;

/**
 * Handles items
 * @author David Charkey
 */
public class ItemStore extends EntityStore
{

    @Override
    public ArrayList<Entity> getEntities()
    {
        // Create list
        ArrayList<Entity> itemList = new ArrayList<>();

        // Add magic items
        itemList.add(new Item(
                "Cryocapacitor Set", 
                new String[] 
                { 
                "Seems to be absorbing small amounts of nearby energy as antimatter",
                "Could annihilate some weak, dry matter once activated"
                },
                23, 25));
        itemList.add(new Item(
                "Magistructor Orb", 
                new String[] 
                { 
                "It beats rhythmically with creative vigor",
                "May be able to manifest, build and/or extend an existing substance"
                },
                82, 5));

        // Add crystals
        itemList.add(new Item(
                "Crystal12",
                new String[]
                {
                "A high, specific EMR frequency is emanating.. 1122Hz",
                "Somehow it seems that .. it is longing for something... "
                },
                65, 6));
        itemList.add(new ProtectedItem( 
                "Crystal3", 
                new String[]
                {
                "Has an EMR signature of 333Hz",
                "It .. misses the presence of something... "
                },
                "Viridash",
                30, 33));
        itemList.add(new ProtectedItem( 
                "Crystal6", 
                new String[]
                {
                "It emanates a particular EMR frequency .. 666Hz",
                "It is longing for something... "
                },
                "Trevil",
                89, 41));
        itemList.add(new ProtectedItem(
                "Crystal9", 
                new String[]
                {
                "A particular EMR frequency is being emitted .. 999Hz",
                "This crystal yearns to join something ... "
                },
                "Mycovolence",
                71, 81));
        
        // Return list
        return itemList;

    }
    
    @Override
    public String getEntLS()
    {
        return "Items";
    }

    /**
     * Get item under player
     * @param alien
     * @return Item, or null if nothing/non-item
     */
    public Item getItemUnder(Player alien)
    {
        // Get entity
        Entity entityFound = super.getEntityUnder(alien);
        
        Item item = null;
        if (entityFound instanceof Item)
        {
            // Return item if an item
            item = (Item) entityFound;
        }
        
        // Return null if not an item
        return item;
    }
    
    /**
     * Get a popup with item information
     * @param item
     * @param r
     * @param c
     * @return 
     */
    public Popup getInfoPopup(Item item, int r, int c)
    {
        // Set features of popip
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(r);  // Tile grid row
        feats.add(c);  // Tile grid column 
        feats.add(18); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"
        
        // Create popup lines 
        ArrayList<String> itemLines = item.getInfoLines();
        ArrayList<String> newLines = new ArrayList<>();
        String start = "(Xaidu, telepathically): ";
        for (String curItemLine : itemLines)
        {
            newLines.add(start + curItemLine);
        }

        // Return
        return (new Popup(feats, newLines));
    }

    
 }
