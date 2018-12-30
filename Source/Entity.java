import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * Models players/rooms
 *  - Facilitates easy inventory management
 *  - Co-ordinates movement of items
 *
 * @author David Charkey 
 */
public class Entity
{
    public ArrayList<Item> items;

    public Entity()
    {
        items = new ArrayList<Item>();
    }

    /**
     * Return inventory of entity
     */
    public ArrayList<Item> getItemAL()
    {
        return items;
    }
    
    /**
     * Add an item to this entity's inventory
     */
    public void addItem(Item newitem)
    {
        items.add(newitem);
    }
    
    /**
     * Return a message based on the items in an entity
     */
    public String getISS()
    {      

        if (items.size() == 1)
        {
            if (returnNumberofType("Fixed") > 0)
            {
                return ("There is an immovable item here"  + "\n");
            }
            else
            {
                return ("There is an item here" + "\n");
            }
        }
        else if (items.size() > 1)
        {
            return ("There are items here"  + "\n");
        }

        return ("There are no items here" + "\n");
    }

    /**
     * Return lines of inventory items
     */
    public String getInvString()
    {
        if (items.size() < 1)
        {
            return "";
        }

        String output = "";

        for (Item curitem : items)
        {
            output += curitem.getInformation() + "\n";
        }
   
        return (output + "\n");
    }

    /**
     * Remove an item based on its name
     */
    public void removeItem(String itemname)
    {
        if (items.size() < 1)
        {
            return;
        }

        itemname = itemname.toLowerCase();   

        Iterator<Item> it = items.iterator();

        while (it.hasNext())
        {
            if (it.next().getItemName().toLowerCase().equals(itemname))
            {
                it.remove();
            }
        }
    }

    /**
     * Remove a given numbers of items based on type
     */
    public void removeType(int number, String type)
    {
        ArrayList<String> marked = new ArrayList<>();
        int counter = 0;
        int check = number;
        boolean removed = false;

        for ( int i = number ; i != 0  ; i--)
        {

            for (Item curitem : items)
            {
                if (curitem.getItemType().equals(type) && !removed )
                {
                    if (check == 1)
                    {
                        removed = true;
                    }

                    marked.add(curitem.getItemName());
                }
            }

            counter++;
        }

        for (String str : marked)
        {
            removeItem(str);
        }
    }

    /**
     * Move an item with a certain name to another entity
     */
    public void moveItemTo(Entity entity , String itemname)
    {
        ArrayList<Item> dest = entity.getItemAL();
        itemname = itemname.toLowerCase();   
        boolean done = false;
        
        for (Item curitem : items)
        {
            if ( curitem.getItemName().toLowerCase().equals(itemname)  &&  curitem.getItemType() != "Fixed" && !done)
            {
                dest.add(curitem);   //Add item to destination
                done = true;         // Ensures loop runs once
            }
        }

        // Remove item from source arraylist
        removeItem(itemname);
    }

    /**
     * Move all items from the currrent entity to specified one
     */
    public void moveAllTo(Entity entity)
    {
        ArrayList<Item> dest = entity.getItemAL();
        ArrayList<String> itemS = new ArrayList<>();

        for (Item curitem : items)   // Add the names of all items here to an array
        {
            if (curitem.getItemType() != "Fixed")
            {
                itemS.add(curitem.getItemName());
            }
        }

        for (String itemname : itemS)  // Move each item here to the desination
        {
            moveItemTo(entity, itemname);
        }
    }

    /**
     * Return how many items of a certain type the entity has
     */
    public int returnNumberofType(String itemtype)
    {
        int counter = 0;

        for (Item cur : items)
        {
            if (cur.getItemType().contains(itemtype))
            {
                counter++;
            }
        }

        return counter;
    }

}
