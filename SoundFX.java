import greenfoot.Greenfoot;

/**
 * Audio-optional sound helper wrapping GreenfootSound/Greenfoot.playSound safely.
 */
public final class SoundFX
{
    public static final String COFFEE = "sfx_coffee.wav";
    public static final String HIT = "sfx_hit.wav";
    public static final String MISS = "sfx_miss.wav";
    public static final String CHAT = "sfx_chat.wav";
    public static final String COIN = "sfx_coin.wav";
    public static final String SLOT = "sfx_slot.wav";
    public static final String SHIRT = "sfx_shirt.wav";
    public static final String BIKE = "sfx_bike.wav";
    public static final String SPLASH = "sfx_splash.wav";
    public static final String WIN = "sfx_win.wav";

    private static boolean enabled = true;

    private SoundFX()
    {
    }

    public static void setEnabled(boolean enable)
    {
        enabled = enable;
    }

    public static boolean isEnabled()
    {
        return enabled;
    }

    /**
     * Plays a WAV effect from sounds/ without crashing if audio is unavailable.
     */
    public static void play(String fileName)
    {
        if (!enabled || fileName == null || fileName.isEmpty())
        {
            return;
        }
        try
        {
            Greenfoot.playSound(fileName);
        }
        catch (Throwable ignored)
        {
            // Headless tests or muted environments keep running cleanly.
        }
    }
}
