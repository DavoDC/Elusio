package Entity.Obstacle;

import Components.Structures.Player;
import Entity.Base.Entity;
import Entity.Base.EntityStore;
import Main.Globals;

import java.util.ArrayList;

/**
 * Handles obstacles
 *
 * @author David Charkey
 */
public class ObstacleStore extends EntityStore
{

    // Crystals placed
    int crystalsPlaced = 0;

    @Override
    public ArrayList<Entity> getEntities()
    {
        ArrayList<Entity> obstacles = new ArrayList<>();

        // Trees that enclose initial area
        obstacles.add(new Obstacle("Trees", "TreeZone", 32, 9, 5, 5, true));
        obstacles.add(new ObstacleZone("TreeZone", "Cryocap", 29, 7, 6, 6));

        // Crystal placement to open gate
        obstacles.add(new Obstacle("HiGate", "HiSpot", 82, 9, 1, 1, false));
        obstacles.add(new Obstacle("HiCrystal", "HiSpot", 82, 14, 1, 1, false));
        obstacles.add(new ObstacleZone("HiSpot", "Crystal12", 82, 14, 1, 1));

        obstacles.add(new Obstacle("LoGate", "LoSpot", 82, 10, 1, 1, false));
        obstacles.add(new Obstacle("LoCrystal", "LoSpot", 82, 16, 1, 1, false));
        obstacles.add(new ObstacleZone("LoSpot", "Crystal3", 82, 16, 1, 1));

        obstacles.add(new Obstacle("LeftGate", "LeftSpot", 81, 9, 1, 2, false));
        obstacles.add(new Obstacle("LeftCrystal", "LeftSpot", 81, 15, 1, 1, false));
        obstacles.add(new ObstacleZone("LeftSpot", "Crystal6", 81, 15, 1, 1));

        obstacles.add(new Obstacle("RightGate", "RightSpot", 83, 9, 1, 2, false));
        obstacles.add(new Obstacle("RightCrystal", "RightSpot", 83, 15, 1, 1, false));
        obstacles.add(new ObstacleZone("RightSpot", "Crystal9", 83, 15, 1, 1));

        return obstacles;
    }

    @Override
    public String getEntLS()
    {
        return "Obstacles";
    }

    @Override
    public String getHideLS()
    {
        return "ObHide";
    }

    /**
     * Get obstacle zone under player
     *
     * @param alien
     * @return OZ, or null if nothing/non-item
     */
    public ObstacleZone getZoneUnder(Player alien)
    {
        // For all entities
        for (Entity curEnt : getEntityList())
        {
            // If under player
            if (isEntityUnder(alien, curEnt))
            {
                // If an obstacle zone
                if (curEnt instanceof ObstacleZone)
                {
                    return (ObstacleZone) curEnt;
                }
            }
        }

        // Return default
        return null;
    }

    public ArrayList<Obstacle> getMatchingObstacles(ObstacleZone obZone)
    {
        String obZoneName = obZone.getName();
        ArrayList<Obstacle> matches = new ArrayList<>();

        // For all obstacles
        for (Entity curEnt : super.getEntityList())
        {
            // If is an obstacle, not zone
            if (curEnt instanceof Obstacle)
            {
                // Get the name of the zone in the obstacle
                String curObstaclesZone = ((Obstacle) curEnt).getOZName();

                // Compare current zone to current obstacle's zone
                if (curObstaclesZone.contains(obZoneName))
                {
                    // Add if a match
                    matches.add(((Obstacle) curEnt));
                }
            }
        }

        return matches;
    }

    /**
     * Unblock gate when 4 crystals placed
     */
    public void crystalPlaced()
    {
        crystalsPlaced++;

        if (crystalsPlaced == 4)
        {
            ArrayList<Entity> entities = super.getEntityList();
            for (Entity curEnt : entities)
            {
                if (curEnt instanceof Obstacle)
                {
                    if (curEnt.getName().contains("Gate"))
                    {
                        Globals.map.unblockEntity(curEnt);
                    }
                }
            }
        }
    }

}
