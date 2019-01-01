
/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * This class models in-game items
 * They can be created with a type, or without
 * 
 * @author David Charkey 
 */
public class Item
{
    private String type;
    private String name;
    private String description;

    /**
     * Constructor for general items
     */
    public Item( String name, String description)
    {
        type = "General";
        this.name = name;
        this.description = description;
    }

    /**
     * Constructor for items with a type
     */
    public Item( String name, String description, String type )
    {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    /**
     * Get information about this item, including the item's name and description
     */
    public String getInformation()
    {
        String info = " ";

        info += (name + " , " + description);

        return info;
    }

    /**
     * Return the name of this item , as is
     */
    public String getItemName()
    {
        return name;
    }

    /**
     * Return what type of item this is, as is
     */
    public String getItemType()
    {
        return type;
    }

}
