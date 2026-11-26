import greenfoot.Actor;

/**
 * Temporary floating speech bubble for ambient Sundanese banter or out-of-order quips.
 */
public class SpeechBubble extends Actor
{
    private int remainingTicks;
    private final Actor anchor;
    private final int offsetY;

    public SpeechBubble(String speaker, String message, int durationTicks, Actor anchor, int offsetY)
    {
        this.remainingTicks = durationTicks;
        this.anchor = anchor;
        this.offsetY = offsetY;
        setImage(Theme.createSpeechBubbleImage(speaker, message, 220));
    }

    @Override
    public void act()
    {
        if (anchor != null && anchor.getWorld() != null)
        {
            int clampedX = Math.max(120, Math.min(Theme.WORLD_WIDTH - 120, anchor.getX()));
            int clampedY = Math.max(Theme.HUD_HEIGHT + 34, anchor.getY() + offsetY);
            setLocation(clampedX, clampedY);
        }
        remainingTicks--;
        if (remainingTicks <= 0 && getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }
}
