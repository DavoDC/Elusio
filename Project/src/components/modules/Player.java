package components.modules;

import entity.item.Item;
import base.Globals;
import base.Movable;
import static components.modules.GameMap.canPass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Models the player
 *
 * @author David
 */
public class Player extends Movable {

    // Collision tightness variables
    private final int Xfactor = 25;
    private final int Xadj = 30;
    private final int Yfactor = 60;
    private final int Yadj = 50;

    // Movement animation
    private final int spRatio = 90;
    private int animSpeed;
    private final Animation anim;
    private final HashMap<String, String> animConfig;

    // Inventory
    private final ArrayList<Item> inv;

    /**
     * Create a Player
     *
     */
    public Player() {

        // Call Movable constructor
        super(300, 300, 33, 48, 1.5, 0.2f, false, "alienStep");

        // Initalize sprites of player animation
        SpriteSheet sprites = null;
        try {
            sprites = new SpriteSheet(
                    Globals.getFP("playerSS"),
                    super.getWidth(),
                    super.getHeight());
        } catch (SlickException e) {
        }

        // Sync animation speed with movement speed
        syncAnimSpeed();

        // Initialize animation 
        anim = new Animation(sprites, animSpeed);

        // Initialize animation configuration
        animConfig = new HashMap<>();
        animConfig.put("up", "n9n10n11");
        animConfig.put("down", "n0n1n2");
        animConfig.put("left", "n3n4n5");
        animConfig.put("right", "n6n7n8");

        // Prevent animation from starting on its own
        anim.stop();

        // Set animation on "standing still" frame
        anim.setCurrentFrame(1);

        // Initialise inventory
        inv = new ArrayList<>();

        // If in IDE
        if (Globals.inIDE) {

            // Increase movement speed for faster test runs
            changeMovSpeed(0.4f);
        }
    }

    /**
     * Process the player's change in position
     *
     * @param sign
     * @param axis
     * @param trueSpeed
     */
    @Override
    public void procPosChange(char sign, char axis, int trueSpeed) {

        // Get player position
        int xPos = super.getX();
        int yPos = super.getY();

        // Get movement type booleans
        // New position variables
        int newX1 = xPos;
        int newX2 = xPos;
        int newY1 = yPos;
        int newY2 = yPos;

        // Get change
        String changeS = "" + sign + "" + trueSpeed;
        int change = Integer.parseInt(changeS);

        // True when player is still within map
        boolean inMap = true;

        // Get tile size
        int tSize = Globals.tileSize;

        // Get new position variables
        // If on X axis
        if (axis == 'x') {

            // Add change to X (as this may occur)
            newX1 += change;
            newX2 += change;

            // Adjust Y (for collision)
            newY1 += Yfactor;
            newY2 += Yadj;

            // If moving rightward (+x)
            if (sign == '+') {

                // Adjust for right (+x) collision 
                newX1 += Xfactor;
                newX2 += Xfactor;

                // Check that player is not going off right edge of map
                int xLimiter = Globals.gameMap.getCoordWidth() - (5 * tSize / 6);
                inMap = (xPos + trueSpeed) < xLimiter;
            }
        } else {

            // Else if on Y axis:
            // Adjust X (for collision)
            newX1 += Xfactor;
            newX2 += Xadj;

            // Add change to Y (as this may occur)
            newY1 += change;
            newY2 += change;

            // If moving downward (+y)
            if (sign == '+') {

                // Adjust Y 
                newY1 += Yfactor;
                newY2 += Yfactor;

                // Check that player is not going off bottom of map
                int yLimiter = Globals.gameMap.getCoordHeight() - tSize;
                inMap = (yPos + trueSpeed) < yLimiter;
            }
        }

        // Use new position to check if the player is colliding
        boolean noColl1 = canPass(newX1, newY1);
        boolean noColl2 = canPass(newX2, newY2);
        boolean noCollision = noColl1 && noColl2;

        // If player is not colliding and is still in map
        if (noCollision && inMap) {

            // Apply change to correct axis
            if (axis == 'x') {
                adjustX(change);
            } else {
                adjustY(change);
            }

            // Start animation and limit frames
            anim.start();
            limitFrames(animConfig.get(super.getLastDir()));
        }
    }

    /**
     * Limits an animation to only use frames in a frame configuration string.
     * (Format Example: "n0n1n2" for the first three frames)
     *
     * @param config
     */
    public void limitFrames(String config) {

        // Get frame count 
        int frameCount = anim.getFrameCount();

        // Initalize list of numbers
        ArrayList<Integer> frameIndices = new ArrayList<>();

        // For every number string 
        for (String curNo : config.split("n")) {

            // If not an empty string
            if (curNo.length() != 0) {

                // Add number derived from string
                frameIndices.add(Integer.parseInt(curNo));
            }
        }

        // For every frame 
        for (int i = 0; i < frameCount; i++) {

            // If frame is in list of wanted frames
            if (frameIndices.contains(i)) {

                // Give it a normal duration
                anim.setDuration(i, animSpeed);

            } else {

                // Else if it is not a wanted frame,
                // set its duration to zero
                anim.setDuration(i, 0);
            }
        }
    }

    /**
     * Attempt to retrieve an item with a given name substring from the player
     *
     * @param itemName
     * @return the Item or null if not found
     */
    public Item getItemByName(String itemName) {

        // For all items 
        for (Item curItem : inv) {

            // Get current item's name
            String curItemS = curItem.getName();

            // If current item matches inputted item
            if (curItemS.contains(itemName)) {

                // Return item
                return curItem;
            }
        }

        // If no item found, return null
        return null;
    }

    /**
     * Remove item by name
     *
     * @param itemName
     */
    public void removeItemByName(String itemName) {

        // Create iterator
        Iterator<Item> it = inv.iterator();

        // While there is a next item
        while (it.hasNext()) {

            // If the current item matches
            if (it.next().getName().equalsIgnoreCase(itemName)) {

                // Remove it
                it.remove();

                // Stop
                return;
            }
        }

        // Throw exception if unable to remove item
        throw new IllegalArgumentException("removeItemByName: Invalid name");
    }

    /**
     * Draw the player
     */
    public void draw() {

        // If not visible
        if (!super.isVisible()) {

            // Do not draw
            return;
        }

        // Make animation use game time
        anim.update(Globals.curDelta);

        // Get bounds
        int drawX = super.getX();
        int drawY = super.getY();
        int drawW = super.getScaledWidth();
        int drawH = super.getScaledHeight();

        // Draw at given location
        anim.draw(drawX, drawY, drawW, drawH);
    }

    /**
     * Change movement speed and re-sync animation speed
     *
     * @param change
     */
    public void changeMovSpeed(float change) {
        super.setMovSpeed(super.getMovSpeed() + change);
        syncAnimSpeed();
    }

    /**
     * Sync animation speed with movement speed
     */
    private void syncAnimSpeed() {
        animSpeed = (int) ((1 / super.getMovSpeed()) * spRatio);
    }

    /**
     * Return true if player has an item with the given name substring
     *
     * @param itemName
     * @return
     */
    public boolean hasItem(String itemName) {

        // Return true if found item is not null
        return (getItemByName(itemName) != null);
    }

    /**
     * Add the given item to the player inventory
     *
     * @param item
     */
    public void addItem(Item item) {
        inv.add(item);
    }

    /**
     * Retrieve player inventory
     *
     * @return
     */
    public ArrayList<Item> getInv() {
        return inv;
    }

    /**
     * Stop the player animation
     */
    public void stopAnim() {
        anim.stop();
    }
}
