import greenfoot.GreenfootImage;
import greenfoot.World;

/**
 * Base 800x600 Greenfoot world providing persistent HUD, prompt strip, and paint order.
 */
public abstract class BaseWorld extends World
{
    protected HudOverlay hudOverlay;
    protected PromptPill promptPill;

    protected BaseWorld(boolean showHud)
    {
        super(Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT, 1, false);
        configurePaintOrder();
        if (showHud)
        {
            hudOverlay = new HudOverlay();
            addObject(hudOverlay, Theme.WORLD_WIDTH / 2, Theme.HUD_HEIGHT / 2);
        }
        promptPill = new PromptPill();
        addObject(promptPill, Theme.WORLD_WIDTH / 2, 574);
    }

    protected void configurePaintOrder()
    {
        setPaintOrder(
            RetryBanner.class,
            HudOverlay.class,
            PromptPill.class,
            SpeechBubble.class,
            MiniGameOverlay.class,
            HotspotHighlight.class,
            BapakActor.class,
            NpcDadActor.class,
            BikeRiderActor.class,
            RoadObstacleActor.class,
            InteractableObject.class,
            ShadowActor.class
        );
    }

    protected void setBackgroundScaled(String imagePath)
    {
        GreenfootImage bg = Theme.loadNearest(imagePath, Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT);
        setBackground(bg);
    }

    public void showPrompt(String keyLabel, String actionText)
    {
        if (promptPill != null)
        {
            promptPill.show(keyLabel, actionText);
        }
    }

    public void hidePrompt()
    {
        if (promptPill != null)
        {
            promptPill.hide();
        }
    }

    public void showRetryFlash(String message)
    {
        removeObjects(getObjects(RetryBanner.class));
        addObject(new RetryBanner(message), Theme.WORLD_WIDTH / 2, 110);
    }
}
