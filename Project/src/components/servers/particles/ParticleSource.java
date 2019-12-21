package components.servers.particles;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Globals;
import org.newdawn.slick.Color;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

/**
 *
 * @author David
 */
public class ParticleSource extends ParticleSystem {

    /**
     * Create a particle source
     *
     * @param imageName The name of the particle image
     * @param maxParticles The number of particles available
     * @param mask The color used to make the image transparent
     * @param glowOn Glow effect status
     * @param configName The name of the config XML
     */
    public ParticleSource(
            String imageName,
            int maxParticles,
            Color mask,
            boolean glowOn,
            String configName) {

        // Call ParticleSystem constructor
        super(Globals.getFP(imageName), maxParticles, mask);

        // Adjust ParticleSystem
        if (glowOn) {
            setBlendingMode(BLEND_ADDITIVE);
        } else {
            setBlendingMode(BLEND_COMBINE);
        }
        setPosition(Globals.screenW / 2, Globals.screenH / 2);
        setVisible(true);

        // Add emitter
        try {
            String confPath = Globals.getFP(configName);
            ParticleEmitter pe = ParticleIO.loadEmitter(confPath);
            pe.setEnabled(true);
            addEmitter(pe);
        } catch (IOException ex) {
            Logger.getLogger(ParticleSource.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
}
