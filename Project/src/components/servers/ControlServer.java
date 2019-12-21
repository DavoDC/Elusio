package components.servers;

import main.Globals;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.state.GameState;
import states.special.Rift;

/**
 * Provide input and control handling
 *
 * @author David
 */
public class ControlServer {

    /**
     * Models a control
     */
    public abstract class Control {

        // Name of control
        private final String desc;

        // The keys that activate the control
        private final String[] keyList;

        /**
         * Create a control
         *
         * @param desc
         * @param keyConfig
         */
        public Control(String desc, String keyConfig) {
            this.desc = desc;
            keyList = keyConfig.split("-");
        }

        /**
         * Get description
         *
         * @return
         */
        public String getDesc() {
            return desc;
        }

        /**
         * Get key list
         *
         * @return
         */
        public String[] getKeyList() {
            return keyList;
        }

        /**
         * Returns true if any of the control's keys are pressed down
         *
         * @param keyConfig Official key names separated by dashes
         * @return True if any key is down, False otherwise
         */
        private boolean isActivated() {

            // If empty key
            if (desc.equals("")) {

                // The control is always activated
                return true;
            }

            // For all keys
            for (String curKeyS : keyList) {

                // If current key is down
                if (Globals.agc.getInput().
                        isKeyDown(keyMap.get(curKeyS))) {

                    // Return true
                    return true;
                }
            }

            // If no keys were down
            return false;
        }

        /**
         * Do control action if activated
         *
         * @return Success status
         */
        public final boolean doActionIfActivated() {

            // If key activated
            if (isActivated()) {

                // Do action and return success
                doAction();
                return true;
            }

            // Return unsuccessful
            return false;
        }

        /**
         * Action done when activated (do not call directly)
         */
        public abstract void doAction();
    }

    // Maps key strings to key codes
    private final HashMap<String, Integer> keyMap;

    // List of main controls
    private final ArrayList<Control> mainContList;

    // Delta value
    private int delta;

    /**
     * Initialize input handler
     */
    public ControlServer() {

        // Create hashmap of key code and key string pairs
        // Check key mappings document in Project/Documents
        // Initialize map
        keyMap = new HashMap<>();

        // For all keys
        for (int i = 0; i < 255; i++) {

            // Add mapping
            keyMap.put(Input.getKeyName(i), i);
        }

        // Add needed mappings that are missing
        keyMap.put("RALT", Input.KEY_RALT);
        keyMap.put("LALT", Input.KEY_LALT);
        keyMap.put("ENTER", Input.KEY_ENTER);
        keyMap.put("BACKSPACE", Input.KEY_BACK);

        // Initialize list of controls
        mainContList = new ArrayList<>();

        // Add movement controls
        mainContList.add(new Control("Up", "UP-W") {
            @Override
            public void doAction() {
                Globals.player.move("Up", delta);
                checkRiftInput("Up");
            }
        });
        mainContList.add(new Control("Left", "LEFT-A") {
            @Override
            public void doAction() {
                Globals.player.move("Left", delta);
                checkRiftInput("Left");
            }
        });
        mainContList.add(new Control("Right", "RIGHT-D") {
            @Override
            public void doAction() {
                Globals.player.move("Right", delta);
                checkRiftInput("Right");
            }
        });
        mainContList.add(new Control("Down", "DOWN-S") {
            @Override
            public void doAction() {
                Globals.player.move("Down", delta);
                checkRiftInput("Down");
            }
        });

        // Add back control
        mainContList.add(new Control("Back/Menu",
                "ESCAPE-LALT-RALT-BACKSPACE") {
            @Override
            public void doAction() {

                // Act based on where back is requested
                // If back requested in inventory
                if (Globals.isGameInState("Inventory")) {

                    // Go back to play state
                    Globals.changeState("Play", true);

                } else {

                    // If back requested in play state
                    if (Globals.isGameInState("Play")) {

                        // Game has been paused
                        Globals.isGameStarted = true;
                    }

                    // By default, go to menu
                    Globals.changeState("Menu", false);
                }

            }
        });

        // Add inventory control
        mainContList.add(new Control("Inventory", "I") {
            @Override
            public void doAction() {
                Globals.SBG.enterState(Globals.states.get("Inventory"));
            }
        });

        // Add item grab control
        mainContList.add(new Control("Grab Item", "Z-ENTER") {
            @Override
            public void doAction() {

                // If Z or Enter is pressed, try to grab item
                Globals.itemProc.processItemGrab();
            }
        });

        // Add screenshot control
        mainContList.add(new Control("Take Screenshot", "F12") {
            @Override
            public void doAction() {
                // Take screenshot when F12 is pressed
                try {
                    // Get image
                    Image target = new Image(Globals.screenW, Globals.screenH);
                    Globals.agc.getGraphics().copyArea(target, 0, 0);

                    // Generate full path
                    String folder = "Screenshots";
                    String full = folder + "/screenshot";
                    full += Globals.agc.getTime();
                    full += ".png";

                    // Make folder
                    File file = new File(full);
                    file.getParentFile().mkdirs();

                    // Write image and release
                    ImageOut.write(target, full);
                    target.destroy();

                } catch (SlickException ex) {
                    Logger.getLogger(ControlServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Add empty control
        mainContList.add(new Control("", "") {
            @Override
            public void doAction() {
                // Stop player when nothing is pressed
                Globals.player.stopAnim();
            }
        });

    }

    /**
     * Get control list
     *
     * @return
     */
    public ArrayList<Control> getControlList() {
        return mainContList;
    }

    /**
     * Check for Rift input
     *
     * @param dir
     */
    public void checkRiftInput(String dir) {

        // If currently in Rift state
        if (Globals.isGameInState("Rift")) {

            // Get state and call move method
            GameState state = Globals.SBG.getCurrentState();
            Rift rift = (Rift) state;
            rift.moveShip(dir);
        }
    }

    /**
     * Process input for a given list of controls
     *
     * @param contList
     */
    public void handleInput(String[] contList) {

        // If input is being ignored
        if (Globals.isInputBlocked) {

            // Do not process further
            return;
        }

        // For all official controls
        for (Control curCont : mainContList) {

            // Get official description
            String desc = curCont.getDesc();

            // For all inputted controls
            for (String inputCont : contList) {

                // If official matches given
                if (desc.contains(inputCont)) {

                    // Do action if activated
                    if (curCont.doActionIfActivated()) {

                        // If successful, stop processing
                        break;
                    }
                }
            }
        }
    }

    /**
     * Process user input in play state
     *
     * @param delta The delta of the update method
     */
    public void handlePlayInput(int delta) {

        // If input is being ignored
        if (Globals.isInputBlocked) {

            // Do not process further
            return;
        }

        // Update delta
        this.delta = delta;

        // For all controls
        for (Control curCont : mainContList) {

            // Do action if activated
            if (curCont.doActionIfActivated()) {

                // If successful, stop processing
                break;
            }
        }
    }

}
