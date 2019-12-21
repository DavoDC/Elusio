package components.servers.particles;

import main.Globals;
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
                Globals.screenW / 5,
                Globals.screenH / 5,
                3 * Globals.screenW / 5,
                3 * Globals.screenH / 5,
                4, 5000, 15,
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
