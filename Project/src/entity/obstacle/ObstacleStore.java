package entity.obstacle;

import java.util.ArrayList;

import components.modules.Player;
import entity.base.Entity;
import entity.base.EntityStore;

/**
 * Handles obstacles
 *
 * @author David
 */
public class ObstacleStore extends EntityStore {

    /**
     * Get the entities of the Obstacle Store
     *
     * @return
     */
    @Override
    public ArrayList<Entity> getEntities() {

        // Initialize list
        ArrayList<Entity> obstacles = new ArrayList<>();

        // Alien Ship
        obstacles.add(new Obstacle("StartAlienShip", "", 5, 2, 4, 4, true));
        obstacles.add(new ObstacleZone("EndAlienShip", "Treasure", 4, 2, 9, 7));

        // Starting Area Trees
        obstacles.add(new Obstacle("Trees", "TreeZone", 32, 9, 5, 5, true));
        obstacles.add(new ObstacleZone("TreeZone", "Cryocapacitor", 31, 8, 7, 7));

        // Starting Area Limestone
        obstacles.add(new Obstacle("LimeStone", "LimeZone", 1, 37, 5, 2, true));
        obstacles.add(new ObstacleZone("LimeZone", "Gastric", 1, 36, 6, 4));

        // Magic Gate
        obstacles.add(new Obstacle("HiGate", "HiSpot", 82, 9, 1, 1, false));
        obstacles.add(new Obstacle("HiCrystal", "HiSpot", 82, 14, 1, 1, false));
        obstacles.add(new ObstacleZone("HiSpot", "Destiny", 82, 14, 1, 1));

        obstacles.add(new Obstacle("LoGate", "LoSpot", 82, 10, 1, 1, false));
        obstacles.add(new Obstacle("LoCrystal", "LoSpot", 82, 16, 1, 1, false));
        obstacles.add(new ObstacleZone("LoSpot", "Protection", 82, 16, 1, 1));

        obstacles.add(new Obstacle("LeftGate", "LeftSpot", 81, 9, 1, 2, false));
        obstacles.add(new Obstacle("LeftCrystal", "LeftSpot", 81, 15, 1, 1, false));
        obstacles.add(new ObstacleZone("LeftSpot", "Alignment", 81, 15, 1, 1));

        obstacles.add(new Obstacle("RightGate", "RightSpot", 83, 9, 1, 2, false));
        obstacles.add(new Obstacle("RightCrystal", "RightSpot", 83, 15, 1, 1, false));
        obstacles.add(new ObstacleZone("RightSpot", "Growth", 83, 15, 1, 1));

        // Sailing Ship
        obstacles.add(new Obstacle("Water", "ShipSide", 13, 73, 6, 7, true));
        obstacles.add(new Obstacle("Ship", "ShipSide", 10, 74, 3, 6, true));
        obstacles.add(new ObstacleZone("ShipSide", "Magistructor", 19, 73, 5, 8));

        return obstacles;
    }

    /**
     * Set the name of the layer where the entities are
     *
     * @return
     */
    @Override
    public String getEntLS() {
        return "Obstacles";
    }

    /**
     * Set the name of the layer to hide the entity
     *
     * @return
     */
    @Override
    public String getHideLS() {
        return "ObHide";
    }

    /**
     * Get obstacle zone under player
     *
     * @param alien
     * @return Obstacle Zone, or null if nothing found
     */
    public ObstacleZone getZoneUnder(Player alien) {

        // For all entities
        for (Entity curEnt : getEntityList()) {

            // If the entity is obstacle zone under the player
            if (isEntityUnder(alien, curEnt, true) 
                    && (curEnt instanceof ObstacleZone)) {

                // Return obstacle zone
                return (ObstacleZone) curEnt;
            }
        }

        // Return null as nothing found
        return null;
    }

    /**
     * Get all obstacles linked to a given obstacle zone
     *
     * @param obZone
     * @return
     */
    public ArrayList<Obstacle> getLinkedObstacles(ObstacleZone obZone) {

        // Get zone name
        String obZoneName = obZone.getName();

        // Initialize list
        ArrayList<Obstacle> matches = new ArrayList<>();

        // For all entities
        for (Entity curEnt : super.getEntityList()) {

            // If entity is an obstacle
            if (curEnt instanceof Obstacle) {

                // Get the obstacle zone of the current obstacle
                String curObstaclesZone = ((Obstacle) curEnt).getOZName();

                // If the obstacle possesses the obZone given
                if (curObstaclesZone.contains(obZoneName)) {

                    // Add to the list of obstacles
                    matches.add(((Obstacle) curEnt));
                }
            }
        }

        return matches;
    }
}
