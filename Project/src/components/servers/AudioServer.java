/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components.servers;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            // Initialize music

            // Initialize sound map
            Sound sound = new Sound("path");

        } catch (SlickException ex) {
            Logger.getLogger(AudioServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playSound(String name) {

    }

    public void playItemFalter() {

    }

}
