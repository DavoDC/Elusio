package components.servers.particles;

import java.util.ArrayList;
import org.newdawn.slick.Color;

/**
 * Models multiple particle sources
 *
 * @author David
 */
public class ParticleArray {

    // The underlying particle sources
    private final ArrayList<ParticleSource> sources;

    // The update delta
    private final int updateDelta;

    /**
     * Create ParticleArray
     *
     * @param initX Initial X pos
     * @param initY Initial Y pos
     * @param xSpacing
     * @param ySpacing
     * @param num Amount of sources
     * @param partAvail Particle available for each
     * @param updateDelta Controls the update rate
     * @param name Name for images and config
     * @param transMask Transparent color
     * @param glowOn Glow effect status
     * @param diffImages True if each has a different image
     */
    public ParticleArray(
            float initX, float initY, float xSpacing, float ySpacing,
            int num, int partAvail, int updateDelta,
            String name,
            Color transMask,
            boolean glowOn, boolean diffImages) {

        // Initialize list
        sources = new ArrayList<>();

        // Save updateDelta
        this.updateDelta = updateDelta;

        // Current positions
        float curX = initX;
        float curY = initY;

        // For all images
        for (int i = 0; i < num; i++) {

            // Get image name
            String imgName = name;
            if (diffImages) {
                imgName += i;
            }

            // Create particle source
            ParticleSource ps = new ParticleSource(
                    imgName + ".png",
                    partAvail,
                    transMask,
                    glowOn,
                    name + "Config.xml");

            // Set position and add
            ps.setPosition(curX, curY);
            sources.add(ps);

            // Shift X
            curX += xSpacing;

            // If new column needed
            if (i != 0 && ((i + 1) % 2 == 0)) {

                // Reset X (go back to left)
                curX = initX;

                // Increase Y (move downwards)
                curY += ySpacing;
            }
        }
    }

    /**
     * Render the particle array
     */
    public void render() {

        // For all particle sources
        for (ParticleSource curSrc : sources) {

            // Update particles
            curSrc.update(updateDelta);

            // Render particles
            curSrc.render();
        }
    }
}
