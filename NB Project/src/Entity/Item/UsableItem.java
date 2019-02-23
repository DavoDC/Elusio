/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Item;

/**
 * items activated by pressing E
 * @author CHARKEYD
 */
public class UsableItem extends Item
{
    // usage text
    private String useLine;
    
    public UsableItem(String name, String info1, String info2,  String useLine, int c, int r)
    {
        super(name, info1, info2, c, r);
        
        this.useLine = useLine;
    }
    
    
    public String getUseLine()
    {
        return useLine;
    }
}
