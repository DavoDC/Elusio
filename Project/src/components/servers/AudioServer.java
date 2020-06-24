package components.servers;

import java.util.logging.Level;
import java.util.logging.Logger;

import base.Globals;
import components.underlying.LooseMap;

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
    private LooseMap<Sound> soundMap;

    /**
     * Initialize AudioServer
     */
    public AudioServer() {

        try {

            // Enable audio
            Globals.agc.setMusicOn(!Globals.inIDE);
            Globals.agc.setSoundOn(true);
            Globals.agc.setMusicVolume(0.9f);
            Globals.agc.setSoundVolume(0.6f);

            // Initialize ambient music and loop
            // (Track = new rave + reconfiguration 
            // + rise-of-the-synthwave + system-downtime)
            new Music(Globals.getFP("ambientMusic")).loop();

            // Initialize sound map
            soundMap = new LooseMap<>();

            // For all file paths
            for (String curPath : Globals.fileList) {

                // If path is for a sound
                if (curPath.contains("sound")
                        && curPath.contains(".ogg")) {

                    // Add path and sound pair
                    soundMap.put(curPath, new Sound(curPath));
                }
            }

        } catch (SlickException ex) {
            Logger.getLogger(AudioServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Play sound at a certain volume
     *
     * @param name
     * @param pitch
     * @param vol
     */
    public void playSound(String name, float pitch, float vol) {

        // Retrieve sound 
        Sound sound = soundMap.get(name);

        // If sound is not playing
        if (!sound.playing()) {

            // Play sound
            sound.play(pitch, vol);
        }
    }

    /**
     * Play sound at default volume and pitch
     *
     * @param name
     */
    public void playSound(String name) {
        playSound(name, 1f, 1f);
    }
}
