import greenfoot.GreenfootImage;

/**
 * Brief arrival POV welcome card when Mang Ujang reaches Empang Mang Jaja.
 */
public class EmpangIntroOverlay extends MiniGameOverlay
{
    private final GreenfootImage dad1;
    private final GreenfootImage dad2;

    public EmpangIntroOverlay()
    {
        dad1 = Theme.loadNearest("images/npc_dad_1.png", 72, 72);
        dad2 = Theme.loadNearest("images/npc_dad_2.png", 72, 72);
        dad2.mirrorHorizontally();
        reset();
    }

    @Override
    public void reset()
    {
        miniGameState = MiniGameState.ACTIVE;
        stateTimer = 0;
        redraw();
    }

    @Override
    public void act()
    {
        stateTimer++;
        if (stateTimer >= 165 || (stateTimer > 12 && keys.justPressedAny("space", "e")))
        {
            GameState.getInstance().setEmpangIntroShown(true);
            transitionState(MiniGameState.COMPLETE);
            closeOverlay();
        }
    }

    private void redraw()
    {
        int cardW = 500;
        int cardH = 310;
        int cardX = (Theme.WORLD_WIDTH - cardW) / 2;
        int cardY = (Theme.WORLD_HEIGHT - cardH) / 2;
        drawCardFrame(cardX, cardY, cardW, cardH, Theme.SURFACE_EMPANG);

        Theme.drawCenteredText(canvas, "EMPANG MANG JAJA", Theme.WORLD_WIDTH / 2, cardY + 42,
                Theme.FONT_SUBTITLE, Theme.ACCENT_WARNING);

        canvas.drawImage(dad1, Theme.WORLD_WIDTH / 2 - 96, cardY + 72);
        canvas.drawImage(dad2, Theme.WORLD_WIDTH / 2 + 24, cardY + 72);

        Theme.drawCenteredText(canvas, "Mang Asep: \"Tah gening datang si Mang Ujang!\"",
                Theme.WORLD_WIDTH / 2, cardY + 178, Theme.FONT_DIALOGUE, Theme.INK_LIGHT);
        Theme.drawCenteredText(canvas, "\"Kaosna gagah pisan euy! Hayu langsung merapat ke dermaga.\"",
                Theme.WORLD_WIDTH / 2, cardY + 202, Theme.FONT_DIALOGUE, Theme.SURFACE_RUMAH);

        GreenfootImage pill = Theme.createPromptPillImage("SPACE / E", "Jalan ke Dermaga");
        canvas.drawImage(pill, Theme.WORLD_WIDTH / 2 - pill.getWidth() / 2, cardY + cardH - 46);
    }
}
