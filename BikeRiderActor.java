import greenfoot.Actor;
import greenfoot.GreenfootImage;

/**
 * Classic motorcycle rider actor in RoadBikeWorld switching across 3 rural lanes.
 */
public class BikeRiderActor extends Actor
{
    public static final int[] LANE_X = {320, 400, 480};

    private int currentLane = 1;
    private final KeyEdgeTracker keys = new KeyEdgeTracker("left", "right", "a", "d");

    public BikeRiderActor()
    {
        String sprite = GameState.getInstance().isWearingFishingShirt()
                ? "images/bike_rider_shirt.png"
                : "images/bike_rider.png";
        GreenfootImage img = Theme.loadNearest(sprite, 48, 72);
        setImage(img);
    }

    public void resetLane()
    {
        currentLane = 1;
        setLocation(LANE_X[currentLane], 460);
        keys.syncHeldKeys("left", "right", "a", "d");
    }

    public int getCurrentLane()
    {
        return currentLane;
    }

    public void moveLane(int delta)
    {
        currentLane = Math.max(0, Math.min(LANE_X.length - 1, currentLane + delta));
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
            keys.syncHeldKeys("left", "right", "a", "d");
            return;
        }

        if (keys.justPressedAny("left", "a"))
        {
            moveLane(-1);
        }
        if (keys.justPressedAny("right", "d"))
        {
            moveLane(1);
        }

        int targetX = LANE_X[currentLane];
        int dx = targetX - getX();
        int step = Math.abs(dx) <= 12 ? dx : (dx > 0 ? 12 : -12);
        setLocation(getX() + step, getY());
    }
}
