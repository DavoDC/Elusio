package components.popups;

import java.util.ArrayList;

import main.Globals;
import org.newdawn.slick.Color;

import org.newdawn.slick.Graphics;

/**
 * Handles the displaying of popups
 *
 * @author David
 */
public class PopupDisplayer {

    // Special popup variables
    private Popup curPopup;
    private boolean shouldShowPopup;

    /**
     * Create a popup displayer
     */
    public PopupDisplayer() {

        // Initialize with intro popup
        curPopup = getIntroPopup();

        // Show popup
        shouldShowPopup = true;
    }

    /**
     * Get the the intro popup
     *
     * @return
     */
    private Popup getIntroPopup() {

        // Features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(8);  // Tile grid row
        feats.add(2);  // Tile grid column 
        feats.add(17); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"
        feats.add(Color.black); // Text color

        // Text
        ArrayList<String> textLines = new ArrayList<>();
        textLines.add("You: Argh ... my head ... Where am I?");
        textLines.add("Ehecatl: We appear to have materialised in Elusio. There was...");
        textLines.add("You: Ehecatl! You surprised me! I am so glad you are still alive!");
        textLines.add("Ehecatl: I do not dissipate that easily, my friend");
        textLines.add("You: What happened to you?");
        textLines.add("Ehecatl: Something disturbed my phase shift inhibitor");
        textLines.add("You: Perhaps something similar to electrovelox could help....");
        textLines.add("Ehecatl: Good idea! There should be some in this diverse dimension.");
        textLines.add("Ehecatl: I will help you navigate it. Listen closely to my messages");

        // Return
        return (new Popup(feats, textLines));
    }

    /**
     * Manage loaded popups
     */
    public void updatePD() {

        // If not in IDE
        if (Globals.inIDE) {

            // Never show popups for faster testing
            shouldShowPopup = false;
        }

        // If a popup should be shown
        if (shouldShowPopup) {

            // Disable input
            Globals.inputIgnored = true;

            // Make current popup visible
            curPopup.setVisible(true);

            // A popup should not be shown
            shouldShowPopup = false;
        }
    }

    /**
     * Renders current popup
     *
     * @param g
     */
    public void renderPopups(Graphics g) {

        // Render current popup
        curPopup.show(g);
    }

    /**
     * Load a popup for displayer
     *
     * @param newPopup
     */
    public void loadPopup(Popup newPopup) {

        // Save popup and activate the showing of it
        curPopup = newPopup;
        shouldShowPopup = true;
    }

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
//        // Calculate actual position
//        int camRadj = 3;
//        int camCadj = 2;
//        int camYadj = camRadj * Globals.tileSize;
//        int camXadj = camCadj * Globals.tileSize;
//        int r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
//        int c = Map.convertXtoCol(Globals.cam.getX() + camXadj);
//
//        // Set features of popup
//        ArrayList<Object> feats = new ArrayList<>();
//        feats.add(r);  // Tile grid row
//        feats.add(c);  // Tile grid column 
//        feats.add(18); // Width as number of tiles 
//        feats.add(2);  // Height as number of tiles 
//        feats.add(20); // Interval for delay writer
//        feats.add("default"); // FontS or "default"
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
