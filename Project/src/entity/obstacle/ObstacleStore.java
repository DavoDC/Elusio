package entity.obstacle;

import java.util.ArrayList;

import main.Globals;
import components.popups.Popup;
import components.structures.Map;
import components.structures.Player;
import entity.base.Entity;
import entity.base.EntityStore;

/**
 * Handles obstacles
 *
 * @author David
 */
public class ObstacleStore extends EntityStore {

    // Count of crystals placed
    private int crystalsPlaced = 0;

    /**
     * Get the entities of the Obstacle Store
     *
     * @return
     */
    @Override
    public ArrayList<Entity> getEntities() {

        // Initialize list
        ArrayList<Entity> obstacles = new ArrayList<>();

        // Trees = Initial Area
        //  Add Trees locked by TreeZone
        obstacles.add(new Obstacle("Trees", "TreeZone", 32, 9, 5, 5, true));
        //  Add TreeZone locked by Cryocapacitor
        obstacles.add(new ObstacleZone("TreeZone", "Cryocap", 29, 7, 6, 6));

        // Limestone = Initial Area
        //  Add LimeStone locked by LimeZone
        obstacles.add(new Obstacle("LimeStone", "LimeZone", 1, 37, 5, 2, true));
        //  Add LimeZone locked by Acid
        obstacles.add(new ObstacleZone("LimeZone", "Acid", 2, 34, 2, 3));

        // Add four crystals that lock gate
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

        // Add sail Ship
        obstacles.add(new Obstacle("Water", "ShipSide", 13, 73, 6, 7, true));
        obstacles.add(new Obstacle("Ship", "ShipSide", 10, 74, 3, 6, true));
        obstacles.add(new ObstacleZone("ShipSide", "Magistruct", 19, 73, 5, 8));

        // Add alien ship
        obstacles.add(new ObstacleZone("AlienShip", "ShipGold", 4, 2, 9, 7) {
            @Override
            public void afterAction() {
                Globals.hud.loadPopup(getEndPopup());
            }
        }
        );

        return obstacles;
    }

    /**
     * Return the final popup
     *
     * @return
     */
    private Popup getEndPopup() {

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
        Popup finish = new Popup(feats, textLines) {
            @Override
            public void postAction() {
                int exitID = Globals.STATES.get("EXIT");
                Globals.SBG.enterState(exitID, Globals.getLeave(), Globals.getEnter());
            }
        };

        // Return
        return finish;
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
            if (isEntityUnder(alien, curEnt) && (curEnt instanceof ObstacleZone)) {

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

    /**
     * Unblock magic gate when 4 crystals placed
     */
    public void crystalPlaced() {

        // Increase crystal count
        crystalsPlaced++;

        // If all crystals have been placed
        if (crystalsPlaced == 4) {

            // Get all obstacles
            ArrayList<Entity> entities = super.getEntityList();

            // For all obstacles
            for (Entity curEnt : entities) {

                // If the entity is an obstacle AND has Gate in its name
                if ((curEnt instanceof Obstacle) && (curEnt.getName().contains("Gate"))) {

                    // Unblock one of the FOUR magic gate parts
                    Globals.map.unblockEntity(curEnt);

                    // Show magic gate popup
                    Globals.hud.loadPopup(getGatePopup());
                }
            }
        }
    }

    /**
     * Get popup for when magic gate unlocks
     *
     * @return
     */
    private Popup getGatePopup() {

        // Calculate actual position
        int camRadj = 3;
        int camCadj = 2;
        int camYadj = camRadj * Globals.tileSize;
        int camXadj = camCadj * Globals.tileSize;
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
