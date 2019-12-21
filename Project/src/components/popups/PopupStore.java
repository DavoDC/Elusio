package components.popups;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import main.Globals;

import org.newdawn.slick.Graphics;

/**
 * Handles the displaying of popups
 *
 * @author David
 */
public class PopupStore {

    // Maps names to popups
    private final HashMap<String, Popup> popMap;

    // Current popup variables
    private Popup curPopup;
    private boolean shouldShowPopup;

    /**
     * Create a popup displayer
     */
    public PopupStore() {

        // Initialize popup map
        popMap = new HashMap<>();

        // Add popups
        // Add popup for introductory Rift
        integratePopup(new Popup(
                "RiftIntro",
                new String[]{
                    // Both come back to spirit world
                    "Nagual:We've arrived back at the nonspatial rift, "
                    + "Tonal",
                    // Spirit world is nice, Body asks soul for instructions
                    "Tonal: Its always a calming place. "
                    + "What is our next objective?",
                    // Soul wants to improve the soul-brain interface
                    "Nagual:We need to re-modulate our encepha-interface "
                    + "to ascend vibrationally",
                    // Soul knows more than mind
                    "Tonal:I think I know what you mean.. "
                    + "How will we do that?",
                    // Soul encourages incarnation for development
                    "Nagual:I know just the locality needed! "
                    + "The hylentangler is now resynchronizing..."
                }) {
            @Override
            public void doPostShowAction() {

                // Disable input
                Globals.isInputBlocked = true;

                // Intro has completed
                Globals.isIntroRiftDone = true;

                // Go to actual play state
                Globals.changeState("Play", true);

                // Schedule ship coming back
                long shipAppear = 3369;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        // Show alien ship
                        Globals.obStore.removeEncounterByName("StartAlien");
                    }
                }, shipAppear);

                // Schedule player becoming visible
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        // Player becomes visible 
                        Globals.player.setVisible(true);

                    }
                }, shipAppear + 2000);

                // Schedule player walking down
                Timer playerMove = new Timer();
                playerMove.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {

                        // Player moves down
                        Globals.player.move("Down", 20);
                    }
                }, shipAppear + 3000, 60);

                // Schedule player stopping and popup loading
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        // Player stops moving
                        playerMove.cancel();
                        Globals.player.stopAnim();

                        // Load next popup
                        // Globals.popStore.loadPopup("PlayIntro");
                    }
                }, shipAppear + 4100);
            }
        }, false);

//        // Add popup displayed after leaving introductory Rift
//        popMap.put("PlayIntro", new Popup(
//                new String[]{
//                    "Nagual:Resynchronization complete!",
//                    "Tonal:What strange vibrations this place has. "
//                    + "Where are we?",
//                    "Nagual:A diverse dimension with a vast array of "
//                    + "manifestations for you to experience",
//                    "Tonal: My form feels... different. "
//                    + "I need some guidance",
//                    "Nagual: Of course. "
//                }) {
//            @Override
//            public void doPostShowAction() {
//
//                // Save player's direction
//                String prevDir = Globals.player.getLastDir();
//
//                // Enable player to move
//                Globals.isInputBlocked = false;
//
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//
//                        // If player has moved
//                        if (!prevDir.equals(Globals.player.getLastDir())) {
//                            // Load next popup
//                        }
//                    }
//                }, 1000, 1000);
//            }
//        }
//        );
    }

    /**
     * Integrate a popup into the popup map
     *
     * @param p
     */
    private void integratePopup(Popup p, boolean isInputBlocker) {

        // Adjust popup
        p.setInputBlocking(isInputBlocker);

        // Add popup with name
        popMap.put(p.getName(), p);
    }

    /**
     * Load in a popup for showing
     *
     * @param popupName
     */
    public void loadPopup(String popupName) {

        // Save popup 
        curPopup = popMap.get(popupName);

        // If popup found was null
        if (curPopup == null) {
            // Throw error
            throw new IllegalArgumentException("loadPopup query issue");
        }

        // Request that it is shown
        shouldShowPopup = true;
    }

    /**
     * Renders current popup
     *
     * @param g
     */
    public void renderCurPopup(Graphics g) {

        // If popup is null
        if (curPopup == null) {

            // Do not try to render
            return;
        }

        // If a popup should be shown
        if (shouldShowPopup) {

            // If popup is input blocker
            if (curPopup.isInputBlocker()) {

                // Block input
                Globals.isInputBlocked = true;
            }

            // Make current popup visible
            curPopup.setVisible(true);

            // The popup has now been shown
            shouldShowPopup = false;
        }

        // Render current popup
        curPopup.render(g);
    }

