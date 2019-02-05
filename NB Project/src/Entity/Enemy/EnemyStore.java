package Entity.Enemy;

import Entity.Entity;
import Entity.EntityStore;
import Entity.Player;
import Utility.TiledMapPlus;
import java.util.ArrayList;

/**
 *
 * @author David Charkey
 */
public class EnemyStore extends EntityStore
{

    public EnemyStore(TiledMapPlus map)
    {
        super(map);
    }

    @Override
    public ArrayList<Entity> getEntities()
    {
        return null;
    }

    @Override
    public String getEntityLayerName()
    {
        return "Enemies";
    }

    @Override
    public Entity getLastInteractedEntity(Player player)
    {
        return null;
    }

    @Override
    public boolean getEntityInteractionStatus()
    {
        return false;
    }

    @Override
    public void switchEntityInteractionStatus()
    {
    }

}
