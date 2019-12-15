package components.structures;

import java.util.ArrayList;

import main.Globals;
import components.buttons.ButtonGrid;
import components.popups.Popup;
import components.popups.PopupDisplayer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import states.Inventory;

/**
 * Provides a HUD (Heads Up Display) with always-accessible buttons
 *
 * @author David
 */
public class HUD {

    // Buttons
    private final ButtonGrid buttonG;
    private final int BUTTON_NO = 2;
    private final int SIDE_SIZE = (int) (64 * 1.69);
    private final int SPACING = 16;
    private int shiftX;
    private int shiftY;

    // Camera co-ordinates
    private int camX;
    private int camY;

    // Player co-ordinates 
    private int playerX;
    private int playerY;

    // Popup Displayer
    private final PopupDisplayer popupDisp;

    // Inventory images
    private Image invImg;
    private Image invImgNew;

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

            // Pause game 
            Globals.hasBeenPaused = true;

            // Enter Main Menu state
            // Note: No transitions because map goes weird
            Globals.SBG.enterState(Globals.STATES.get("MAINMENU"));
        });

        // Add inventory button action
        buttonG.getButtonByPos(1).addListener(
                (AbstractComponent source)
                -> {

            // Pause game
            Globals.hasBeenPaused = true;

            // Get inventory state ID
            int invID = Globals.STATES.get("INVENTORY");

            // Update inventory state
            ((Inventory) Globals.SBG.getState(invID)).updateInv();

            // Enter inventory state
            Globals.SBG.enterState(invID);
        });

        // Initialise co-ordinates
        camX = cam.getX();
        camY = cam.getY();
        playerX = player.getX();
        playerY = player.getY();
        shiftX = 0;
        shiftY = 0;

        // Initialise PD
        popupDisp = new PopupDisplayer();

    }

    /**
     * Load a popup into the displayer
     *
     * @param itemInfo
     */
    public void loadPopup(Popup itemInfo) {
        popupDisp.loadPopup(itemInfo);
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
        this.playerX = player.getX();
        this.playerY = player.getY();

        // Update PD
        popupDisp.updatePD();

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

        // Draw popups
        popupDisp.renderPopups(g);

        // Draw info overlays
        // drawStatsText(g);
    }

    /**
     * Draw internal game variable data on top right of screen
     *
     * @param g
     */
    private void drawStatsText(Graphics g) {

        // If not in IDE
        if (!(Globals.inIDE)) {

            // Do not continue
            return;
        }

        // Calculate position
        int drawX = camX + Globals.screenW - 200;
        int drawY = camY + 12;

        // Set font color
        g.setColor(Color.black);

        // YSpacing
        int yGap = 20;

        // Draw FPS
        String fps = "FPS: " + Globals.agc.getFPS();
        g.drawString(fps, drawX, drawY);

        // Draw memory use = 1
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long memoryUsed = (totalMem - freeMem) / 1000000;
        String mem = "Mem. Use: " + memoryUsed + " MB";
        g.drawString(mem, drawX, drawY + 1 * yGap);

        // Draw cam = 2 and 3
        String cam = "cX: " + camX + " , cY: " + camY;
        g.drawString(cam, drawX, drawY + 2 * yGap);
        int camCol = Map.convertXtoCol(camX);
        int camRow = Map.convertYtoRow(camY);
        String camTile = "cC: " + camCol + " , cR: " + camRow;
        g.drawString(camTile, drawX, drawY + 3 * yGap);

        // Draw player = 4 and 5
        int adjPX = playerX + Globals.playerXadj;
        int adjPY = playerY + Globals.playerYadj;
        String player = "pX: " + adjPX + " , pY: " + adjPY;
        g.drawString(player, drawX, drawY + 4 * yGap);
        int playerCol = Map.convertXtoCol(adjPX);
        int playerRow = Map.convertYtoRow(adjPY);
        String playerTile = "pC: " + playerCol + " , pR: " + playerRow;
        g.drawString(playerTile, drawX, drawY + 5 * yGap);

        // Get extra mouse info
        Input input = Globals.agc.getInput();
        int mX = input.getMouseX();
        int mY = input.getMouseY();

        // Draw mouse = 6 and 7 
        String mouse = "mX: " + mX + " , mY: " + mY;
        g.drawString(mouse, drawX, drawY + 6 * yGap);
        int mCol = Map.convertXtoCol(mX);
        int mRow = Map.convertYtoRow(mY);
        String mouseTile = "mC: " + mCol + " , mR: " + mRow;
        g.drawString(mouseTile, drawX, drawY + 7 * yGap);
    }

}
