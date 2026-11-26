import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import java.util.List;

/**
 * Top-down protagonist Mang Ujang with WASD/Arrow movement, wall/furniture collision,
 * and automatic outfit swap after equipping the black KDM fishing shirt.
 */
public class BapakActor extends Actor
{
    private static final int SPEED = 4;
    private static final int HITBOX_HALF_W = 14;
    private static final int HITBOX_HALF_H = 14;

    private final GreenfootImage[] casualRight;
    private final GreenfootImage[] casualLeft;
    private final GreenfootImage[] shirtRight;
    private final GreenfootImage[] shirtLeft;

    private boolean facingLeft = false;
    private int animTick = 0;
    private ShadowActor shadow;

    public BapakActor()
    {
        casualRight = loadTriple("images/bapak_topdown.png", "images/bapak_walk1.png", "images/bapak_walk2.png");
        casualLeft = mirrorTriple(casualRight);
        shirtRight = loadTriple("images/bapak_shirt_topdown.png", "images/bapak_shirt_walk1.png", "images/bapak_shirt_walk2.png");
        shirtLeft = mirrorTriple(shirtRight);
        refreshSprite(false);
    }

    private GreenfootImage[] loadTriple(String idlePath, String w1Path, String w2Path)
    {
        return new GreenfootImage[] {
            Theme.loadNearest(idlePath, 44, 44),
            Theme.loadNearest(w1Path, 44, 44),
            Theme.loadNearest(w2Path, 44, 44)
        };
    }

    private GreenfootImage[] mirrorTriple(GreenfootImage[] src)
    {
        GreenfootImage[] out = new GreenfootImage[src.length];
        for (int i = 0; i < src.length; i++)
        {
            out[i] = new GreenfootImage(src[i]);
            out[i].mirrorHorizontally();
        }
        return out;
    }

    @Override
    protected void addedToWorld(World world)
    {
        if (shadow == null)
        {
            shadow = new ShadowActor(this, 32, 12, 18);
        }
        if (shadow.getWorld() == null)
        {
            world.addObject(shadow, getX(), getY() + 18);
        }
    }

    @Override
    public void act()
    {
        if (!(getWorld() instanceof ExplorationWorld))
        {
            return;
        }
        ExplorationWorld world = (ExplorationWorld) getWorld();
        if (world.hasActiveOverlay())
        {
            refreshSprite(false);
            return;
        }

        int dx = 0;
        int dy = 0;
        if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))
        {
            dx -= SPEED;
        }
        if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right"))
        {
            dx += SPEED;
        }
        if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up"))
        {
            dy -= SPEED;
        }
        if (Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down"))
        {
            dy += SPEED;
        }

        if (dx < 0)
        {
            facingLeft = true;
        }
        else if (dx > 0)
        {
            facingLeft = false;
        }

        boolean moved = false;
        List<CollisionRect> colliders = world.getColliders();
        if (dx != 0 && canMoveTo(getX() + dx, getY(), colliders))
        {
            setLocation(getX() + dx, getY());
            moved = true;
        }
        if (dy != 0 && canMoveTo(getX(), getY() + dy, colliders))
        {
            setLocation(getX(), getY() + dy);
            moved = true;
        }

        if (moved)
        {
            animTick++;
        }
        else
        {
            animTick = 0;
        }
        refreshSprite(moved);
    }

    public boolean canMoveTo(int nextX, int nextY, List<CollisionRect> colliders)
    {
        int rx = nextX - HITBOX_HALF_W;
        int ry = nextY - HITBOX_HALF_H;
        int rw = HITBOX_HALF_W * 2;
        int rh = HITBOX_HALF_H * 2;

        if (rx < 0 || ry < Theme.HUD_HEIGHT || rx + rw > Theme.WORLD_WIDTH || ry + rh > Theme.PROMPT_BAR_TOP + 12)
        {
            return false;
        }
        for (CollisionRect rect : colliders)
        {
            if (rect.intersects(rx, ry, rw, rh))
            {
                return false;
            }
        }
        return true;
    }

    public void refreshSprite(boolean moving)
    {
        boolean shirt = GameState.getInstance().isWearingFishingShirt();
        GreenfootImage[] set = shirt ? (facingLeft ? shirtLeft : shirtRight)
                                     : (facingLeft ? casualLeft : casualRight);
        int idx = 0;
        if (moving)
        {
            idx = ((animTick / 8) % 2 == 0) ? 1 : 2;
        }
        setImage(set[idx]);
    }
}