//
//
//
//
//                // Calculate adjustment from camera
//                int camRadj = 3;
//                int camCadj = 2;
//                int camYadj = camRadj * Globals.tileSize;
//                int camXadj = camCadj * Globals.tileSize;
//
//                // Calculate actual position
//                int r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
//                int c = Map.convertXtoCol(Globals.cam.getX() + camXadj);
//
//                // Show popups after item use
//                System.out.println("#### TEST:  " + obst.getName()
//                        + " , " + obst.getOZName());
//                showUsagePopup(obN, "Tree", "Cryo", r, c);
//                showUsagePopup(obN, "Lime", "Acid", r, c);
//                showUsagePopup(obN, "Water", "Orb", r, c);
//    /**
//     * Show information about an item as a popup
//     *
//     * @param item
//     */
//    private void showItemPopup(Item item) {
//
//        // Calculate adjustment from camera
//        int camRadj = 3;
//        int camCadj = 2;
//        int camYadj = camRadj * Globals.tileSize;
//        int camXadj = camCadj * Globals.tileSize;
//
//        // Calculate actual position
//        int r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
//        int c = Map.convertXtoCol(Globals.cam.getX() + camXadj);
//
//        // Account for special teleportation cases
//        String name = item.getName().toLowerCase();
//        if (name.contains("clock")) {
//            r = Map.convertYtoRow(Globals.cam.getY() + camYadj - 34 * 64);
//            c = Map.convertXtoCol(Globals.cam.getX() + camXadj);
//        } else if (name.contains("treasure")) {
//            r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
//            c = Map.convertXtoCol(Globals.cam.getX() + camXadj + 6 * 64);
//            item.afterAction();
//        }
//
//        // Generate and show popup
//        Popup itemInfo = getItemPopup(item, r, c);
//        Globals.hud.loadPopup(itemInfo);
//    }
//
//    /**
//     * Get a popup that describes a given item
//     *
//     * @param item
//     * @param r
//     * @param c
//     * @return
//     */
//    public Popup getItemPopup(Item item, int r, int c) {
//
//        // Set features of popup
//        ArrayList<Object> feats = new ArrayList<>();
//        feats.add(r);  // Tile grid row
//        feats.add(c);  // Tile grid column 
//        feats.add(18); // Width as number of tiles 
//        feats.add(2);  // Height as number of tiles 
//        feats.add(20); // Interval for delay writer
//        feats.add("default"); // FontS or "default"
//        feats.add(Color.black); // Text color
//
//        // Create popup lines 
//        ArrayList<String> itemLines = item.getInfoLines();
//        ArrayList<String> newLines = new ArrayList<>();
//        String start = "(Ehecatl, telepathically): ";
//        for (String curItemLine : itemLines) {
//            newLines.add(start + curItemLine);
//        }
//        newLines.add("(You, telepathically): Thanks for the info, Ehecatl!");
//
//        // Return
//        return (new Popup(feats, newLines));
//    }
//
//
//    /**
//     * Show usable item popup after it is grabbed
//     *
//     * @param obN The obstacle's full name
//     * @param obQ Part of the obstacle's name
//     * @param itemName Item name subset
//     * @param r Row position of popup
//     * @param c Column position of popup
//     */
//    private void showUsagePopup(String obN, String obQ, String itemName, int r, int c) {
//
//        // If obstacle name contains query
//        if (obN.contains(obQ)) {
//
//            // Get item
//            UsableItem item = (UsableItem) Globals.player.getItemByName(itemName);
//
//            // Load popup for item
//            Globals.hud.loadPopup(getUsagePopup(item, r, c));
//        }
//    }
//
//    /**
//     * Get a popup describing what a usable item just did
//     *
//     * @param item
//     * @param r
//     * @param c
//     * @return
//     */
//    public Popup getUsagePopup(Item item, int r, int c) {
//
//        // Set features of popup
//        ArrayList<Object> feats = new ArrayList<>();
//        feats.add(r);  // Tile grid row
//        feats.add(c);  // Tile grid column 
//        feats.add(18); // Width as number of tiles 
//        feats.add(2);  // Height as number of tiles 
//        feats.add(20); // Interval for delay writer
//        feats.add("default"); // FontS or "default"
//        feats.add(Color.black); // Text color
//
//        // Create popup lines 
//        ArrayList<String> newLines = new ArrayList<>();
//        String start = "(Ehecatl, telepathically): ";
//        newLines.add(start + ((UsableItem) item).getUseLine());
//        newLines.add("(You, telepathically): Thanks for the info, Ehecatl!");
//
//        // Return
//        return (new Popup(feats, newLines));
//    }
//      /**
//     * Return the final popup
//     *
//     * @return
//     */
//    private Popup getEndPopup() {
//
//        // Features
//        ArrayList<Object> feats = new ArrayList<>();
//        feats.add(8);  // Tile grid row
//        feats.add(2);  // Tile grid column 
//        feats.add(17); // Width as number of tiles 
//        feats.add(2);  // Height as number of tiles 
//        feats.add(20); // Interval for delay writer
//        feats.add("default"); // FontS or "default"
//
//        // Text
//        ArrayList<String> textLines = new ArrayList<>();
//        textLines.add("You: I've got the elecrovelox!");
////        textLines.add("Ehecatl: Thats great! I'll begin repairs");
////        textLines.add("You: You wouldn't believe what I went through to get it!");
////        textLines.add("You: I even encountered a dragon!");
////        textLines.add("Ehecatl: No way!");
////        textLines.add("Narrator: And thats how Ehecatl and Nagual got home");
////        textLines.add("Narrator: But this was a memory they'd never forget");
////        textLines.add("Le Fin");
//
//        // Create
//        Popup finish = new Popup(feats, textLines) {
//            @Override
//            public void postAction() {
//                int exitID = Globals.STATES.get("EXIT");
//                Globals.SBG.enterState(exitID, Globals.getLeave(), Globals.getEnter());
//            }
//        };
//
//        // Return
//        return finish;
//    }
    /**
     * // * Get popup for when magic gate unlocks // * // * @return //
     */
//    private Popup getGatePopup() {
//
//        // Create popup lines 
//        ArrayList<String> newLines = new ArrayList<>();
//        String start = "(Ehecatl, telepathically): ";
//        newLines.add(start + "I've just detected psionic activity near that gate!");
//        newLines.add(start + "Felt like an etherealise spell");
//        newLines.add(start + "You should investigate it!");
//        newLines.add("(You, telepathically): Thanks for the info, Ehecatl!");
//
//        // Return
//        return (new Popup(feats, newLines));
//    }
}
