package Entity.Enemy;

import Entity.Base.Entity;
import Entity.Base.EntityStore;
import java.util.ArrayList;

/**
 * Handles a group of enemies
 *
 * @author David Charkey
 */
public class EnemyStore extends EntityStore
{

    @Override
    public ArrayList<Entity> getEntities()
    {
        ArrayList<Entity> enemies = new ArrayList<>();

        // Mycovolence
        enemies.add(new Enemy("Mycovolence", 72, 80)
        {
            @Override
            public void doAction()
            {
                //Globals.SBG.enterState(Globals.STATES.get("CHALLENGE"));
            }
        });

        // Trevil
        enemies.add(new Enemy("Trevil", 90, 40)
        {
            @Override
            public void doAction()
            {
                //Globals.SBG.enterState(Globals.STATES.get("CHALLENGE"));
            }
        });

        // Viridash
        enemies.add(new Enemy("Viridash", 31, 32)
        {
            @Override
            public void doAction()
            {
                //Globals.SBG.enterState(Globals.STATES.get("CHALLENGE"));
            }
        });

        // Ship
        enemies.add(new Enemy("Ship", 11, 76)
        {
            @Override
            public void doAction()
            {
                //Globals.SBG.enterState(Globals.STATES.get("CHALLENGE"));
            }
        });

        return enemies;
    }

    @Override
    public String getEntLS()
    {
        return "Enemies";
    }

    /**
     * Get an enemy by name
     *
     * @param name
     * @return Enemy, or null
     */
    public Enemy getEnemy(String name)
    {
        Enemy enemy = null;
        for (Entity curEnt : getEntityList())
        {
            if (curEnt.getName().equalsIgnoreCase(name))
            {
                enemy = (Enemy) curEnt;
                break;
            }
        }

        return enemy;
    }

}
