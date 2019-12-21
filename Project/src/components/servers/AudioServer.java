/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components.servers;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Globals;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Provides audio for the game
 *
 * @author David
 */
public class AudioServer {

    // Map sound names to sounds
    private HashMap<String, Sound> soundMap;

    /**
     * Initialize AudioServer
     */
    public AudioServer() {

        try {

            // Enable audio
            Globals.agc.setMusicOn(true);
            Globals.agc.setSoundOn(true);
            Globals.agc.setMusicVolume(1f);
            Globals.agc.setSoundVolume(1f);

            // Initialize ambient music and loop
            // (Track = new rave + reconfiguration 
            // + rise-of-the-synthwave + system-downtime)
            new Music(Globals.getFP("ambientMusic")).loop();

            // Initialize sound map
            soundMap = new HashMap<>();

            // For all file paths
            for (String curPath : Globals.fileList) {

                // If path is for a sound
                if (curPath.contains("audio")
                        && !curPath.contains("ambient")) {

                    // Add path and sound pair
                    soundMap.put(curPath, new Sound(curPath));
                }
            }

        } catch (SlickException ex) {
            Logger.getLogger(AudioServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Play sound at full volume
     *
     * @param name
     */
    public void playSound(String name) {
        playSound(name, 1f);
    }

    /**
     * Play sound at a certain volume
     *
     * @param name
     * @param volume
     */
    public void playSound(String name, float volume) {

        // Sound holder
        Sound soundH = null;

        // Get all sound paths
        Set<String> soundPaths = soundMap.keySet();

        // For all sound paths 
        for (String curSP : soundPaths) {

            // If path contains given name
            if (curSP.contains(name)) {

                // Save sound
                soundH = soundMap.get(curSP);

                // If name contains wildcard
                if (name.contains("?")) {

                    // Possibly search for another sound
                    if (!(Math.random() < 0.55)) {

                        // Stop searching
                        break;
                    }
                }
            }
        }

        // If no sound was found
        if (soundH == null) {

            // Throw exception
            throw new IllegalArgumentException("SoundNameError: " + name);
        }

        // Otherwise, play sound found
        soundH.play();
    }
}
