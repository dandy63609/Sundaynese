import greenfoot.Actor;
import greenfoot.World;

/**
 * Ambient Sundanese dad fishing buddy who speaks once in mixed Indonesian/Sundanese.
 */
public class NpcDadActor extends Actor
{
    private static final String[] AMBIENT_LINES = {
        "Kopi hideung mah wajib, lur!",
        "Istri kerja di luar negeri, kita mah jaga empang wae.",
        "Eta pelampung gerak, Mang! Tarik atuh!",
        "Aduh rungkad deui tadi, ayeuna mah fokus narik nila!",
        "Kaos baraya teh aura-na beda pisan euy!"
    };

    private final String speakerName;
    private int chatTimer;
    private int lineIndex;
    private boolean hasSpoken = false;
    private ShadowActor shadow;

    public NpcDadActor(String speakerName, String imagePath, boolean mirror, int initialDelay, int startLineIndex)
    {
        this.speakerName = speakerName;
        this.chatTimer = initialDelay;
        this.lineIndex = startLineIndex % AMBIENT_LINES.length;

        greenfoot.GreenfootImage img = Theme.loadNearest(imagePath, 44, 44);
        if (mirror)
        {
            img.mirrorHorizontally();
        }
        setImage(img);
    }

    @Override
    protected void addedToWorld(World world)
    {
        if (shadow == null)
        {
            shadow = new ShadowActor(this, 32, 12, 18);
            world.addObject(shadow, getX(), getY() + 18);
        }
    }

    @Override
    public void act()
    {
        if (getWorld() == null || hasSpoken)
        {
            return;
        }
        if (getWorld() instanceof ExplorationWorld && ((ExplorationWorld) getWorld()).hasActiveOverlay())
        {
            return;
        }
        chatTimer--;
        if (chatTimer <= 0)
        {
            speakNextLine();
            hasSpoken = true;
        }
    }

    public boolean hasSpoken()
    {
        return hasSpoken;
    }

    private void speakNextLine()
    {
        String line = AMBIENT_LINES[lineIndex % AMBIENT_LINES.length];
        if (lineIndex == 4 && !GameState.getInstance().isWearingFishingShirt())
        {
            line = AMBIENT_LINES[0];
        }
        lineIndex = (lineIndex + 1) % AMBIENT_LINES.length;
        getWorld().addObject(new SpeechBubble(speakerName, line, 145, this, -48), getX(), getY() - 48);
    }
}
