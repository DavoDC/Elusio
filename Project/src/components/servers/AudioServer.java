/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components.servers;

import java.util.HashMap;
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
        // Initialize music
        // reconfiguration
        // system-downtime
        // new-rave
        // rise-of-the-synthwave
        

         // Initialize sound map
        // try {
        
       
        //Sound sound = new Sound("path");
//        } catch (SlickException ex) {
//            Logger.getLogger(AudioServer.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    private void addSound(String name)
    {
       // GLobals.soundLoc
       // ‘Add sound’ method = Adds to hashmap <name, Global.soundloc + name>s
    }

    /**
     * 
     * @param name 
     */
    public void playSound(String name) {
        
        System.out.print(" played custom sound!");

        // play full volume
        playSound(name, 1f);
    }
    
    /**
     * 
     * @param name
     * @param volume 
     */
     public void playSound(String name, float volume) {
        
        System.out.print(" played custom sound!");

    }

    public void playItemFalter() {

         System.out.print(" played item falter sound!");
    }

}
