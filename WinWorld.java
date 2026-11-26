import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

/**
 * End-of-day bedtime WinWorld displaying final cash summary and Main Lagi button.
 */
public class WinWorld extends BaseWorld
{
    private static final int BTN_X = Theme.WORLD_WIDTH / 2 - 125;
    private static final int BTN_Y = 470;
    private static final int BTN_W = 250;
    private static final int BTN_H = 50;

    private final KeyEdgeTracker keys = new KeyEdgeTracker("space", "enter", "e");

    public WinWorld()
    {
        super(false);
        SoundFX.play(SoundFX.WIN);
        renderWin();
        hidePrompt();
    }

    private void renderWin()
    {
        GreenfootImage bg = Theme.loadNearest("images/bg_win.png", Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT);

        Theme.fillRoundRect(bg, 150, 36, 500, 178, 14, new Color(31, 26, 23, 215), Theme.ACCENT_WARNING, 2);
        Theme.drawShadowedText(bg, "HARI INI BERES!", Theme.WORLD_WIDTH / 2, 82,
                Theme.FONT_TITLE, Theme.ACCENT_WARNING, Theme.INK_PRIMARY);
        Theme.drawCenteredText(bg, "\"Hari ini beres, Sundaynese sejati.\"", Theme.WORLD_WIDTH / 2, 114,
                Theme.FONT_SUBTITLE, Theme.INK_LIGHT);
        Theme.drawCenteredText(bg, "Sisa Uang: " + GameState.formatRupiah(GameState.getInstance().getMoney()),
                Theme.WORLD_WIDTH / 2, 148, Theme.FONT_HUD_VALUE, Theme.ACCENT_UANG);
        Theme.drawCenteredText(bg, "Kopi habis, istri transfer, judol rungkad, empang dapet 3 nila!",
                Theme.WORLD_WIDTH / 2, 182, Theme.FONT_DIALOGUE, Theme.SURFACE_RUMAH);

        Theme.fillRoundRect(bg, BTN_X, BTN_Y, BTN_W, BTN_H, 10, Theme.ACCENT_UANG, Theme.ACCENT_WARNING, 2);
        Theme.drawCenteredText(bg, "MAIN LAGI  [SPACE]", BTN_X + BTN_W / 2, BTN_Y + 31,
                Theme.FONT_PROMPT, Theme.INK_LIGHT);

        setBackground(bg);
    }

    @Override
    public void act()
    {
        if (keys.justPressedAny("space", "enter", "e"))
        {
            restartGame();
            return;
        }
        if (Greenfoot.mouseClicked(null))
        {
            MouseInfo mi = Greenfoot.getMouseInfo();
            if (mi != null && mi.getX() >= BTN_X && mi.getX() <= BTN_X + BTN_W
                    && mi.getY() >= BTN_Y && mi.getY() <= BTN_Y + BTN_H)
            {
                restartGame();
            }
        }
    }

    public void restartGame()
    {
        GameState.getInstance().reset();
        Greenfoot.setWorld(new TitleWorld());
    }
}
