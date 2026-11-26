import greenfoot.Actor;
import greenfoot.GreenfootImage;

/**
 * Non-blocking comedic retry banner shown briefly on missed timing or road crashes.
 */
public class RetryBanner extends Actor
{
    private int ticks = 65;

    public RetryBanner(String message)
    {
        GreenfootImage probe = new GreenfootImage(1, 1);
        int textW = Theme.measureTextWidth(probe, Theme.FONT_PROMPT, message);
        int w = Math.max(260, textW + 44);
        int h = 44;
        GreenfootImage img = new GreenfootImage(w, h);
        Theme.fillRoundRect(img, 0, 0, w, h, 10, Theme.ACCENT_STRIKE, Theme.INK_LIGHT, 2);
        Theme.drawCenteredText(img, message, w / 2, 27, Theme.FONT_PROMPT, Theme.INK_LIGHT);
        setImage(img);
    }

    @Override
    public void act()
    {
        ticks--;
        if (ticks <= 0 && getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }
}
