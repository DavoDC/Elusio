package states;

import java.util.ArrayList;

import main.Globals;
import components.popups.Popup;
import components.structures.Map;
import entity.item.ItemStore;
import entity.item.Item;
import entity.item.InstantItem;
import entity.item.ProtectedItem;
import entity.item.UsableItem;
import entity.enemy.Enemy;
import entity.enemy.EnemyStore;
import entity.obstacle.Obstacle;
import entity.obstacle.ObstacleStore;
import entity.obstacle.ObstacleZone;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Provides actual gameplay state
 *
 * @author David
 */
public class ActualGame extends BasicGameState {

    // Entity Stores
    private ItemStore itemStore;
    private EnemyStore enemyStore;
    private ObstacleStore obStore;

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("PLAY");
    }

    /**
     * Do initialization tasks
     *
     * @param gc
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        // Initialize entity stores
        itemStore = new ItemStore();
        enemyStore = new EnemyStore();
        obStore = new ObstacleStore();

    }

    /**
     * Handles logic and input every game loop
     *
     * @param gc Holds the game
     * @param sbg
     * @param delta Amount of time since between updates
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        // Make animation use game time
        Globals.player.updateAnimation(delta);

        // Calculative relative speed
        int relSpeed = (int) (Math.round(delta * Globals.player.getMovSpeed()));

        // Handle input
        handleInput(gc.getInput(), sbg, relSpeed);

        // Update camera
        Globals.cam.centerOn(Globals.player.getX(), Globals.player.getY());

        // Update HUD
        Globals.hud.update(Globals.cam, Globals.player, delta);
    }

    /**
     * Acts based on user input
     *
     * @param input
     * @param sbg
     * @param rSpd
     * @throws SlickException
     */
    private void handleInput(Input input, StateBasedGame sbg, int rSpd) throws SlickException {

        // If input is being ignored
        if (Globals.inputIgnored) {

            // Do not process further
            return;
        }

        // Check which keys are down
        boolean upArrowDown = input.isKeyDown(Input.KEY_UP);
        boolean downArrowDown = input.isKeyDown(Input.KEY_DOWN);
        boolean leftArrowDown = input.isKeyDown(Input.KEY_LEFT);
        boolean rightArrowDown = input.isKeyDown(Input.KEY_RIGHT);

        boolean escapeDown = input.isKeyDown(Input.KEY_ESCAPE);
        boolean altDown = input.isKeyDown(Input.KEY_RALT) || input.isKeyDown(Input.KEY_LALT);

        boolean fKeyDown = input.isKeyDown(Input.KEY_F);
        boolean qKeyDown = input.isKeyDown(Input.KEY_Q);
        boolean eKeyDown = input.isKeyDown(Input.KEY_E);

        // Act on each key
        if (upArrowDown) {

            // Change animation
            Globals.player.startAnim("up");

            // If no collision
            if (Globals.map.isUpAllowed(Globals.player.getX(), Globals.player.getY(), rSpd)) {

                // Move up
                Globals.player.adjustY(-rSpd);
            }

        } else if (downArrowDown) {

            // Change animation
            Globals.player.startAnim("down");

            // If no collision
            if (Globals.map.isDownAllowed(Globals.player.getX(), Globals.player.getY(), rSpd)) {

                // Move down
                Globals.player.adjustY(rSpd);
            }
        } else if (leftArrowDown) {

            // Change animation
            Globals.player.startAnim("left");

            // If no collision
            if (Globals.map.isLeftAllowed(Globals.player.getX(), Globals.player.getY(), rSpd)) {

                // Move left
                Globals.player.adjustX(-rSpd);
            }
        } else if (rightArrowDown) {

            // Change animation
            Globals.player.startAnim("right");

            // If no collision
            if (Globals.map.isRightAllowed(Globals.player.getX(), Globals.player.getY(), rSpd)) {

                // Move right
                Globals.player.adjustX(rSpd);
            }

        } else if (escapeDown || altDown) {

            // When Escape or Alt-Tab pressed,
            // enter Main Menu and pause
            sbg.enterState(Globals.STATES.get("MAINMENU"));
            Globals.hasBeenPaused = true;

        } else if (fKeyDown) {

            // If F is pressed, toggle fullscreen
            boolean newStatus = !Globals.agc.isFullscreen();
            Globals.agc.setFullscreen(newStatus);

        } else if (qKeyDown) {

            // If Q is pressed, try to grab item
            processItemGrab();

        } else if (eKeyDown) {

            // If E is pressed, try to use item
            processItemUse();

        } else {

            // Stop player when nothing is pressed
            Globals.player.stopAnim();
        }
    }

    /**
     * Attempt to grab an item
     */
    private void processItemGrab() {

        // Attempt to retrieve item under player
        Item itemFound = (Item) itemStore.getItemUnder(Globals.player);

        // If an item was found, and hasn't been found before
        if (itemFound != null && !itemFound.isFound()) {

            // Set the item as found
            itemFound.setFound(true);

            // Hide item
            itemStore.addEncounter(itemFound);

            // Add item to inventory
            Globals.player.addItem(itemFound);

            // Check for protector items and process
            processProtectedItem(itemFound);

            // Check for instant items and process
            processInstant(itemFound);

            // Show info popup
            showItemPopup(itemFound);

        }
    }

    /**
     * Process an item being grabbed near its protector (enemy)
     *
     * @param item
     */
    private void processProtectedItem(Item item) {

        // If not a protected item
        if (!(item instanceof ProtectedItem)) {

            // Do not process further
            return;
        }

        // Get protector name
        String protectorName = ((ProtectedItem) item).getProtector();

        // Get enemy/protector 
        Enemy enemy = enemyStore.getEnemy(protectorName);

        // Make enemy act
        enemy.doAction();

        // Hide encountered enemy
        enemyStore.addEncounter(enemy);

        // Unblock enemy
        Globals.map.unblockEntity(enemy);
    }

    /**
     * Process instant items
     *
     * @param item
     */
    private void processInstant(Item item) {

        // If not an instant item
        if (!(item instanceof InstantItem)) {

            // Do not process further
            return;
        }

        // Activate item effect
        ((InstantItem) item).activateEffect(Globals.player);
    }

    /**
     * Show information about an item as a popup
     *
     * @param item
     */
    private void showItemPopup(Item item) {

        // Calculate adjustment from camera
        int camRadj = 3;
        int camCadj = 2;
        int camYadj = camRadj * Globals.tileSize;
        int camXadj = camCadj * Globals.tileSize;

        // Calculate actual position
        int r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
        int c = Map.convertXtoCol(Globals.cam.getX() + camXadj);

        // Account for special teleportation cases
        String name = item.getName().toLowerCase();
        if (name.contains("clock")) {
            r = Map.convertYtoRow(Globals.cam.getY() + camYadj - 34 * 64);
            c = Map.convertXtoCol(Globals.cam.getX() + camXadj);
        } else if (name.contains("shipgold")) {
            r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
            c = Map.convertXtoCol(Globals.cam.getX() + camXadj + 6 * 64);
            item.afterAction();
        }

        // Generate and show popup
        Popup itemInfo = itemStore.getInfoPopup(item, r, c);
        Globals.hud.loadPopup(itemInfo);
    }

    /**
     * Attempt to use all items
     */
    private void processItemUse() {

        // Get the obstacle zone the player is currently inside, if any
        ObstacleZone obZone = obStore.getZoneUnder(Globals.player);

        // If (locked obstacle found) && (player has key item of zone)
        if (((obZone != null) && (obZone.isLocked()))
                && (Globals.player.hasItem(obZone.getKeyItem()))) {

            // Move player if they have picked up ShipGold item
            if (obZone.getKeyItem().equals("ShipGold")) {
                obZone.afterAction();
                return;
            }

            // Set obstacle zone as unlocked
            obZone.setLocked(false);

            // Get the zone's matching obstacle(s)
            ArrayList<Obstacle> obstacles = obStore.getLinkedObstacles(obZone);

            // For every matching obstacle
            for (Obstacle obst : obstacles) {

                // Hide the obstacle
                obStore.addEncounter(obst);

                // Get obstacle name
                String obN = obst.getName();

                // Increase crystal count for magic gate
                if (obN.contains("Gate")) {
                    obStore.crystalPlaced();
                }

                // Calculate adjustment from camera
                int camRadj = 3;
                int camCadj = 2;
                int camYadj = camRadj * Globals.tileSize;
                int camXadj = camCadj * Globals.tileSize;

                // Calculate actual position
                int r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
                int c = Map.convertXtoCol(Globals.cam.getX() + camXadj);

                // Show popups after item use
                showUsablePopup(obN, "Tree", "Cryo", r, c);
                showUsablePopup(obN, "Lime", "Acid", r, c);
                showUsablePopup(obN, "Water", "Orb", r, c);

                // Unblock the obstacle
                if (obst.isUnblockOn()) {
                    Globals.map.unblockEntity(obst);
                }
            }

        }
    }

    /**
     * Show usable item popup after it is grabbed
     *
     * @param obN The obstacle's full name
     * @param obQ Part of the obstacle's name
     * @param itemName Item name subset
     * @param r Row position of popup
     * @param c Column position of popup
     */
    private void showUsablePopup(String obN, String obQ, String itemName, int r, int c) {

        // If obstacle name contains query
        if (obN.contains(obQ)) {

            // Get item
            UsableItem item = (UsableItem) Globals.player.getItemByName(itemName);

            // Load popup for item
            Globals.hud.loadPopup(itemStore.getUsablePopup(item, r, c));

        }
    }

    /**
     * Render map, alien (player) and HUD
     *
     * @param gc
     * @param sbg
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {

        // Draw camera's view of map
        // Note: Drawing by layers individually is not done due to severe lag
        Globals.cam.drawMap();
        Globals.cam.translateGraphics();

        // Account for entity interactions
        itemStore.updateMap(g);
        enemyStore.updateMap(g);
        obStore.updateMap(g);

        // Draw player
        Globals.player.drawPlayer(Globals.player.getX(), Globals.player.getY());

        // Draw HUD
        Globals.hud.drawHUD(g);
    }

}
