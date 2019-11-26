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
 * Handles actual gameplay
 *
 * @author David
 */
public class ActualGame extends BasicGameState
{

    // Entity Stores
    private ItemStore itemStore;
    private EnemyStore enemyStore;
    private ObstacleStore obStore;

    /**
     * Used to identify STATES and switch to them
     *
     * @return id
     */
    @Override
    public int getID()
    {
        return Globals.STATES.get("PLAY");
    }

    /**
     * This is only called when the game starts Used to load resources Used to
     * initialise the game state.
     *
     * @param gc
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException
    {

        itemStore = new ItemStore();
        enemyStore = new EnemyStore();
        obStore = new ObstacleStore();

    }

    /**
     * The method is called each game loop to cause your game to update it's
     * logic. This is where you should make objects move. This is also where you
     * should check input and change the state of the game.
     *
     * @param gc Holds the game
     * @param sbg
     * @param delta Amount of time since between updates
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
        // Make animation use game time
        Globals.alien.updateAnimation(delta);

        // Relative speed
        int relSpeed = (int) (Math.round(delta * Globals.alien.getMovSpeed()));

        // Handle input
        handleInput(gc.getInput(), sbg, relSpeed);

        // Update camera
        Globals.cam.centerOn(Globals.alien.getX(), Globals.alien.getY());

        // Update Globals.hud
        Globals.hud.update(Globals.cam, Globals.alien, delta);
    }

    /**
     * Acts based on user input
     */
    private void handleInput(Input input, StateBasedGame sbg, int rSpd) throws SlickException
    {
        // Only process keys if input is not been ignored
        if (Globals.inputIgnored)
        {
            return;
        }

        // Get key down
        boolean upArrowDown = input.isKeyDown(Input.KEY_UP);
        boolean downArrowDown = input.isKeyDown(Input.KEY_DOWN);
        boolean leftArrowDown = input.isKeyDown(Input.KEY_LEFT);
        boolean rightArrowDown = input.isKeyDown(Input.KEY_RIGHT);

        boolean escapeDown = input.isKeyDown(Input.KEY_ESCAPE);
        boolean altDown = input.isKeyDown(Input.KEY_RALT) || input.isKeyDown(Input.KEY_LALT);

        boolean fKeyDown = input.isKeyDown(Input.KEY_F);
        boolean qKeyDown = input.isKeyDown(Input.KEY_Q);
        boolean eKeyDown = input.isKeyDown(Input.KEY_E);

        // Check and act
        if (upArrowDown)
        {
            // Change animation
            Globals.alien.startAnim("up");

            // Move but dont collide
            if (Globals.map.isUpAllowed(Globals.alien.getX(), Globals.alien.getY(), rSpd))
            {
                Globals.alien.adjustY(-rSpd);
            }
        }
        else if (downArrowDown)
        {
            // Change animation
            Globals.alien.startAnim("down");

            // Move if conditions are satisfied
            if (Globals.map.isDownAllowed(Globals.alien.getX(), Globals.alien.getY(), rSpd))
            {
                Globals.alien.adjustY(rSpd);
            }
        }
        else if (leftArrowDown)
        {
            // Change animation
            Globals.alien.startAnim("left");

            // Move if conditions are satisfied
            if (Globals.map.isLeftAllowed(Globals.alien.getX(), Globals.alien.getY(), rSpd))
            {
                Globals.alien.adjustX(-rSpd);
            }
        }
        else if (rightArrowDown)
        {
            // Change animation
            Globals.alien.startAnim("right");

            // Move if conditions are satisfied
            if (Globals.map.isRightAllowed(Globals.alien.getX(), Globals.alien.getY(), rSpd))
            {
                Globals.alien.adjustX(rSpd);
            }
        }
        else if (escapeDown || altDown) // Paused when alt-tabbed too
        {
            sbg.enterState(Globals.STATES.get("MAINMENU"));
            // No transitions because map goes weird
            Globals.hasBeenPaused = true;
        }
        else if (fKeyDown) // Fullscreen toggle
        {
            boolean newStatus = !Globals.agc.isFullscreen();
            Globals.agc.setFullscreen(newStatus);
        }
        else if (qKeyDown) // Grab item key 
        {
            processItemGrab();
        }
        else if (eKeyDown) // Use items key
        {
            processItemUse();
        }
        else // When nothing is being pressed
        {
            Globals.alien.stopAnim();
        }
    }

    private void processItemGrab()
    {
        // Get item under player, or null if no item
        Item itemFound = (Item) itemStore.getItemUnder(Globals.alien);

        // If an item was found, and hasn't been found before
        if (itemFound != null && !itemFound.isFound())
        {
            // Process item
            itemFound.setFound(true); // Set the item as found
            itemStore.addEncounter(itemFound); // Hide item
            Globals.alien.addItem(itemFound); // Add to player inventory

            // Check for protector items and process
            processProtector(itemFound);

            // Check for instant items and process
            processInstant(itemFound);

            // Show info popup
            showItemPopup(itemFound);

        }
    }

