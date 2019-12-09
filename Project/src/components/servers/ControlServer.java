package components.servers;

import entity.item.ItemProcessor;
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

/**
 * Provide input and control handling
 *
 * @author David
 */
public class ControlServer {

    // Maps key strings to key codes
    private final HashMap<String, Integer> keyMap;

    // List of controls
    private final ArrayList<Control> controlList;

    // Play state delta
    private int delta;

    // A list of states where you cannot go back
    private ArrayList<Integer> specialStates;

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
        controlList = new ArrayList<>();
        controlList.add(new Control("Up", "UP-W") {
            @Override
            public void doAction() {
                Globals.player.move("Up", delta);
            }
        });
        controlList.add(new Control("Left", "LEFT-A") {
            @Override
            public void doAction() {
                Globals.player.move("Left", delta);
            }
        });
        controlList.add(new Control("Right", "RIGHT-D") {
            @Override
            public void doAction() {
                Globals.player.move("Right", delta);
            }
        });
        controlList.add(new Control("Down", "DOWN-S") {
            @Override
            public void doAction() {
                Globals.player.move("Down", delta);
            }
        });
        controlList.add(new Control("Back/MainMenu",
                "ESCAPE-LALT-RALT-BACKSPACE") {
            @Override
            public void doAction() {

                // Get stateIDs
                int curState = Globals.SBG.getCurrentStateID();
                int playState = Globals.STATES.get("PLAY");
                int invState = Globals.STATES.get("INVENTORY");

                // Act based on where back is requested
                // If back requested in inventory
                if (curState == invState) {

                    // Go back to play state
                    Globals.SBG.enterState(playState,
                            Globals.getLeave(),
                            Globals.getEnter());

                } else {

                    // If back requested in play state
                    if (curState == playState) {

                        // Game has been paused
                        Globals.hasBeenPaused = true;
                    }

                    // By default, go to main menu
                    Globals.SBG.enterState(Globals.STATES.get("MAINMENU"));
                }

            }
        });
        controlList.add(new Control("Inventory", "I") {
            @Override
            public void doAction() {
                Globals.SBG.enterState(Globals.STATES.get("INVENTORY"));
            }
        });
        controlList.add(new Control("Grab Item", "Z-ENTER") {
            @Override
            public void doAction() {

                // If Z or Enter is pressed, try to grab item
                Globals.itemProc.processItemGrab();
            }
        });
        controlList.add(new Control("Take Screenshot", "F12") {
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
        controlList.add(new Control("", "") {
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
        return controlList;
    }

    /**
     * Process user input in play state
     *
     * @param delta The delta of the update method
     * @throws SlickException
     */
    public void handlePlayInput(int delta) throws SlickException {

        // If input is being ignored
        if (Globals.inputIgnored) {

            // Do not process further
            return;
        }

        // Update input source and delta value
        this.delta = delta;

        // For all controls
        for (Control curCont : controlList) {

            // Do action if activated
            if (curCont.doActionIfActivated()) {
                // If successful, stop processing
                break;
            }
        }
    }

    /**
     * Process user input in screens
     */
    public void handleScreenInput() {

        // For all controls
        for (Control curCont : controlList) {

            // Get description
            String desc = curCont.getDesc();

            // For back and screenshot controls, 
            // do action if activated
            if (desc.contains("MainMenu")
                    || desc.contains("Screenshot")) {

                // Do action if activated
                if (curCont.doActionIfActivated()) {

                    // If successful, stop processing
                    break;
                }
            }
        }
    }
}
