package Entity.Item;

/**
 * Models in-game items protected by enemies
 *
 * @author David Charkey
 */
public class ProtectedItem extends Item
{
  
    // Info
    private String protector;

    public ProtectedItem(String name, String[] infoArr, String protector, int col, int row)
    {
        super(name, infoArr, col, row);
        
        this.protector = protector;
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
