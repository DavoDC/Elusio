package components.servers.controls;

import java.util.HashMap;

import states.special.SpRealm;
import base.Globals;

import org.newdawn.slick.state.GameState;

/**
 * Models a movement control
 *
 * @author David
 */
public class MovCont extends Control {

    /**
     * Make a movement control
     *
     * @param desc
     * @param keyConfig
     * @param keyMap
     */
    public MovCont(String desc, String keyConfig,
            HashMap<String, Integer> keyMap) {
        super(desc, keyConfig, keyMap);
    }

    @Override
    public final void doAction() {

        // If in Play state
        if (Globals.isGameInState("Play")) {

            // Move player
            Globals.player.move(getDesc());

        } else if (Globals.isGameInState("SpRealm")) {

            // Else if currently in Rift state,
            // get state and call move method
            GameState state = Globals.sbg.getCurrentState();
            SpRealm rift = (SpRealm) state;
            rift.getShip().move(getDesc());
        }
    }

    /**
     * Get part of the key parts of info line
     *
     * @param type
     * @return
     */
    public String getKeyPart(int type) {

        // Get key part of info line
        String[] infoParts = super.getInfoLine().split("=");
        String keys = infoParts[1].trim();

        // Separate keys into arrows and keyboard
        String[] keyParts = keys.split(",");

        // Return certain key type
        return (keyParts[type].trim() + ", ");
    }

}
