package components.servers.particles;

import base.Globals;
import org.newdawn.slick.Color;

/**
 * Provides particle effects
 *
 * @author David
 */
public class ParticleServer {

    // Rift particles
    private final ParticleArray riftPart;

    // Ship jet particles
    private final ParticleSource shipJet;

    /**
     * Initialize ParticleServer
     */
    public ParticleServer() {

        // Initilalize rift particles
        riftPart = new ParticleArray(
                Globals.screenW / 5,
                Globals.screenH / 5,
                3 * Globals.screenW / 5,
                3 * Globals.screenH / 5,
                4, 5000, 15,
                "riftPart",
                Color.black,
                true, true);

        // Initialize ship jet
        shipJet = new ParticleSource("shipJetPart",
                2500, Color.black, true,
                "shipJetPartConfig");
    }

    /**
     * Draw the rift particles
     */
    public void drawRiftParticles() {
        riftPart.render();
    }

    /**
     * Draw the ship jet
     * @param shipX
     * @param shipY
     */
    public void drawShipJet(int shipX, int shipY) {
        float newX = (float) shipX;
        float newY = (float) shipY;
        shipJet.render();
    }
}
