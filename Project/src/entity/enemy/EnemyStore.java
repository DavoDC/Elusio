package entity.enemy;

import java.util.ArrayList;

import entity.base.Entity;
import entity.base.Entity;
import entity.base.EntityStore;
import entity.base.EntityStore;

/**
 * Handles a group of enemies
 *
 * @author David
 */
public class EnemyStore extends EntityStore {

    /**
     * Create list of all enemies
     *
     * @return
     */
    @Override
    public ArrayList<Entity> getEntities() {
        ArrayList<Entity> enemies = new ArrayList<>();

        // Mycovolence
        enemies.add(new Enemy("Mycovolence", 72, 80) {
            @Override
            public void doAction() {
                //Globals.SBG.enterState(Globals.STATES.get("CHALLENGE"));
            }
        });

        // Trevil
        enemies.add(new Enemy("Trevil", 90, 40) {
            @Override
            public void doAction() {
                //Globals.SBG.enterState(Globals.STATES.get("CHALLENGE"));
            }
        });

        // Viridash
        enemies.add(new Enemy("Viridash", 31, 32) {
            @Override
            public void doAction() {
                //Globals.SBG.enterState(Globals.STATES.get("CHALLENGE"));
            }
        });

        // Ship
        enemies.add(new Enemy("Ship", 11, 76) {
            @Override
            public void doAction() {
                //Globals.SBG.enterState(Globals.STATES.get("CHALLENGE"));
            }
        });

        return enemies;
    }

    /**
     * Get layer holding enemy entities
     *
     * @return
     */
    @Override
    public String getEntLS() {
        return "Enemies";
    }

    /**
     * Get an enemy by its exact name, ignoring case
     *
     * @param name
     * @return Enemy, or null
     */
    public Enemy getEnemy(String name) {

        // For all enemies
        for (Entity curEnt : getEntityList()) {

            // If current enemy name matches given name
            if (curEnt.getName().equalsIgnoreCase(name)) {

                // Return enemy
                return (Enemy) curEnt;
            }
        }

        // If no enemy was found, return null
        return null;
    }

}
