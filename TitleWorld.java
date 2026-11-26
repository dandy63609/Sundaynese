import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

/**
 * Title screen featuring Mang Ujang's morning veranda art and Start button.
 */
public class TitleWorld extends BaseWorld
{
    private static final int BTN_X = 64;
    private static final int BTN_Y = 398;
    private static final int BTN_W = 240;
    private static final int BTN_H = 52;

    private final KeyEdgeTracker keys = new KeyEdgeTracker("space", "enter", "e");

    public TitleWorld()
    {
        super(false);
        GameState.getInstance().reset();
        renderTitle();
        hidePrompt();
    }

    private void renderTitle()
    {
        GreenfootImage bg = Theme.loadNearest("images/bg_title.png", Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT);

        // Left-side semi-transparent card leaving Mang Ujang clearly visible on the right
        Theme.fillRoundRect(bg, 38, 54, 340, 444, 14, new greenfoot.Color(31, 26, 23, 215),
                Theme.BORDER_WOOD, 3);

        Theme.drawShadowedText(bg, "SUNDAYNESE", 208, 114, Theme.FONT_TITLE,
                Theme.ACCENT_WARNING, Theme.INK_PRIMARY);
        Theme.drawCenteredText(bg, "Simulasi Sehari Bapak-Bapak Sunda", 208, 144,
                Theme.FONT_DIALOGUE, Theme.INK_LIGHT);

        bg.setColor(Theme.BORDER_WOOD);
        bg.fillRect(64, 162, 288, 2);

        Theme.drawCenteredText(bg, "Alur Hari Ini:", 208, 192, Theme.FONT_HUD_LABEL, Theme.ACCENT_WARNING);
        String[] lines = {
            "1. Ngopi hideung & udud heula",
            "2. Chat istri minta uang belanja",
            "3. Cek nasib di aplikasi gacor",
            "4. Pakai kaos andalan & gas empang",
            "5. Mancing santai lalu pulang tidur"
        };
        bg.setFont(Theme.FONT_PHONE_MSG);
        bg.setColor(Theme.SURFACE_RUMAH);
        for (int i = 0; i < lines.length; i++)
        {
            bg.drawString(lines[i], 64, 220 + i * 24);
        }

        Theme.drawCenteredText(bg, "Kontrol: WASD / Panah  |  E / SPACE", 208, 368,
                Theme.FONT_SMALL, Theme.INK_LIGHT);

        // Mulai Hari button
        Theme.fillRoundRect(bg, BTN_X, BTN_Y, BTN_W, BTN_H, 10, Theme.ACCENT_UANG, Theme.ACCENT_WARNING, 2);
        Theme.drawCenteredText(bg, "MULAI HARI  [SPACE]", BTN_X + BTN_W / 2, BTN_Y + 32,
                Theme.FONT_PROMPT, Theme.INK_LIGHT);

        setBackground(bg);
    }

    @Override
    public void act()
    {
        if (keys.justPressedAny("space", "enter", "e"))
        {
            startGame();
            return;
        }
        if (Greenfoot.mouseClicked(null))
        {
            MouseInfo mi = Greenfoot.getMouseInfo();
            if (mi != null && mi.getX() >= BTN_X && mi.getX() <= BTN_X + BTN_W
                    && mi.getY() >= BTN_Y && mi.getY() <= BTN_Y + BTN_H)
            {
                startGame();
            }
        }
    }

    public void startGame()
    {
        GameState.getInstance().reset();
        SoundFX.play(SoundFX.HIT);
        Greenfoot.setWorld(new HouseWorld(HouseWorld.SpawnPoint.BESIDE_BED));
    }
}
