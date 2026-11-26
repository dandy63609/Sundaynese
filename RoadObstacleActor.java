import greenfoot.Actor;

/**
 * Scrolling village road obstacle (pothole, chicken, or angkot) in RoadBikeWorld.
 */
public class RoadObstacleActor extends Actor
{
    public enum Kind
    {
        POTHOLE("images/obs_pothole.png", 56, 36, 20, 12, "Lubang jalan"),
        CHICKEN("images/obs_chicken.png", 40, 40, 14, 14, "Ayam kampung"),
        ANGKOT("images/obs_angkot.png", 64, 96, 24, 36, "Angkot ngetem");

        final String path;
        final int w;
        final int h;
        final int hitHalfW;
        final int hitHalfH;
        final String label;

        Kind(String path, int w, int h, int hitHalfW, int hitHalfH, String label)
        {
            this.path = path;
            this.w = w;
            this.h = h;
            this.hitHalfW = hitHalfW;
            this.hitHalfH = hitHalfH;
            this.label = label;
        }
    }

    private final Kind kind;
    private final int speed;

    public RoadObstacleActor(Kind kind, int speed)
    {
        this.kind = kind;
        this.speed = speed;
        setImage(Theme.loadNearest(kind.path, kind.w, kind.h));
    }

    @Override
    public void act()
    {
        if (!(getWorld() instanceof RoadBikeWorld))
        {
            return;
        }
        RoadBikeWorld world = (RoadBikeWorld) getWorld();
        if (world.getMiniGameState() != MiniGameState.ACTIVE)
        {
            return;
        }
        setLocation(getX(), getY() + speed);
        if (getY() > Theme.WORLD_HEIGHT + 70)
        {
            world.removeObject(this);
        }
    }

    public boolean collidesWith(BikeRiderActor rider)
    {
        if (rider == null)
        {
            return false;
        }
        return Math.abs(rider.getX() - getX()) < (kind.hitHalfW + 14)
                && Math.abs(rider.getY() - getY()) < (kind.hitHalfH + 24);
    }

    public String getCrashMessage()
    {
        return "Nabrak " + kind.label + "! Ulangi dari awal jalan!";
    }
}
