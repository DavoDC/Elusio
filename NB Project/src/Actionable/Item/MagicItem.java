package Actionable.Item;

import Actionable.Base.Actionable;

/**
 * Models in-game items
 *
 * @author David Charkey
 */
public class MagicItem extends Item
{
    // Info
    private String properties;
    private String use;
    
    /**
     * Create a magic item
     * @param name Name
     * @param properties e.g. "collect energy"
     * @param use e.g. "destroy things"
     * @param col Column on tile grid
     * @param row Row on tile grid
     */
    public MagicItem(String name, String properties, String use, int col, int row)
    {
        super(name, col, row);
        
        this.properties = properties;
        this.use = use;
    }
    
    /**
     * Get magic properties
     * @return 
     */
    public String getProp()
    {
        return properties;
    }
    
    /**
     * Get use
     * @return 
     */
    public String getUse()
    {
        return use;
    }

}
