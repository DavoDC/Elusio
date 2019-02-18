package Actionable.Item;

import Components.Popups.Popup;
import Actionable.Base.Actionable;
import Actionable.Base.ActionStore;
import Components.Structures.Player;
import Main.Globals;
import Components.Structures.TiledMapPlus;
import java.util.ArrayList;

/**
 * Handles items
 *
 * @author David Charkey
 */
public class ItemStore extends ActionStore
{

    public ItemStore(TiledMapPlus map)
    {
        super(map);
    }

    @Override
    public ArrayList<Actionable> getEntities()
    {
        // Create list
        ArrayList<Actionable> itemList = new ArrayList<>();

        // Add magic items
        itemList.add(new MagicItem(
                "Cryocapacitor", 
                "absorb small amounts of nearby energy as antimatter",
                "annihilating some weak, dry matter once activated",
                23, 25));
        itemList.add(new MagicItem(
                "Magistructor Orb",
                "beat rhythmically with creative vigor",
                "manifest, build and extend an existing substance",
                82, 5));
        
        // Add crystals
        itemList.add(new Item( // Highest
                "Crystal1", 
                65, 6));
        itemList.add(new Item( // Leftest
                "Crystal2", 
                30, 3));
        itemList.add(new Item( // Mid
                "Crystal3",
                89, 41));
        itemList.add(new Item( // Lowest
                "Crystal4",
                71, 81));
        
        // Return list
        return itemList;

    }

    @Override
    public String getEntityLayerName()
    {
        return "Items";
    }

    @Override
    public Actionable getLastInteractedEntity(Player player)
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

    public Popup getInfoPopup(Item item, int r, int c)
    {
        // Features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(adjustGridPos("row", r));  // Tile grid row
        feats.add(adjustGridPos("col", c));  // Tile grid column 
        feats.add(18); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"
        
        // Text
        ArrayList<String> text = new ArrayList<>();
        String start = "(Xaidu, telepathically): ";
        if (item instanceof MagicItem)
        {
            // Add name info
            String name = start;
            name += "Here is my appraisal of what I would call a ";
            name += item.getName();
            text.add(name);
            
            // Add properties info
            String prop = start;
            prop += "It appears to: ";
            prop += ((MagicItem) item).getProp();
            text.add(prop);
            
            // Add usage info
            String use = start;
            use += "It could be used for: ";
            use += ((MagicItem) item).getUse();
            text.add(use);
        }
        else // Normal item
        {
            // Add name info
            String name = start;
            name += "This appears to be a ";
            name += item.getName();
            text.add(name);
            
            // Crystal case
            if (name.contains("Crystal"))
            {
                String crystal = start;
                crystal += "It is embued with a signature enchantment of some sort";
                text.add(crystal);
            }
        }
        
        // Return
        return (new Popup(feats, text));
    }

    private int adjustGridPos(String axis, int raw)
    {
        // Grid axis
        boolean isRow = axis.equals("row");
        
        // New value
        int newVal = 0;
        
        // Get max value in origin square
        int screen = 0;
        if (isRow) { screen = Globals.screenH; }
        else { screen = Globals.screenW; }
        int max = screen/TiledMapPlus.tileSize;
        
        // Get value in origin square
        int origin = raw % max;
        
        // Take away to get to origin of current square
        newVal = raw - origin; 

        // Adjust to where you want
        if (isRow) 
        { 
            newVal -= 4; 
        }
        else 
        { 
            newVal -= 7;  
        }
        
        return newVal;
    }
            
            
    
    
}
