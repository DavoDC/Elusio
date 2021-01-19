package entity.item;

import entity.base.Entity;
import entity.enemy.Enemy;
import entity.item.types.KeyItem;
import entity.item.types.UsableItem;
import java.util.Timer;
import java.util.TimerTask;
import base.Globals;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Processes the acquiring and use of items
 *
 * @author David
 */
public class ItemProcessor {

    // The item just used
    private Item usedItem;

    // Whether to show the item
    private boolean showItem;

    // The item image
    private Animation itemAnim;

    // The last direction and location of the player
    private String lastDir;
    private int itemX;
    private int itemY;

    // The image filter
    private Color filterColor;

    /**
     * Attempt to grab an item
     */
    public void processItemGrab() {

        // Retrieve entity under player
        Entity entityFound = Globals.itemStore.
                getEntityUnder(Globals.player, true);

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
                Globals.gameMap.unblockEntity(enemy);
            }
        }

        // If usable item
        if (itemF instanceof UsableItem) {

            // Play usage sound upon grab
            ((UsableItem) itemF).playUseSound();
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

        // Stop player and save direction
        Globals.player.stopAnim();
        lastDir = Globals.player.getLastDir();

        // Initialize item location as player location
        itemX = Globals.player.getX();
        itemY = Globals.player.getY();

        // Setup item animation
        itemAnim = new Animation();
        itemAnim.addFrame(item.getImage(), 100);

        // If item is usable
        if (usedItem instanceof UsableItem) {

            // Show item for a few seconds
            showItem = true;

            // Get drawing config
            String drawConfig = ((UsableItem) usedItem).getDrawConfig();

            // If front requested, move item in front
            if (drawConfig.contains("Front")) {
                shiftItemPos(50);
            }

            // Set color for non-flashing items
            filterColor = Color.white;

            // Create draw timer
            Timer drawTimer = new Timer();
            drawTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    // If flash requested, alternate filter color
                    if (drawConfig.contains("Flash")) {

                        // If filter is null or dark
                        if (filterColor == null
                                || filterColor == Color.darkGray) {

                            // Change to light
                            filterColor = Color.lightGray;
                        } else {

                            // Else, change to dark
                            filterColor = Color.darkGray;
                        }
                    }

                    // If intake requested, move item toward player
                    if (drawConfig.contains("Intake")) {
                        shiftItemPos(-1);
                    }
                }
            }, 0, 50);

            // Transition to actual game and ignore input
            Globals.sbg.enterState(Globals.states.get("PLAY"));
            Globals.isInputBlocked = true;

            // Schedule further processing after some time
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    // Stop showing item
                    showItem = false;

                    // Stop draw timer
                    drawTimer.cancel();

                    // Activate item and act based on successful usage
                    if (((UsableItem) usedItem).doAction()) {

                        // If was successful, play successful sound
                        ((UsableItem) usedItem).playUseSound();

                    } else {

                        // Else, play falter sound
                        Globals.audioServer.playSound("itemFalter");
                    }

                    // Accept input again
                    Globals.isInputBlocked = false;
                }
            }, 2036);
        } else {
            // Else if item is not usable,
            // play falter sound
            Globals.audioServer.playSound("itemFalter");

            // Accept input again
            Globals.isInputBlocked = false;
        }
    }

    /**
     * Shift the item closer to the player
     *
     * @param value
     */
    private void shiftItemPos(int value) {

        // Act based on direction
        switch (lastDir) {
            case "up":
                itemY -= value;
                break;
            case "down":
                itemY += value;
                break;
            case "left":
                itemX -= value;
                break;
            case "right":
                itemX += value;
                break;
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

            // Draw item 
            itemAnim.draw(itemX, itemY, filterColor);
        }
    }

}
