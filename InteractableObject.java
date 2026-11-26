import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.World;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Interactive world prop (table, phone, wardrobe, classic bike, fishing dock, bed).
 */
public class InteractableObject extends Actor
{
    private final String id;
    private final String actionVerb;
    private final Set<GameStep> activeSteps;
    private final CollisionRect solidBox;
    private final int interactRadius;
    private final boolean hasShadow;

    public InteractableObject(String id, String imagePath, int width, int height,
                              String actionVerb, int interactRadius,
                              CollisionRect solidBox, boolean hasShadow, GameStep... steps)
    {
        this.id = id;
        this.actionVerb = actionVerb;
        this.interactRadius = interactRadius;
        this.solidBox = solidBox;
        this.hasShadow = hasShadow;
        this.activeSteps = new HashSet<>(Arrays.asList(steps));
        GreenfootImage img = Theme.loadNearest(imagePath, width, height);
        setImage(img);
    }

    @Override
    protected void addedToWorld(World world)
    {
        if (hasShadow)
        {
            int sw = Math.max(28, getImage().getWidth() - 8);
            int sh = 12;
            int offsetY = getImage().getHeight() / 2 - 2;
            world.addObject(new ShadowActor(this, sw, sh, offsetY), getX(), getY() + offsetY);
        }
    }

    public String getId()
    {
        return id;
    }

    public String getActionVerb()
    {
        return actionVerb;
    }

    public CollisionRect getSolidBox()
    {
        return solidBox;
    }

    public boolean isActiveFor(GameStep step)
    {
        return activeSteps.contains(step);
    }

    public boolean isNear(Actor actor)
    {
        if (actor == null)
        {
            return false;
        }
        int dx = actor.getX() - getX();
        int dy = actor.getY() - getY();
        return (dx * dx + dy * dy) <= interactRadius * interactRadius;
    }

    public int distanceSq(Actor actor)
    {
        int dx = actor.getX() - getX();
        int dy = actor.getY() - getY();
        return dx * dx + dy * dy;
    }
}
