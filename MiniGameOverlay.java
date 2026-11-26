import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.World;

/**
 * Base class for front/POV cut-ins and in-world mini-game overlays implementing MiniGame.
 */
public abstract class MiniGameOverlay extends Actor implements MiniGame
{
    protected MiniGameState miniGameState = MiniGameState.INTRO_POV;
    protected int stateTimer = 0;
    protected final GreenfootImage canvas;
    protected final KeyEdgeTracker keys = new KeyEdgeTracker("space", "e");

    protected MiniGameOverlay()
    {
        canvas = new GreenfootImage(Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT);
        setImage(canvas);
    }

    @Override
    protected void addedToWorld(World world)
    {
        keys.syncHeldKeys("space", "e");
    }

    @Override
    public void start()
    {
        miniGameState = MiniGameState.ACTIVE;
        stateTimer = 0;
    }

    @Override
    public boolean isCompleted()
    {
        return miniGameState == MiniGameState.COMPLETE;
    }

    @Override
    public MiniGameState getMiniGameState()
    {
        return miniGameState;
    }

    protected void transitionState(MiniGameState next)
    {
        miniGameState = next;
        stateTimer = 0;
    }

    /**
     * Draws a dimmed backdrop and centered card panel for POV mini-games.
     */
    protected void drawCardFrame(int cardX, int cardY, int cardW, int cardH, greenfoot.Color borderColor)
    {
        canvas.clear();
        canvas.setColor(Theme.SCRIM_DARK);
        canvas.fillRect(0, Theme.HUD_HEIGHT, Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT - Theme.HUD_HEIGHT);
        Theme.fillRoundRect(canvas, cardX, cardY, cardW, cardH, 14, Theme.SURFACE_HUD, borderColor, 3);
    }

    /**
     * Draws a standard 360x32px TimingBar with green safe zone and sliding yellow marker.
     */
    protected void drawTimingBar(int barX, int barY, int barW, int barH,
                                 double markerPos, double zoneStart, double zoneEnd)
    {
        Theme.fillRoundRect(canvas, barX, barY, barW, barH, 6, Theme.INK_PRIMARY, Theme.INK_LIGHT, 2);

        int zx = barX + (int) Math.round(zoneStart * barW);
        int zw = (int) Math.round((zoneEnd - zoneStart) * barW);
        canvas.setColor(Theme.ACCENT_UANG);
        canvas.fillRect(zx, barY + 3, zw, barH - 6);

        int mx = barX + (int) Math.round(markerPos * (barW - 10));
        Theme.fillRoundRect(canvas, mx, barY - 4, 10, barH + 8, 3, Theme.ACCENT_WARNING, Theme.INK_PRIMARY, 1);
    }

    protected void closeOverlay()
    {
        if (getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }
}
