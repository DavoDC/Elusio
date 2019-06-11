package Entity.Obstacle;

import Components.Popups.Popup;
import Components.Structures.Map;
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
    private int crystalsPlaced = 0;

    @Override
    public ArrayList<Entity> getEntities()
    {
        ArrayList<Entity> obstacles = new ArrayList<>();

        // Trees = Initial Area
        obstacles.add(new Obstacle("Trees", "TreeZone", 32, 9, 5, 5, true));
        obstacles.add(new ObstacleZone("TreeZone", "Cryocap", 29, 7, 6, 6));

        // Limestone = Initial Area
        obstacles.add(new Obstacle("LimeStone", "LimeZone", 1, 37, 5, 2, true));
        obstacles.add(new ObstacleZone("LimeZone", "Acid", 2, 34, 2, 3));

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

        // Sail Ship
        obstacles.add(new Obstacle("Water", "ShipSide", 13, 73, 6, 7, true));
        obstacles.add(new Obstacle("Ship", "ShipSide", 10, 74, 3, 6, true));
        obstacles.add(new ObstacleZone("ShipSide", "Magistruct", 19, 73, 5, 8));

        // Alien ship
        obstacles.add(new ObstacleZone("AlienShip", "ShipGold", 4, 2, 9, 7)
        {
            @Override
            public void afterAction()
            {
                Globals.hud.loadPopup(getEndPopup());
            }
        }
        );

        return obstacles;
    }

    private Popup getEndPopup()
    {
        // Features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(8);  // Tile grid row
        feats.add(2);  // Tile grid column 
        feats.add(17); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"

        // Text
        ArrayList<String> textLines = new ArrayList<>();
        textLines.add("You: I've got the elecrovelox!");
        textLines.add("Ehecatl: Thats great! I'll begin repairs");
        textLines.add("You: You wouldn't believe what I went through to get it!");
        textLines.add("You: I even encountered a dragon!");
        textLines.add("Ehecatl: No way!");
        textLines.add("Narrator: And thats how Ehecatl and Nagual got home");
        textLines.add("Narrator: But this was a memory they'd never forget");
        textLines.add("Le Fin");

        // Create
        Popup finish = new Popup(feats, textLines)
        {
            @Override
            public void action()
            {
                int exitID = Globals.STATES.get("EXIT");
                Globals.SBG.enterState(exitID, Globals.getLeave(), Globals.getEnter());
            }
        };

        // Return
        return finish;
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
            // If under player AND if an obstacle zone
            if (isEntityUnder(alien, curEnt) && (curEnt instanceof ObstacleZone))
            {
                return (ObstacleZone) curEnt;
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
                // If is entity is an obstacle AND has Gate in name
                if ((curEnt instanceof Obstacle) && (curEnt.getName().contains("Gate")))
                {
                    Globals.map.unblockEntity(curEnt);
                    Globals.hud.loadPopup(getGatePopup());
                }
            }
        }
    }

    private Popup getGatePopup()
    {
        // Calculate 
        int camRadj = 3;
        int camCadj = 2;
        int camYadj = camRadj * Globals.tileSize;
        int camXadj = camCadj * Globals.tileSize;

        // Calculate actual position
        int r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
        int c = Map.convertXtoCol(Globals.cam.getX() + camXadj);

        // Set features of popup
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(r);  // Tile grid row
        feats.add(c);  // Tile grid column 
        feats.add(18); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"

        // Create popup lines 
        ArrayList<String> newLines = new ArrayList<>();
        String start = "(Ehecatl, telepathically): ";
        newLines.add(start + "I've just detected psionic activity near that gate!");
        newLines.add(start + "Felt like an etherealise spell");
        newLines.add(start + "You should investigate it!");
        newLines.add("(You, telepathically): Thanks for the info, Ehecatl!");

        // Return
        return (new Popup(feats, newLines));
    }

}
