import greenfoot.Greenfoot;
import greenfoot.GreenfootSound;

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
    public static final String BGM_GAMEPLAY = "bgm_gameplay.mp3";

    private static boolean enabled = true;
    private static GreenfootSound bgm;

    private SoundFX()
    {
    }

    public static void setEnabled(boolean enable)
    {
        enabled = enable;
        if (!enable)
        {
            stopBgm();
        }
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

    /**
     * Starts looping background music if enabled.
     */
    public static void startBgm()
    {
        if (!enabled)
        {
            return;
        }
        try
        {
            if (bgm == null)
            {
                bgm = new GreenfootSound(BGM_GAMEPLAY);
            }
            if (!bgm.isPlaying())
            {
                bgm.playLoop();
            }
        }
        catch (Throwable ignored)
        {
            // Audio hardware fallback
        }
    }

    /**
     * Stops the looping background music.
     */
    public static void stopBgm()
    {
        try
        {
            if (bgm != null && bgm.isPlaying())
            {
                bgm.stop();
            }
        }
        catch (Throwable ignored)
        {
        }
    }

    /**
     * Pauses the looping background music.
     */
    public static void pauseBgm()
    {
        try
        {
            if (bgm != null && bgm.isPlaying())
            {
                bgm.pause();
            }
        }
        catch (Throwable ignored)
        {
        }
    }
}
