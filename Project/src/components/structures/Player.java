package components.structures;

import entity.item.Item;
import main.Globals;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Models the player
 *
 * @author David
 */
public class Player {

    // Player location
    private int xPos;
    private int yPos;

    // Starting location
    private final int START_X = 300;
    private final int START_Y = 300;

    // Dimensions of player
    private double scale;
    private int width;
    private int height;

    // Movement speed of player
    private float movSpeed;

    // Variables for player movement animation
    private int animSpeed;
    private final Animation anim;

    // Amount of animSpeed per movSpeed
    private final int speedRatio = 90;

    // Inventory
    private final ArrayList<Item> inv;

    /**
     * Create a player
     */
    public Player() {

        // Initialise fields
        xPos = START_X;
        yPos = START_Y;
        scale = 1.5;
        width = 33;
        height = 48;
        inv = new ArrayList<>();

        // Set normal default speed
        movSpeed = 0.20f;

        // If in IDE
        if (Globals.inIDE) {

            // Increase speed for faster test runs
            movSpeed += 0.50f;
        }

        // Initalize sprites of player animation
        SpriteSheet sprites = null;
        try {
            sprites = new SpriteSheet(Globals.playerSprRes, width, height);
        } catch (SlickException e) {
        }

        // Sync animation speed with movement speed
        syncAnimSpeed();

        // Initialize animation 
        anim = new Animation(sprites, animSpeed);

        // Preven animation from starting on its own
        anim.stop();

        // Set animation on "standing still" frame
        anim.setCurrentFrame(1);
    }

    /**
     * Put animation speed in sync with movement speed
     */
    private void syncAnimSpeed() {
        animSpeed = (int) ((1 / movSpeed) * speedRatio);
    }

    /**
     * Get X position of player
     *
     * @return
     */
    public int getX() {
        return xPos;
    }

    /**
     * Get Y position of player
     *
     * @return
     */
    public int getY() {
        return yPos;
    }

    /**
     * Change X position of player, preventing
     *
     * @param change
     */
    public void adjustX(int change) {

        // If change does not result in negative vaue
        if (!(xPos + change < 0)) {

            // Apply change
            xPos += change;
        }
    }

    public void adjustY(int change) {

        // If change does not result in negative vaue
        if (!(yPos + change < 0)) {

            // Apply change
            yPos += change;
        }
    }

    /**
     * Reset player location back to start
     */
    public void resetLocation() {
        xPos = START_X;
        yPos = START_Y;
    }

    /**
     * Get movement speed
     *
     * @return
     */
    public float getMovSpeed() {
        return movSpeed;
    }

    /**
     * Change movement speed and re-sync animation speed
     *
     * @param change
     */
    public void changeMovSpeed(float change) {
        movSpeed += change;
        syncAnimSpeed();
    }

    /**
     * Update player movement animation
     *
     * @param delta
     */
    public void updateAnimation(int delta) {
        anim.update(delta);
    }

    /**
     * Starts the walking animation in a given direction
     *
     * @param dir Direction of motion
     */
    public void startAnim(String dir) {

        // Start animation
        anim.start();

        // Determine frames needed
        String frameConfig;
        switch (dir) {
            case "up":
                frameConfig = "n9n10n11"; // Back frames
                break;
            case "down":
                frameConfig = "n0n1n2"; // Front frames
                break;
            case "left":
                frameConfig = "n3n4n5"; // Left frames
                break;
            case "right":
                frameConfig = "n6n7n8"; // Right frames
                break;
            default:
                frameConfig = "n0n1n2"; // Front frames
                break;
        }

        // Limit frames to those needed
        configureFrames(frameConfig);
    }

    /**
     * Adjusts an animation to only use given frames. Frame configuration string
     * would be "n0n1n2" for first three frames.
     *
     * @param frConfS
     */
    public void configureFrames(String frConfS) {

        // Get frame count 
        int frameCount = anim.getFrameCount();

        // Initalize list of numbers
        ArrayList<Integer> frNumsWanted = new ArrayList<>();

        // For every number string 
        for (String curNo : frConfS.split("n")) {

            // If not an empty string
            if (curNo.length() != 0) {

                // Add number derived from string
                frNumsWanted.add(Integer.parseInt(curNo));
            }
        }

        // For every frame 
        for (int i = 0; i < frameCount; i++) {

            // If frame is in list of wanted frames
            if (frNumsWanted.contains(i)) {

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
     * Stop the player animation
     */
    public void stopAnim() {
        anim.stop();
    }

    /**
     * Draw the correctly sized player at the coordinates
     *
     * @param pX
     * @param pY
     */
    public void drawPlayer(int pX, int pY) {

        // Get width and height
        int largerW = (int) (width * scale);
        int largerH = (int) (height * scale);

        // Draw at given location
        anim.draw(pX, pY, largerW, largerH);
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
     * Retrieve player inventory
     *
     * @return
     */
    public ArrayList<Item> getInv() {
        return inv;
    }
}
