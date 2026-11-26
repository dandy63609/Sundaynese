import greenfoot.Actor;
import greenfoot.GreenfootImage;

/**
 * Bottom interaction hint pill displaying keycaps and action verbs.
 */
public class PromptPill extends Actor
{
    private String lastKey = "";
    private String lastAction = "";
    private boolean visible = false;

    public PromptPill()
    {
        hide();
    }

    public void show(String keyLabel, String actionText)
    {
        if (visible && keyLabel.equals(lastKey) && actionText.equals(lastAction))
        {
            return;
        }
        lastKey = keyLabel;
        lastAction = actionText;
        visible = true;
        setImage(Theme.createPromptPillImage(keyLabel, actionText));
    }

    public void hide()
    {
        if (!visible && getImage() != null && getImage().getWidth() == 1)
        {
            return;
        }
        visible = false;
        lastKey = "";
        lastAction = "";
        setImage(new GreenfootImage(1, 1));
    }

    public boolean isShowing()
    {
        return visible;
    }
}
