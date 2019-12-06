package components.helpers;

import java.util.HashMap;
import main.Globals;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author David
 */
public class InputHandler {

    // Maps key strings to key codes
    private final HashMap<String, Integer> keyMap;

    // The current input source
    private static Input inputSrc;

    /**
     * Initialize input handler
     */
    public InputHandler() {

        // Create hashmap of key code and key string pairs
        // Check key mappings document in Project/Documents
        // Initialize map
        keyMap = new HashMap<>();

        // For all keys
        for (int i = 0; i < 256; i++) {

            // Add mapping
            keyMap.put(Input.getKeyName(i), i);
        }
    }

    /**
     * Returns true if any of the given keys are pressed down
     *
     * @param keyConfig Official key names separated by dashes
     * @return True if any key is down, False otherwise
     */
    private boolean areKeysDown(String keyConfig) {

        // Split string
        String[] keyStrings = keyConfig.split("-");

        // For all keys
        for (String curKeyS : keyStrings) {

            // If current key is down
            if (inputSrc.isKeyDown(keyMap.get(curKeyS))) {

                // Return true
                return true;
            }
        }

        // If no keys were down
        return false;
    }

    /**
     * Process user input in play state
     *
     * @param in The input of the game container
     * @param delta The delta of the update method
     * @throws SlickException
     */
    public void handlePlayInput(Input in, int delta) throws SlickException {

        // If input is being ignored
        if (Globals.inputIgnored) {

            // Do not process further
            return;
        }

        // Update input source
        inputSrc = in;

        // Get player information
        float movSpeed = Globals.player.getMovSpeed();
        int trueSpeed = (int) (Math.round(delta * movSpeed));
        int pX = Globals.player.getX();
        int pY = Globals.player.getY();

        // Act on each set of keys
        if (areKeysDown("UP-W")) {

            // Change animation
            Globals.player.startAnim("up");

            // If no collision
            if (Globals.map.isUpAllowed(pX, pY, trueSpeed)) {

                // Move up
                Globals.player.adjustY(-trueSpeed);
            }

        } else if (areKeysDown("DOWN-S")) {

            // Change animation
            Globals.player.startAnim("down");

            // If no collision
            if (Globals.map.isDownAllowed(pX, pY, trueSpeed)) {

                // Move down
                Globals.player.adjustY(trueSpeed);
            }
        } else if (areKeysDown("LEFT-A")) {

            // Change animation
            Globals.player.startAnim("left");

            // If no collision
            if (Globals.map.isLeftAllowed(pX, pY, trueSpeed)) {

                // Move left
                Globals.player.adjustX(-trueSpeed);
            }
        } else if (areKeysDown("RIGHT-D")) {

            // Change animation
            Globals.player.startAnim("right");

            // If no collision
            if (Globals.map.isRightAllowed(pX, pY, trueSpeed)) {

                // Move right
                Globals.player.adjustX(trueSpeed);
            }

        } else if (areKeysDown("ESCAPE-LMENU-RMENU")) {

            // When Escape or Alt pressed,
            // enter Main Menu and pause
            Globals.SBG.enterState(Globals.STATES.get("MAINMENU"));
            Globals.hasBeenPaused = true;

        } else if (areKeysDown("I")) {

            // If I is pressed, go to inventory
            Globals.SBG.enterState(Globals.STATES.get("INVENTORY"));

        } else if (areKeysDown("Q-RETURN")) {

            // If Q or Enter is pressed, try to grab item
            Globals.itemStore.processItemGrab();

        } else {

            // Stop player when nothing is pressed
            Globals.player.stopAnim();
        }
    }

}
