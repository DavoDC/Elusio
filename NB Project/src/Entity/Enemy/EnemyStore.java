package Entity.Enemy;

import Components.Structures.Player;
import Entity.Base.Entity;
import Entity.Base.EntityStore;
import Main.Globals;
import java.util.ArrayList;

/**
 * Handles a group of enemies
 * @author David Charkey
 */
public class EnemyStore extends EntityStore
{

    @Override
    public ArrayList<Entity> getEntities()
    {
        ArrayList<Entity> enemies = new ArrayList<>();
        
        enemies.add( new Enemy("Mycovolence", 72, 80));
        enemies.add( new Enemy("Trevil", 90, 40));
        enemies.add( new Enemy("Viridash", 31, 32));

        return enemies;
    }

    @Override
    public String getEntityLayerName()
    {
        return "Enemies";
    }

    @Override
    public Entity getLastInteractedEntity(Player player)
    {
        return player.getLastEnemy();
    }

    @Override
    public boolean getEntityInteractionStatus()
    {
        return Globals.enemyEnc;
    }

    @Override
    public void switchEntityInteractionStatus()
    {
        Globals.enemyEnc = !Globals.enemyEnc;
    }
    
    /**
     * Get an enemy by name
     * @param name
     * @return Enemy, or null
     */
    public Enemy getEnemy(String name)
    {
        name = name.toLowerCase();
                
        Enemy enemy = null;
        for (Entity curEnt : getEntityList())
        {
            if (curEnt.getName().toLowerCase().equals(name))
            {
                enemy = (Enemy) curEnt;
                break;
            }
        }
        
        return enemy;
    }
    
}
