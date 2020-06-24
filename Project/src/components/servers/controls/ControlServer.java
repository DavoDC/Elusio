package components.servers.controls;

import base.Globals;
import components.underlying.LooseMap;

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

    // List of all controls
    private final ArrayList<Control> contList;

    // Loosely maps control names to info line
    private LooseMap<String> contInfoMap;

    /**
     * Initialize input handler
     */
    public ControlServer() {

        // Create hashmap of key code and key string pairs
        // Check key mappings document in Project/Documents
        HashMap<String, Integer> keyMap = new HashMap<>();

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
        contList = new ArrayList<>();

        // Add movement controls
        contList.add(new MovCont("Up", "UP-W", keyMap));
        contList.add(new MovCont("Left", "LEFT-A", keyMap));
        contList.add(new MovCont("Down", "DOWN-S", keyMap));
        contList.add(new MovCont("Right", "RIGHT-D", keyMap));

        // Add back control
        contList.add(new Control("Back/Menu",
                "ESCAPE-LALT-RALT-BACKSPACE", keyMap) {
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
        contList.add(new Control("Inventory", "I", keyMap) {
            @Override
            public void doAction() {
                Globals.sbg.enterState(Globals.states.get("Inventory"));
            }
        });

        // Add item grab control
        contList.add(new Control("Grab Item", "Z-ENTER", keyMap) {
            @Override
            public void doAction() {

                // If Z or Enter is pressed, try to grab item
                Globals.itemProc.processItemGrab();
            }
        });

        // Add screenshot control
        contList.add(new Control("Take Screenshot", "F12", keyMap) {
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

        // Add empty control LAST
        contList.add(new Control("", "", keyMap) {
            @Override
            public void doAction() {

                // Stop player when nothing is pressed
                Globals.player.stopAnim();
            }
        });

        // Initialize control info map
        initContInfoMap();
    }

    /**
     * Process input for a given list of control name substrings
     *
     * @param contNames
     */
    public void handleInput(String[] contNames) {

        // If input is being ignored
        if (Globals.isInputBlocked) {

            // Do not process further
            return;
        }

        // If all controls are requested
        if (contNames[0].equalsIgnoreCase("All")) {

            // For all controls
            for (Control curCont : contList) {

                // Do action if activated
                if (curCont.doActionIfActivated()) {

                    // If successful, stop processing
                    break;
                }
            }
        } else {

            // Else if a specific control list is requested:
            // For all official controls
            for (Control curCont : contList) {

                // Get official description
                String desc = curCont.getDesc();

                // For all inputted controls
                for (String inputCont : contNames) {

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
    }

    /**
     * Initialize control information map
     */
    public void initContInfoMap() {

        // Initialize control info map
        contInfoMap = new LooseMap<>();

        // Add movement control
        contInfoMap.put("Movement", getMovementLine());

        // For all other controls
        for (Control curCont : contList) {

            // If null or a movement control
            if (curCont == null || curCont instanceof MovCont) {

                // Skip
                continue;
            }

            // Get description
            String curDesc = curCont.getDesc();

            // If not empty key or mov
            if (!curDesc.equals("")) {

                // Add pair to map
                contInfoMap.put(curDesc, curCont.getInfoLine());

            }
        }
    }

    /**
     * Get movement control line
     *
     * @return
     */
    private String getMovementLine() {

        // Holders
        String keys = "";
        String arrows = "";

        // For all other controls
        for (Control curCont : contList) {

            // If a valid movement control
            if (curCont != null && curCont instanceof MovCont) {

                // Convert to movement control
                MovCont mov = (MovCont) curCont;

                // Add to keys
                keys += mov.getKeyPart(1);

                // Add to arrows
                arrows += mov.getKeyPart(0);
            }
        }

        // Take off extra comma
        arrows = arrows.substring(0, arrows.length() - 2);

        // Return result
        return ("MOVEMENT = " + keys + arrows);
    }

    /**
     * Get control info map with (control-name), (info-line) pairs
     *
     * @return
     */
    public LooseMap<String> getContInfo() {
        return contInfoMap;
    }
}