    private void processProtector(Item item)
    {
        // Check if protected
        if (!(item instanceof ProtectedItem))
        {
            return;
        }

        // Get protector name
        String protectorName = ((ProtectedItem) item).getProtector();
        Enemy enemy = enemyStore.getEnemy(protectorName); // Get enemy/protector
        enemy.doAction();
        enemyStore.addEncounter(enemy); // Hide enemy
        Globals.map.unblockEntity(enemy); // Unblock enemy 
    }

    private void processInstant(Item item)
    {
        // Check if is an 
        if (!(item instanceof InstantItem))
        {
            return;
        }

        // Activate
        ((InstantItem) item).activateEffect(Globals.alien);
    }

    /**
     * Show information about an item as a popup
     *
     * @param item
     */
    private void showItemPopup(Item item)
    {
        // Calculate adjustment from camera
        int camRadj = 3;
        int camCadj = 2;
        int camYadj = camRadj * Globals.tileSize;
        int camXadj = camCadj * Globals.tileSize;

        // Calculate actual position
        int r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
        int c = Map.convertXtoCol(Globals.cam.getX() + camXadj);

        // Special cases = teleportation
        String name = item.getName().toLowerCase();
        if (name.contains("clock"))
        {
            r = Map.convertYtoRow(Globals.cam.getY() + camYadj - 34 * 64);
            c = Map.convertXtoCol(Globals.cam.getX() + camXadj);
        }
        else if (name.contains("shipgold"))
        {
            r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
            c = Map.convertXtoCol(Globals.cam.getX() + camXadj + 6 * 64);
            item.afterAction();
        }

        Popup itemInfo = itemStore.getInfoPopup(item, r, c); // Generate info
        Globals.hud.loadPopup(itemInfo); // Show it
    }

    private void processItemUse()
    {
        // Get the obstacle zone the player is currently inside, if any
        ObstacleZone obZone = obStore.getZoneUnder(Globals.alien);

        // If (locked obstacle found) && (player has key item of zone)
        if (((obZone != null) && (obZone.isLocked())) && (Globals.alien.hasItem(obZone.getKeyItem())))
        {
            // Special
            if (obZone.getKeyItem().equals("ShipGold"))
            {
                obZone.afterAction();
                return;
            }

            // Set as unlocked
            obZone.setLocked(false);

            // Get the zone's matching obstacle(s)
            ArrayList<Obstacle> obstacles = obStore.getMatchingObstacles(obZone);

            // For every matching obstacle
            for (Obstacle obst : obstacles)
            {
                // Hide the obstacle
                obStore.addEncounter(obst);

                // Special processing of crystals
                String obN = obst.getName();
                if (obN.contains("Gate"))
                {
                    obStore.crystalPlaced();
                }

                // Usables
                boolean cryo = obN.contains("Tree");
                boolean gun = obN.contains("Lime");
                boolean orb = obN.contains("Water");
                // Calculate adjustment from camera
                int camRadj = 3;
                int camCadj = 2;
                int camYadj = camRadj * Globals.tileSize;
                int camXadj = camCadj * Globals.tileSize;

                // Calculate actual position
                int r = Map.convertYtoRow(Globals.cam.getY() + camYadj);
                int c = Map.convertXtoCol(Globals.cam.getX() + camXadj);
                if (cryo)
                {
                    UsableItem item = Globals.alien.getItemByName("Cryo");
                    Globals.hud.loadPopup(itemStore.getUsablePopup(item, r, c));
                }
                else if (gun)
                {
                    UsableItem item = Globals.alien.getItemByName("Acid");
                    Globals.hud.loadPopup(itemStore.getUsablePopup(item, r, c));
                }
                else if (orb)
                {
                    UsableItem item = Globals.alien.getItemByName("Orb");
                    Globals.hud.loadPopup(itemStore.getUsablePopup(item, r, c));
                }

                // Unblock the obstacle
                if (obst.isUnblockOn())
                {
                    Globals.map.unblockEntity(obst);
                }
            }

        }
    }

    /**
     * This method should be used to draw to the screen. All of your game's
     * rendering should take place in this method (or via calls) It is called
     * constantly. Items are constantly redrawn
     *
     * @param gc
     * @param sbg
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
        // Draw camera's view of map
        // Note: Drawing by layers individually is not done due to severe lag
        Globals.cam.drawMap();
        Globals.cam.translateGraphics();

        // Account for grabbed items
        itemStore.updateMap(g, Globals.alien);
        enemyStore.updateMap(g, Globals.alien);
        obStore.updateMap(g, Globals.alien);

        // Draw player
        Globals.alien.drawPlayer(Globals.alien.getX(), Globals.alien.getY());

        // Draw HUD
        Globals.hud.drawHUD(g);
    }

}
