import greenfoot.Actor;

/**
 * Flat 35%-opacity ground shadow oval anchored under standing top-down actors.
 */
public class ShadowActor extends Actor
{
    private final Actor owner;
    private final int offsetY;

    public ShadowActor(Actor owner, int width, int height, int offsetY)
    {
        this.owner = owner;
        this.offsetY = offsetY;
        setImage(Theme.createShadowOval(width, height));
    }

    @Override
    public void act()
    {
        if (owner == null || owner.getWorld() == null)
        {
            if (getWorld() != null)
            {
                getWorld().removeObject(this);
            }
            return;
        }
        setLocation(owner.getX(), owner.getY() + offsetY);
    }
}
