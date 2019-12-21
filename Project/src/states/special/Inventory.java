package states.special;

import components.buttons.Button;
import components.servers.FontServer;
import states.base.InterfaceScreen;
import entity.base.Entity;
import entity.item.Item;
import entity.item.types.UsableItem;
import main.Globals;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;

/**
 * Provides a screen for the player's current inventory
 *
 * @author David
 */
public class Inventory extends InterfaceScreen {

    // Normal font
    private TrueTypeFont normFont = null;

    // Button features list
    private ArrayList<Object> common;

    // Map items to buttons
    private HashMap<Item, Button> itemMap;

    // Max number of items
    private final int MAX_ITEMS = Globals.itemStore.getEntityList().size();

    // Copy of the player's inventory
    private ArrayList<Item> inv;

    // Item info panel
    private Image itemInfoPanel;

    // Header location
    private int headX;
    private int headY;

    // Subheading text
    private String subHeading;

    /**
     * Set back state to ActualGame instead of Menu
     *
     * @return
     */
    @Override
    public String getBackState() {
        return "Play";
    }

    /**
     * Set button features
     *
     * @return
     */
    @Override
    public ArrayList<Object> getButtonFeatures() {

        // Initialize list
        ArrayList<Object> feats = new ArrayList<>();

        // Add features
        feats.add(MAX_ITEMS + 1); // Button amount
        feats.add(Globals.getFP("nothing")); // Image Location
        feats.add(100); // StartXpos
        feats.add(60); // StartYpos 
        feats.add(64); // Width
        feats.add(64); // Height
        feats.add(Globals.screenW / 14); // XSpacing
        feats.add(Globals.screenH / 15); // YSpacing
        feats.add(7); // NumberofColumns
        feats.add("Segoe UI-Plain-18"); // FontString

        // Save feats
        common = feats;

        // Return list
        return feats;
    }

    /**
     * Set button labels
     *
     * @return
     */
    @Override
    public ArrayList<String> getButtonLabels() {

        // Return placeholder list
        ArrayList<String> text = new ArrayList<>();
        for (int i = 0; i < MAX_ITEMS + 1; i++) {
            text.add(" ");
        }
        return text;
    }

    /**
     * Set darkened option
     *
     * @return
     */
    @Override
    public boolean isDarkened() {
        return true;
    }

    /**
     * Do custom initialization
     */
    @Override
    public void customInit() {

        // Adjust header
        Button header = super.getButtonGrid().getButtonByPos(0);
        header.setFont("Segoe UI-Bold-30");
        header.setTextOffsets(-3, 0);
        header.setLabel("Your Inventory");

        // Save header location
        headX = header.getX();
        headY = header.getY();

        // Initialize font for item names and panel     
        normFont = FontServer.getFont("Segoe UI-Plain-20");

        // Initialize item info panel
        try {
            itemInfoPanel = new Image(Globals.getFP("iteminfo"));
        } catch (SlickException ex) {
            System.err.println("Image error in ButtonGrid");
        }

        // Get item list
        ArrayList<Entity> itemList = Globals.itemStore.getEntityList();

        // Initialize map
        itemMap = new HashMap<>();

        // For every item
        for (int i = 0; i < MAX_ITEMS; i++) {

            // Get item and its image
            Item curItem = (Item) itemList.get(i);
            Image itemImg = curItem.getImage();

            // Load item information into button
            Button curB = super.getButtonGrid().getButtonByPos(i + 1);
            curB.setLabel(curItem.getName());
            curB.setImage(itemImg, true);
            curB.setTextOffsets(0, -25);

            // Add item use action to button
            curB.addListener((AbstractComponent source) -> {

                // Initiate item use
                Globals.itemProc.loadUsedItem(curItem);
            });

            // Save item and button pair into map
            itemMap.put(curItem, curB);
        }

    }

