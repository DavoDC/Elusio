package Entity.Obstacle;

import Entity.Base.Entity;

/**
 * Represents an obstructed area
 *
 * @author David Charkey
 */
public class Obstacle extends Entity
{

    // Matching zone
    private String obZoneName;

    // Records whether to unblock when zone unlocked
    private boolean unblockOn;

    public Obstacle(
            String name,
            String obZoneName,
            int tlc,
            int tlr,
            int w,
            int h,
            boolean unblockOn)
    {
        super(name, tlc, tlr, w, h);

        this.obZoneName = obZoneName;

        this.unblockOn = unblockOn;
    }

    public String getOZName()
    {
        return obZoneName;
    }

    public boolean isUnblockOn()
    {
        return unblockOn;
    }

}
