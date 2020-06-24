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

    /**
     * Initialize ParticleServer
     */
    public ParticleServer() {

        // Initilalize rift particles
        riftPart = new ParticleArray(
                Globals.screenW / 2 - 200,
                Globals.screenH / 3 - 150,
                Globals.screenW / 4,
                Globals.screenW / 4,
                4, 2500, 15,
                "riftPart",
                Color.black,
                true, true);
    }

    /**
     * Draw the rift particles
     */
    public void drawRiftParticles() {
        riftPart.render();
    }
}
