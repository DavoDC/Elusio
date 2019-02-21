package Entity.Item;

import Entity.Base.Entity;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Models in-game items
 *
 * @author David Charkey
 */
public class Item extends Entity
{
    
    // Info
    private ArrayList<String> info;
    
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
     * Create a item
     * @param name Item name
     * @param infoArr Item information
     * @param col Column on tile grid
     * @param row Row on tile grid
     */
    public Item(String name, String[] infoArr, int col, int row)
    {
        super(name, col, row, 1, 1);
        
        info = new ArrayList<>();
        info.addAll(Arrays.asList(infoArr));
        
        isFound = false;
    }
    
    /**
     * Get info lines
     * @return 
     */
    public ArrayList<String> getInfoLines()
    {
        return info;
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
    
}
