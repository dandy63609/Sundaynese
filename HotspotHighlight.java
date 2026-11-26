import greenfoot.Actor;
import greenfoot.GreenfootImage;

/**
 * Bouncing golden downward arrow indicating the currently active objective hotspot.
 */
public class HotspotHighlight extends Actor
{
    private int tick = 0;
    private int baseX;
    private int baseY;

    public HotspotHighlight(int x, int y)
    {
        this.baseX = x;
        this.baseY = y;
        GreenfootImage img = new GreenfootImage(24, 24);
        int[] xs = {2, 22, 12};
        int[] ys = {4, 4, 20};
        img.setColor(Theme.INK_PRIMARY);
        img.fillPolygon(xs, ys, 3);
        int[] ixs = {5, 19, 12};
        int[] iys = {6, 6, 17};
        img.setColor(Theme.ACCENT_WARNING);
        img.fillPolygon(ixs, iys, 3);
        setImage(img);
    }

    public void setAnchor(int x, int y)
    {
        this.baseX = x;
        this.baseY = y;
    }

    @Override
    public void act()
    {
        tick++;
        int bounce = (int) Math.round(Math.sin(tick * 0.14) * 5.0);
        setLocation(baseX, baseY + bounce);
    }
}
