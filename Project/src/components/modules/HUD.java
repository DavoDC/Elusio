package components.modules;

import java.util.ArrayList;

import main.Globals;
import components.buttons.ButtonGrid;
import org.newdawn.slick.Color;
import states.special.Inventory;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

/**
 * Provides a HUD (Heads Up Display) with always-accessible buttons
 *
 * @author David
 */
public class HUD {

    // Button features
    private final ButtonGrid buttonG;
    private final int BUTTON_NO = 2;
    private final int SIDE_SIZE = (int) (64 * 1.69);
    private final int SPACING = 16;

    // Inventory images
    private Image invImg;
    private Image invImgNew;

    // Coordinates
    private int shiftX;
    private int shiftY;
    private int camX;
    private int camY;

    /**
     * Initialise the HUD
     *
     * @param cam
     * @param player
     */
    public HUD(Camera cam, Player player) {

        // Create button features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(BUTTON_NO); // Number of buttons
        feats.add(Globals.getFP("nothing")); // Image Location
        feats.add(SPACING); // startXpos
        feats.add(SPACING); // startYpos 
        feats.add(SIDE_SIZE); // width
        feats.add(SIDE_SIZE); // height
        feats.add(SPACING); // Xspacing
        feats.add(0); // Yspacing
        feats.add(BUTTON_NO); // NumberofColumns
        feats.add("calibri-plain-5"); // FontString

        // Create (empty) button labels
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < BUTTON_NO; i++) {
            labels.add(" ");
        }

        // Initialize BG
        buttonG = new ButtonGrid(feats, labels);

        // Load inventory images
        try {
            invImg = new Image(Globals.getFP("inv.png"));
            invImgNew = new Image(Globals.getFP("invNew.png"));
        } catch (SlickException e) {
            System.err.println("Image loading failed");
        }

        // Change images
        buttonG.getButtonByPos(0).setImageLoc(Globals.getFP("menu.png"));
        buttonG.getButtonByPos(1).setImage(invImg, true);

        // Add MainMenu button action
        buttonG.getButtonByPos(0).addListener(
                (AbstractComponent source)
                -> {

            // Do nothing if input ignored
            if (Globals.isInputBlocked) {
                return;
            }

            // Pause game 
            Globals.isGameStarted = true;

            // Enter Main Menu state
            // Note: No transitions because map goes weird
            Globals.SBG.enterState(Globals.states.get("Menu"));
        });

        // Add inventory button action
        buttonG.getButtonByPos(1).addListener(
                (AbstractComponent source)
                -> {

            // Do nothing if input ignored
            if (Globals.isInputBlocked) {
                return;
            }

            // Pause game
            Globals.isGameStarted = true;

            // Get inventory state ID
            int invID = Globals.states.get("Inventory");

            // Update inventory state
            ((Inventory) Globals.SBG.getState(invID)).updateInv();

            // Enter inventory state
            Globals.SBG.enterState(invID);
        });

        // Initialise co-ordinates
        camX = cam.getX();
        camY = cam.getY();
        shiftX = 0;
        shiftY = 0;
    }

    /**
     * Updates internal values and shifts HUD with player
     *
     * @param cam
     * @param player
     * @param delta
     */
    public void update(Camera cam, Player player, int delta) {

        // Update button shift
        shiftX = cam.getX() - camX;
        shiftY = cam.getY() - camY;

        // Update co-ordinates
        this.camX = cam.getX();
        this.camY = cam.getY();

        // Offset mouse so that buttons work   
        Globals.agc.getInput().setOffset(camX + SPACING, camY);
    }

    /**
     * Update inventory button to reflect inventory inspection status
     *
     * @param status True when inventory needs inspection
     */
    public void updateInvButton(boolean status) {

        // If inventory needs inspection
        if (status) {

            // Set special image
            buttonG.getButtonByPos(1).setImage(invImgNew, true);
        } else {

            // Else set normal image
            buttonG.getButtonByPos(1).setImage(invImg, true);
        }

    }

    /**
     * Draw the HUD
     *
     * @param g
     */
    public void drawHUD(Graphics g) {

        // Draw buttons in top left   
        buttonG.drawButtonsShifted(g, shiftX, shiftY);

        // Draw info
        Globals.drawRuntimeInfo(camX, camY, Color.black);
    }

}
