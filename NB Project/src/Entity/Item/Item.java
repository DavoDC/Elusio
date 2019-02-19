package Entity.Item;

import Entity.Base.Entity;

/**
 * Models in-game items
 *
 * @author David Charkey
 */
public class Item extends Entity
{
    
    // Info
    private String properties;
    private String use;
    private String protector;
    
    // Discovery Status
    private boolean isFound;
    
    /**
     * Default constructor = Don't use
     * @param name
     * @param tlc
     * @param tlr
     * @param w
     * @param h 
     */
    private Item(String name, int tlc, int tlr, int w, int h)
    {
        super(name, tlc, tlr, w, h);
    }
    

    /**
     * Create a magic item
     * @param name Name
     * @param properties e.g. "collect energy"
     * @param use e.g. "destroy things"
     * @param protector Enemy name or "none"
     * @param col Column on tile grid
     * @param row Row on tile grid
     */
    public Item(
            String name, 
            String properties, 
            String use, 
            String protector,
            int col, 
            int row)
    {
        super(name, col, row, 1, 1);
        this.properties = properties;
        this.protector = protector.toLowerCase();
        this.use = use;
        isFound = false;
    }
    
    /**
     * Get properties
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

    /**
     * Get discovery status
     * @return 
     */
    public boolean isFound()
    {
        return isFound;
    }
    
    /**
     * Set discovery status
     * @param newStatus 
     */
    public void setFound(boolean newStatus)
    {
        isFound = newStatus;
    }
    
    /**
     * Get protector name
     * @return 
     */
    public String getProtector()
    {
        return protector;
    }
}
