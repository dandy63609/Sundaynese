import greenfoot.Greenfoot;
import java.util.HashMap;
import java.util.Map;

/**
 * Tracks rising-edge key presses to prevent held keys from triggering multiple actions.
 */
public class KeyEdgeTracker
{
    private final Map<String, Boolean> previousState = new HashMap<>();

    public KeyEdgeTracker(String... keys)
    {
        syncHeldKeys(keys);
    }

    /**
     * Captures currently held keys so transitioning into a scene never fires immediately.
     */
    public void syncHeldKeys(String... keys)
    {
        for (String key : keys)
        {
            boolean down = false;
            try
            {
                down = Greenfoot.isKeyDown(key);
            }
            catch (Throwable ignored)
            {
                down = false;
            }
            previousState.put(key, down);
        }
    }

    public boolean justPressed(String key)
    {
        boolean down;
        try
        {
            down = Greenfoot.isKeyDown(key);
        }
        catch (Throwable ignored)
        {
            down = false;
        }
        boolean wasDown = previousState.getOrDefault(key, false);
        previousState.put(key, down);
        return down && !wasDown;
    }

    public boolean justPressedAny(String... keys)
    {
        boolean pressed = false;
        for (String key : keys)
        {
            if (justPressed(key))
            {
                pressed = true;
            }
        }
        return pressed;
    }
}
