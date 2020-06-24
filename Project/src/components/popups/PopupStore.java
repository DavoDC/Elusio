package components.popups;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import base.Globals;

import org.newdawn.slick.Graphics;

/**
 * Handles the displaying of popups
 *
 * @author David
 */
public class PopupStore {

    // Maps names to popups
    private final HashMap<String, Popup> popMap;

    // Current popup variables
    private Popup curPopup;
    private boolean shouldShowPopup;

    /**
     * Create a popup displayer
     */
    public PopupStore() {

        // Initialize popup map
        popMap = new HashMap<>();

        // Add popup for introduction to Rift state
        integratePopup(new Popup(
                "SpRealmIntro",
                new String[]{
                    // 1
                    "Kyrios: Okay, we're in the restoration realm. "
                    + "The hyphenspace drive needed restoring.",
                    // 2
                    "Pneuma: What an amazing mission that was! "
                    + "There were difficulties, "
                    + "but they helped me evolve",
                    // 3
                    "Kyrios: That was what I intended for you "
                    + "- its excellent that you recognized that",
                    // 4
                    "Pneuma: How wise you are, Master. "
                    + "I'm still amazed about how diverse "
                    + "and beautiful that planet was!",
                    // 5
                    "Kyrios: Indeed, it is. "
                    + "I was assigned there many lives ago. ",
                    // 6
                    "Pneuma: What is my next assignment? "
                    + "I'm prepared to evolve again, even if I have to endure!",
                    // 7.1
                    "Kyrios: I commend your eagerness and resolve. "
                    + "A strange dimension has been disconnected from...",
                    // 7.2
                    "Kyrios: ...its natural divine energy rhythm - "
                    + "you'll need to re-align it with Source energy",
                    // 8
                    "Pneuma: Sounds like a mission! "
                    + "I'm ready to go between space-time, into hyphenspace!"
                }) {
            @Override
            public void doPostShowAction() {

                // Disable input
                Globals.isInputBlocked = true;

                // Intro has completed
                Globals.isIntroRiftDone = true;

                // Go to actual play state
                Globals.changeState("Play", true);

                // Schedule next event
                long shipAppear = 3369;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        // Play ship land sound
                        Globals.audioServer.playSound("shipLand");

                        // Show alien ship
                        Globals.obStore.removeEncounterByName("StartAlien");

                    }
                }, shipAppear);

                // Schedule next event
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        // Show brace popup
                        Globals.popStore.loadPopup("PlayIntroBrace");

                    }
                }, shipAppear + 4000);
            }
        }, false);

        // Add brace popup
        integratePopup(new Popup(
                "PlayIntroBrace",
                new String[]{
                    "Kyrios: Head out into the realm now, "
                    + "but brace yourself for incarnation!"
                }
        ) {
            @Override
            public void doPostShowAction() {

                // Player becomes visible
                Globals.player.setVisible(true);

                // Load next popup
                Globals.popStore.loadPopup("PlayIntro");
            }
        }, true);

        // Add play intro popup
        integratePopup(new Popup(
                "PlayIntro",
                new String[]{
                    "Pneuma2: This new form feels so weird... Its uncomfortable",
                    "Kyrios: Your growth will involve adapting and learning.",
                    "Kyrios: You have been provided with basic information...",
                    "Kyrios: ...and a magic haven bag with an analysing module.",
                    "Kyrios: I can only guide you sparingly from now on. "
                    + "Make wise decisions",}
        ) {
            @Override
            public void doPostShowAction() {

            }
        }, true);

    }

    /**
     * Integrate a popup into the popup map
     *
     * @param p
     */
    private void integratePopup(Popup p, boolean isInputBlocker) {

        // Adjust popup
        p.setInputBlocking(isInputBlocker);

        // Add popup with name
        popMap.put(p.getName(), p);
    }

    /**
     * Load in a popup for showing
     *
     * @param popupName
     */
    public void loadPopup(String popupName) {

        // Save popup
        curPopup = popMap.get(popupName);

        // If popup found was null
        if (curPopup == null) {
            // Throw error
            throw new IllegalArgumentException("loadPopup query issue");
        }

        // Request that it is shown
        shouldShowPopup = true;
    }

    /**
     * Renders current popup
     *
     * @param g
     */
    public void renderCurPopup(Graphics g) {

        // If popup is null
        if (curPopup == null) {

            // Do not try to render
            return;
        }

        // If a popup should be shown
        if (shouldShowPopup) {

            // If popup is input blocker
            if (curPopup.isInputBlocker()) {

                // Block input
                Globals.isInputBlocked = true;
            }

            // Make current popup visible
            curPopup.setVisible(true);

            // The popup has now been shown
            shouldShowPopup = false;
        }

        // Render current popup
        curPopup.render(g);
    }
}
