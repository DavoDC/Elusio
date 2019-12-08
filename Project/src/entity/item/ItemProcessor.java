/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.item;

import components.structures.Map;
import entity.base.Entity;
import entity.enemy.Enemy;
import entity.obstacle.Obstacle;
import entity.obstacle.ObstacleZone;
import java.util.ArrayList;
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

    // The item just item
    private static Item usedItem;

    // The last direction of the player
    private static String lastDir;

    // The time at which the item was used
    private static long useTime;

    // Whether item processing is need
    private static boolean procNeeded;

    // The position in fron the player
    private static int[] frontPos;

    // Whether to show the item
    private static boolean showItem;

    // The item image
    private static Animation itemAnim;

    // The time to show the item for
    private static long itemTime;

    /**
     * Attempt to grab an item
     */
    public static void processItemGrab() {

        // Retrieve entity under player
        Entity entityFound = Globals.itemStore.getEntityUnder(Globals.player);

        // If nothing found or non-item found
        if (entityFound == null || !(entityFound instanceof Item)) {

            // Do not process further
            return;
        }

        // Else if entity is a valid item,
        // convert entity to item
        Item itemFound = (Item) entityFound;

        // Hide item
        Globals.itemStore.addEncounter(itemFound);

        // Add item to inventory
        Globals.player.addItem(itemFound);

        // TO DO !!!!!!!!!!!!!!
        // Process protected item
        //itemUseSuccess = procProtectedItem((ProtectedItem) usedItem);
        // Update HUD button
        Globals.hud.updateInvButton(true);
    }

    /**
     * Process a protected item
     */
    private boolean procProtectedItem(ProtectedItem protItem) {

        // Get enemy/protector 
        Enemy enemy = Globals.enemyStore.getEnemy(protItem.getProtName());

        // Enter enemy battle
        boolean defeated = true; // CHALLENGE STATUS 

        // If successfully defeated
        if (defeated) {
            // Hide encountered enemy
            Globals.enemyStore.addEncounter(enemy);

            // Unblock enemy
            Globals.map.unblockEntity(enemy);

            // Return true for success
        }

        // If didn't defeat
        return false;
    }

    /**
     * Load in an used time for processing
     *
     * @param item
     */
    public static void loadUsedItem(Item item) {

        // Save info 
        usedItem = item;
        lastDir = Globals.player.getLastDir();

        // Transition to actual game
        Globals.SBG.enterState(Globals.STATES.get("PLAY"));

        // Ignore input
        Globals.inputIgnored = true;

        // Save time
        useTime = Globals.agc.getTime();

        // Set item showing time
        itemTime = 2639;

        // Reduce item showing time for non-usable items
        if (!isUsedItemUsable()) {
            itemTime = itemTime / 3;
        }

        // Request processing
        procNeeded = true;

        // Setup item animation
        itemAnim = new Animation();
        itemAnim.addFrame(item.getImage(), 100);
    }

    /**
     * Return true if the used item is usable
     *
     * @return
     */
    private static boolean isUsedItemUsable() {
        return (usedItem instanceof InstantItem
                || usedItem instanceof KeyItem);
    }

    /**
     * Process item usage
     *
     */
    public static void processItemUse() {

        // If processing not needed
        if (!procNeeded) {

            // Do not process further
            return;
        }

        // Get position in front of player
        frontPos = getFrontPosition();

        // Show item for a few seconds
        showItem = true;

        // If a few seconds have passed
        if (Globals.agc.getTime() >= useTime + itemTime) {

            // Stop showing item
            showItem = false;

            // Do extra processing for usable items
            // If item is usable
            if (isUsedItemUsable()) {

                // Status of item use
                boolean itemUseSuccess = false;

                // Activate item according to type
                if (usedItem instanceof InstantItem) {

                    // Activate item effect and save status
                    itemUseSuccess = ((InstantItem) usedItem).doAction();

                } else {

                    // Attempt to use KeyItem to unlock obstacle
                    itemUseSuccess = tryZoneUnlock();
                }

                // Notify player about item use
                if (itemUseSuccess) {

                    // 
                    //usedItem.play
                    // MAKE SOUND SERVER
                    // Save sound in items
                    // POWER UP sound for general positive instant
                    // POWER DOWN sound for negative instant
                    // SPECIAL Sound for teleport
                    // SUCCESS noise for key items
                    // Try to emit success particles for all success
                } else {
                    // play buzzer sound. (directly from sound server)
                    //Globals.soundServer.playItemFalter()
                }
            }

            // Accept input again
            Globals.inputIgnored = false;

            // Processing is finished
            procNeeded = false;
        }

    }

    /**
     * Get the position in front of the player
     *
     * @return xPos, yPos, col, row
     */
    private static int[] getFrontPosition() {

        // Get player location
        int pX = Globals.player.getX();
        int pY = Globals.player.getY();

        // Move to front of alien
        switch (lastDir) {
            case "up":
                pY -= 50;
                break;
            case "down":
                pY += 50;
                break;
            case "left":
                pX -= 50;
                break;
            case "right":
                pX += 50;
                break;
        }

        // Get tile grid position
        int pC = Map.convertXtoCol(pX);
        int pR = Map.convertYtoRow(pY);

        // Save in array
        int[] posArr = new int[4];
        posArr[0] = pX;
        posArr[1] = pY;
        posArr[2] = pC;
        posArr[3] = pR;

        // Return array
        return posArr;
    }

    /**
     * Render item use visuals
     *
     * @param g
     */
    public static void renderItemUse(Graphics g) {

        // If item should be shown
        if (showItem) {

            // Get alternating color
            Color col = Color.white;
            if (Globals.agc.getTime() % 11 == 0) {
                col = Color.darkGray;
            }

            // Draw animation
            itemAnim.draw(frontPos[0], frontPos[1], col);
        }
    }

    /**
     * Attempt to unlock current zone with used item
     */
    private static boolean tryZoneUnlock() {

        // Get the obstacle zone the player is currently inside, if any
        ObstacleZone obZone = Globals.obStore.getZoneUnder(Globals.player);

        // If 
        // - No zone found
        // - Zone is not locked
        // - Wrong item used
        if (!(((obZone != null) && (obZone.isLocked()))
                && (usedItem.getName().equals(obZone.getKeyItem())))) {

            // Zone unlock failed
            return false;
        }

        // Set obstacle zone as unlocked
        obZone.setLocked(false);

        // Get the zone's matching obstacle(s)
        ArrayList<Obstacle> obstacles = Globals.obStore.getLinkedObstacles(obZone);

        // For every matching obstacle
        for (Obstacle obst : obstacles) {

            // Hide the obstacle
            Globals.obStore.addEncounter(obst);

            // Get obstacle name
            String obN = obst.getName();

            // Increase crystal count for magic gate
            if (obN.contains("Gate")) {
                Globals.obStore.crystalPlaced();
            }

            // Unblock the obstacle
            if (obst.isUnblockOn()) {
                Globals.map.unblockEntity(obst);
            }
        }

        // Zone unlocked successfully
        return true;
    }

}
