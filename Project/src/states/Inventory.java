package states;

import components.buttons.Button;
import components.helpers.FontServer;
import states.screens.InterfaceScreen;
import entity.base.Entity;
import entity.item.Item;
import main.Globals;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import static states.screens.InfoScreen.headerX;

/**
 * Provides a screen for the player's current inventory
 *
 * @author David
 */
public class Inventory extends InterfaceScreen {

    // Normal font
    private TrueTypeFont normFont = null;

    // Item image map
    private HashMap<String, Image> itemImages;

    // Max number of items
    private final int MAX_ITEMS = Globals.itemStore.getEntityList().size();

    // Item info panel
    private Image itemInfoPanel;

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("INVENTORY");
    }

    /**
     * Set back state to ActualGame instead of MainMenu
     *
     * @return
     */
    @Override
    public String getBackState() {
        return "PLAY";
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
        feats.add(Globals.emptyImgRes); // Image Location
        feats.add(100); // StartXpos
        feats.add(40); // StartYpos 
        feats.add(64); // Width
        feats.add(64); // Height
        feats.add(Globals.screenW / 14); // XSpacing
        feats.add(Globals.screenH / 15); // YSpacing
        feats.add(7); // NumberofColumns
        feats.add("Segoe UI-Plain-18"); // FontString

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
    public void customPostInit() {

        // Initialize font
        normFont = FontServer.getFont("Segoe UI-Italic-20");

        // Generate map from item names to images
        // Get all items
        ArrayList<Entity> itemList = Globals.itemStore.getEntityList();
        // Initialize map
        itemImages = new HashMap<>();
        // For every item
        for (Entity curEnt : itemList) {

            // Get item
            Item curItem = (Item) curEnt;

            // Get image array position
            int row = 0;
            int col = 0;

            // Adjust ShipGold image
            if (curItem.getName().contains("ShipGold")) {
                col++;
            }

            // Add item name and item image pair
            itemImages.put(curItem.getName(), curItem.getImage(row, col));
        }

        // Initialize item info panel
        try {
            itemInfoPanel = new Image(Globals.itemPanelRes);
            //img = img.getScaledCopy((int) rect.getWidth(), (int) rect.getHeight());
        } catch (SlickException ex) {
            System.err.println("Image error in ButtonGrid");
        }
    }

    /**
     * Do custom post rendering here
     */
    @Override
    public void customPostRender(Graphics g) {

        // Create header button
        Button header = super.getButtonGrid().getButtonByPos(0);
        header.setFont("Segoe UI-Bold-30");
        header.changeTextOffset(-3, 0);
        header.setLabel("Your Inventory");

        // Retrieve inventory
        ArrayList<Item> inv = Globals.player.getInv();

        // Create subheading
        String subHeader = "Hover over an item to see its description, Click an item to use it";
        if (inv.isEmpty()) {
            subHeader = "The inventory is empty";
        }
        normFont.drawString(header.getX(), header.getY() + 40, subHeader);

        // For all items in inventory
        for (int i = 0; i < inv.size(); i++) {

            // Get item information
            Item curItem = inv.get(i);
            String itemName = curItem.getName();
            Image itemImg = itemImages.get(itemName);

            // Load item information into button
            Button curB = super.getButtonGrid().getButtonByPos(i + 1);
            curB.setLabel(curItem.getName());
            curB.setImage(itemImg, true);
            curB.changeTextOffset(0, -25);

            // Show item information during hover
            Globals.agc.getInput().setOffset(0, 0);
            if (curB.isMouseOver()) {
                drawItemInfo(g, curItem);
            }

            // When button is clicked, use item
//            curB.addListener((ac) -> { }
//        });
            // Add mouse over for info
            // Add right click to use item 
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
        int newH = desc.size() * 25;
        itemInfoPanel = itemInfoPanel.getScaledCopy(newW, newH);
        g.drawImage(itemInfoPanel, mX, mY);

        // Get and write text
        int lX = mX + 40;
        int lY = mY + 20;
        for (String line : desc) {
            normFont.drawString(lX, lY, line);
            lY += 20;
        }

    }
}
