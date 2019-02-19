package Entity.Item;

import Components.Popups.Popup;
import Components.Structures.Map;
import Entity.Base.Entity;
import Entity.Base.EntityStore;
import Components.Structures.Player;
import Main.Globals;
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
                "Cryocapacitors", 
                "absorb small amounts of nearby energy as antimatter",
                "annihilating some weak, dry matter once activated",
                "none",
                23, 25));
        itemList.add(new Item(
                "Magistructor Orb",
                "beat rhythmically with creative vigor",
                "manifest, build and extend an existing substance",
                "none",
                82, 5));
        
        // Add crystals
        itemList.add(new Item( // Highest
                "Crystal",
                "be embued with a signature enchantment",
                "... unknown",
                "none",
                65, 6));
        itemList.add(new Item( // Leftest
                "Crystal", 
                "be embued with a signature enchantment",
                "... unknown",
                "Viridash",
                30, 33));
        itemList.add(new Item( // Mid
                "Crystal", 
                "be embued with a signature enchantment",
                "... unknown",
                "Trevil",
                89, 41));
        itemList.add(new Item( // Lowest
                "Crystal", 
                "be embued with a signature enchantment",
                "... unknown",
                "Mycovolence",
                71, 81));
        
        // Return list
        return itemList;

    }
    
     /**
     * Get item under player
     * @param player
     * @return foundItem, or null
     */
    public final Item getItemUnder(Player player)
    {
        Item foundItem = null;
        for (Entity curEnt : super.getEntityList())
        {
            if (isItemUnder(player, (Item) curEnt))
            {
                foundItem = (Item) curEnt;
                break;
            }
        }
        return foundItem; 
    }
    
    /**
     * Check if an item is under a given player
     * @param player
     * @param item
     * @return 
     */
    public boolean isItemUnder(Player player, Item item)
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

    /**
     * Get a popup with item information
     * @param item
     * @param r
     * @param c
     * @return 
     */
    public Popup getInfoPopup(Item item, int r, int c)
    {
        // Features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(r);  // Tile grid row
        feats.add(c);  // Tile grid column 
        feats.add(18); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"
        
        // Text
        ArrayList<String> text = new ArrayList<>();
        String start = "(Xaidu, telepathically): ";
        
        // Add name info
        String name = start;
        name += "Here is my appraisal of what I would call a ";
        name += item.getName();
        text.add(name);

        // Add properties info
        String prop = start;
        prop += "It appears to: ";
        prop += item.getProp();
        text.add(prop);

        // Add usage info
        String use = start;
        use += "It could be used for: ";
        use += item.getUse();
        text.add(use);

        // Return
        return (new Popup(feats, text));
    }

    
 }