    /**
     * Update the inventory to match the player's inventory
     */
    public void updateInv() {

        // Inspection is just about to occur,
        // so turn off 'need inspection' image of HUD inventory button
        Globals.hud.updateInvButton(false);

        // Update copy of inventory
        inv = Globals.player.getInv();

        // Update subheading text
        if (inv.isEmpty()) {
            subHeading = "Your inventory is empty. Try finding some items to grab!";
        } else {
            subHeading = "Hover over an item to see its description";
            subHeading += ". Click an item to use it";
        }

        // Update the enabled status of each button
        // For all buttons
        for (int i = 0; i < MAX_ITEMS; i++) {

            // Get button
            Button curB = super.getButtonGrid().getButtonByPos(i + 1);

            // Check if matching item is in inventory
            boolean inInv = Globals.player.hasItem(curB.getLabel());

            // If item is in inventory, enable button
            // If item is NOT in inventory, disable button
            curB.setEnabled(inInv);
        }

        // Adjust item positions
        adjustItemButtons();
    }

    /**
     * Adjust item button positions. Please see ButtonGrid's 'adjustButtons' for
     * true source code
     */
    private void adjustItemButtons() {

        // Extract required information
        int buttonNo = (int) common.get(0);
        int xpos = (int) common.get(2);
        int ypos = (int) common.get(3);
        int bW = (int) common.get(4);
        int bH = (int) common.get(5);
        int xspacing = (Integer) common.get(6);
        int yspacing = (Integer) common.get(7);
        int columns = (Integer) common.get(8);

        // Loop variables
        int buttonsDone = 0;
        int curxpos = xpos;
        int curypos = ypos;

        // For all items
        for (int i = 0; i < MAX_ITEMS; i++) {

            // Get button
            Button curB = super.getButtonGrid().getButtonByPos(i);

            // If button is enabled
            if (curB.isEnabled()) {

                // Skip header
                if (!curB.getLabel().contains("Inv")) {
                    curB.setBounds(curxpos, curypos, bW, bH);
                }

                // Shift current X by width + spacing
                curxpos += (bW + xspacing);

                // If current position is a HEADER or a multiple of columnNo
                if (((buttonsDone % columns) == 0) && (columns != buttonNo)) {

                    // Reset X (go back to left)
                    curxpos = xpos;

                    // Increase Y (move downwards)
                    curypos += (bH + yspacing);
                }

                // Increase buttons done
                buttonsDone++;
            }
        }
    }

    /**
     * Do custom post rendering here
     */
    @Override
    public void customRender(Graphics g) {

        // Draw subheading relative to header
        normFont.drawString(headX, headY + 40, subHeading);

        // Show item information during hover
        // Fix mouse location
        Globals.agc.getInput().setOffset(0, 0);

        // For all items in inventory
        for (Item curItem : inv) {

            // Get button
            Button curB = itemMap.get(curItem);

            // Draw amount of usage times for usable items
            if (curItem instanceof UsableItem) {
                int uX = curB.getX() + curB.getWidth() - 15;
                int uY = curB.getY() + curB.getHeight() - 15;
                g.drawString(
                        ((UsableItem) curItem).getUsageTimes(),
                        uX, uY);
            }

            // If mouse is over button
            if (curB.isMouseOver()) {

                // Draw item panel 
                drawItemInfo(g, curItem);
            }
        }
    }

    /**
     * Draw a box at the mouse location with information about a given items
     *
     * @param g
     * @param item
     */
    private void drawItemInfo(Graphics g, Item item) {

        // Get mouse position
        int mX = Globals.agc.getInput().getAbsoluteMouseX();
        int mY = Globals.agc.getInput().getAbsoluteMouseY();

        // Get description lines
        ArrayList<String> desc = item.getInfoLines();

        // Scale and draw item info panel
        int newW = itemInfoPanel.getWidth();
        int newH = desc.size() * 50;
        itemInfoPanel = itemInfoPanel.getScaledCopy(newW, newH);
        g.drawImage(itemInfoPanel, mX, mY);

        // Get and write text
        int lX = mX + 60;
        int lY = mY + 10;
        for (String line : desc) {

            // Stop if usage line encountered
            if (line.contains("Usage")) {
                break;
            }

            // Draw line and progress down
            normFont.drawString(lX, lY, line);
            lY += 40;
        }

    }
}
