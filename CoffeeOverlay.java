import greenfoot.Color;
import greenfoot.GreenfootImage;

/**
 * FR-5: Timed Sip & Puff mini-game with oscillating timing bar and smoke animation.
 */
public class CoffeeOverlay extends MiniGameOverlay
{
    public static final int TARGET_HITS = 4;
    private static final double ZONE_START = 0.36;
    private static final double ZONE_END = 0.64; // 28% safe zone (>= 25% spec)

    private final GreenfootImage povCoffee;
    private int hits = 0;
    private double markerPos = 0.0;
    private double markerSpeed = 0.022;
    private int smokeTick = 0;

    public CoffeeOverlay()
    {
        povCoffee = Theme.loadNearest("images/bapak_pov_coffee.png", 240, 240);
        reset();
    }

    @Override
    public void reset()
    {
        hits = 0;
        markerPos = 0.05;
        markerSpeed = 0.022;
        smokeTick = 0;
        miniGameState = MiniGameState.INTRO_POV;
        stateTimer = 0;
        redraw();
    }

    @Override
    public void act()
    {
        stateTimer++;
        smokeTick++;

        switch (miniGameState)
        {
            case INTRO_POV:
                if (stateTimer >= 45 || keys.justPressedAny("space", "e"))
                {
                    start();
                }
                break;

            case ACTIVE:
                markerPos += markerSpeed;
                if (markerPos >= 1.0)
                {
                    markerPos = 1.0;
                    markerSpeed = -Math.abs(markerSpeed);
                }
                else if (markerPos <= 0.0)
                {
                    markerPos = 0.0;
                    markerSpeed = Math.abs(markerSpeed);
                }

                if (keys.justPressed("space"))
                {
                    attemptSip();
                }
                break;

            case RETRY_FLASH:
                if (stateTimer >= 45)
                {
                    transitionState(MiniGameState.ACTIVE);
                }
                break;

            case COMPLETE:
                if (stateTimer >= 35 || keys.justPressedAny("space", "e"))
                {
                    GameState.getInstance().completeAndAdvance(GameStep.STEP_1_COFFEE, GameStep.STEP_2_CHAT_WIFE);
                    closeOverlay();
                }
                break;
        }
        redraw();
    }

    /**
     * Evaluates a timing press; returns true if inside the green target zone.
     */
    public boolean attemptSip()
    {
        if (markerPos >= ZONE_START && markerPos <= ZONE_END)
        {
            hits++;
            SoundFX.play(SoundFX.COFFEE);
            if (hits >= TARGET_HITS)
            {
                SoundFX.play(SoundFX.HIT);
                transitionState(MiniGameState.COMPLETE);
            }
            return true;
        }
        else
        {
            SoundFX.play(SoundFX.MISS);
            transitionState(MiniGameState.RETRY_FLASH);
            return false;
        }
    }

    public int getHits()
    {
        return hits;
    }

    public void setMarkerForTest(double pos)
    {
        this.markerPos = pos;
    }

    private void redraw()
    {
        int cardW = 520;
        int cardH = 420;
        int cardX = (Theme.WORLD_WIDTH - cardW) / 2;
        int cardY = (Theme.WORLD_HEIGHT - cardH) / 2;
        drawCardFrame(cardX, cardY, cardW, cardH, Theme.BORDER_WOOD);

        Theme.drawCenteredText(canvas, "NGOPI HITAM & NGEROKOK", Theme.WORLD_WIDTH / 2, cardY + 36,
                Theme.FONT_SUBTITLE, Theme.ACCENT_WARNING);

        // Progress badge
        String prog = hits + "/" + TARGET_HITS + " Sruput";
        Theme.fillRoundRect(canvas, cardX + cardW - 115, cardY + 14, 98, 28, 6, Theme.BORDER_WOOD, null, 0);
        Theme.drawCenteredText(canvas, prog, cardX + cardW - 66, cardY + 33, Theme.FONT_HUD_VALUE, Theme.INK_LIGHT);

        // Portrait + pixel smoke puffs
        int px = Theme.WORLD_WIDTH / 2 - 120;
        int py = cardY + 52;
        canvas.drawImage(povCoffee, px, py);
        drawSmokePuffs(px + 205, py + 115);

        // Timing bar
        int barW = 360;
        int barH = 32;
        int barX = Theme.WORLD_WIDTH / 2 - barW / 2;
        int barY = cardY + 312;
        drawTimingBar(barX, barY, barW, barH, markerPos, ZONE_START, ZONE_END);

        if (miniGameState == MiniGameState.INTRO_POV)
        {
            Theme.drawCenteredText(canvas, "Siap-siap tekan [SPACE] pas di zona hijau!",
                    Theme.WORLD_WIDTH / 2, cardY + 372, Theme.FONT_DIALOGUE, Theme.INK_LIGHT);
        }
        else if (miniGameState == MiniGameState.RETRY_FLASH)
        {
            Theme.drawCenteredText(canvas, "Melenceng! Coba deui, Mang!",
                    Theme.WORLD_WIDTH / 2, cardY + 372, Theme.FONT_PROMPT, Theme.ACCENT_STRIKE);
        }
        else if (miniGameState == MiniGameState.COMPLETE)
        {
            Theme.drawCenteredText(canvas, "Mantap! Melek langsung, saatnya cek HP!",
                    Theme.WORLD_WIDTH / 2, cardY + 372, Theme.FONT_PROMPT, Theme.ACCENT_UANG);
        }
        else
        {
            GreenfootImage pill = Theme.createPromptPillImage("SPACE", "Sruput Pas Zona Hijau");
            canvas.drawImage(pill, Theme.WORLD_WIDTH / 2 - pill.getWidth() / 2, cardY + 360);
        }
    }

    private void drawSmokePuffs(int baseX, int baseY)
    {
        for (int i = 0; i < 3; i++)
        {
            int phase = (smokeTick + i * 20) % 60;
            int sx = baseX + (int) Math.round(Math.sin((smokeTick + i * 12) * 0.1) * 7);
            int sy = baseY - phase;
            int alpha = Math.max(20, 170 - phase * 3);
            int size = 8 + phase / 8;
            canvas.setColor(new Color(235, 235, 235, alpha));
            canvas.fillOval(sx - size / 2, sy - size / 2, size, size);
        }
    }
}
