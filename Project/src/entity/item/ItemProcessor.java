package entity.item;

import entity.base.Entity;
import entity.enemy.Enemy;
import entity.item.types.KeyItem;
import entity.item.types.UsableItem;
import java.util.Timer;
import java.util.TimerTask;
import main.Globals;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Processes the acquiring and use of items
 *
 * @author David
 */
public class ItemProcessor {

    /* 
    Item Processing Variables
     */
    // The item just used
    private Item usedItem;

    // Whether item processing is need
    private boolean procNeeded;

    /*
    Item Drawing Variables
     */
    // The time at which the item was used
    private long useTime;

    // The item image
    private Animation itemAnim;

    // The last direction and location of the player
    private String lastDir;
    private int pX;
    private int pY;

    // Whether to show the item
    private boolean showItem;

    // The drawing configuration
    private String drawConfig;

    /**
     * Attempt to grab an item
     */
    public void processItemGrab() {

        // Retrieve entity under player
        Entity entityFound = Globals.itemStore.getEntityUnder(Globals.player);

        // If nothing found or non-item found
        if (entityFound == null || !(entityFound instanceof Item)) {

            // Do not process further
            return;
        }

        // Else if entity is a valid item,
        // convert entity to item
        Item itemF = (Item) entityFound;

        // If item is a key item with a protector
        if (itemF instanceof KeyItem
                && ((KeyItem) itemF).getProtName() != null) {

            // Get enemy/protector 
            Enemy enemy = Globals.enemyStore.
                    getEnemy(((KeyItem) itemF).getProtName());

            // Enter enemy battle
            boolean defeated = true; //Globals.challMan.enterChallenge(enemy);

            // If not defeated
            if (!defeated) {

                // Stop processing and do not add item
                return;

            } else {

                // Else if enemy was defeated:
                // Hide encountered enemy
                Globals.enemyStore.addEncounter(enemy);

                // Unblock enemy
                Globals.map.unblockEntity(enemy);
            }
        }

        // If usable item
        if (itemF instanceof UsableItem) {

            // Play grab sound
            ((UsableItem) itemF).playGrabSound();
        }

        // Hide item
        Globals.itemStore.addEncounter(itemF);

        // Add item to inventory
        Globals.player.addItem(itemF);

        // Update HUD button
        Globals.hud.updateInvButton(true);
    }

    /**
     * Load in an used item for processing and update processing variables
     *
     * @param item
     */
    public void loadUsedItem(Item item) {

        // Save item
        usedItem = item;

        // Save player's last direction and position
        lastDir = Globals.player.getLastDir();
        pX = Globals.player.getX();
        pY = Globals.player.getY();

        // Save time
        useTime = Globals.agc.getTime();

        // Setup item animation
        itemAnim = new Animation();
        itemAnim.addFrame(item.getImage(), 100);

        // Request processing
        procNeeded = true;

        // If item is usable
        if (usedItem instanceof UsableItem) {

            // Get drawing config
            drawConfig = ((UsableItem) usedItem).getDrawConfig();

            // If front requested, move item in front
            if (drawConfig.contains("Front")) {
                shiftPosition(50);
            }

            // Transition to actual game and ignore input
            Globals.SBG.enterState(Globals.states.get("PLAY"));
            Globals.isInputBlocked = true;

            // Schedule further processing after some time
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    // Stop showing item
                    showItem = false;

                    // Activate item and act based on successful usage
                    if (((UsableItem) usedItem).doAction()) {

                        // If was successful, play successful sound
                        ((UsableItem) usedItem).playUseSound();

                    } else {

                        // Else, play falter sound
                        // Globals.audioServer.playSound(Itemfalter);
                    }

                    // Finish processing
                    procNeeded = false;

                    // Accept input again
                    Globals.isInputBlocked = false;
                }
            }, 2036);
        }
    }

    /**
     * Process item usage
     *
     */
    public void processItemUse() {

        // If processing not needed
        if (!procNeeded) {

            // Do not process further
            return;
        }

        // If item is usable
        if (usedItem instanceof UsableItem) {

            // Show item for a few seconds
            showItem = true;

        } else {

            // Else if item is not usable,
            // play 'incorrect' sound
            // Globals.audioServer.playSound(Itemfalter);
            // Finish processing
            procNeeded = false;

            // Accept input again
            Globals.isInputBlocked = false;
        }
    }

    /**
     * Render item use visuals
     *
     * @param g
     */
    public void renderItemUse(Graphics g) {

        // If item should be shown
        if (showItem) {

            // If flash requested, alternate filter
            Color col = null;
            if (drawConfig.contains("Flash")) {
                if (Globals.agc.getTime() % 11 == 0) {
                    col = Color.darkGray;
                }
            }

            // Draw item 
            itemAnim.draw(pX, pY, col);

            // If intake requested, move item toward player
            if (drawConfig.contains("Intake")) {
                if (Globals.agc.getTime() % 100 == 0) {
                    shiftPosition(-1);
                }
            }
        }
    }

    /**
     * Shift the saved player position
     *
     * @param value
     */
    private void shiftPosition(int value) {

        // Act based on direction
        switch (lastDir) {
            case "up":
                pY -= value;
                break;
            case "down":
                pY += value;
                break;
            case "left":
                pX -= value;
                break;
            case "right":
                pX += value;
                break;
        }
    }

}
