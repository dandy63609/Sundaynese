import greenfoot.GreenfootImage;

/**
 * Morning wake-up POV cut-in introducing Mang Ujang's 1-day loop.
 */
public class WakeupOverlay extends MiniGameOverlay
{
    private final GreenfootImage povPortrait;

    public WakeupOverlay()
    {
        povPortrait = Theme.loadNearest("images/bapak_pov_wakeup.png", 240, 240);
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
        if (keys.justPressedAny("space", "e") || stateTimer > 210)
        {
            completeWakeup();
        }
    }

    public void completeWakeup()
    {
        GameState state = GameState.getInstance();
        state.setWakeupIntroShown(true);
        state.completeAndAdvance(GameStep.STEP_0_WAKEUP, GameStep.STEP_1_COFFEE);
        transitionState(MiniGameState.COMPLETE);
        closeOverlay();
    }

    private void redraw()
    {
        int cardW = 500;
        int cardH = 390;
        int cardX = (Theme.WORLD_WIDTH - cardW) / 2;
        int cardY = (Theme.WORLD_HEIGHT - cardH) / 2;
        drawCardFrame(cardX, cardY, cardW, cardH, Theme.BORDER_WOOD);

        Theme.drawCenteredText(canvas, "MINGGU PAGI DI KAMPUNG", Theme.WORLD_WIDTH / 2, cardY + 38,
                Theme.FONT_SUBTITLE, Theme.ACCENT_WARNING);
        canvas.drawImage(povPortrait, Theme.WORLD_WIDTH / 2 - 120, cardY + 54);
        Theme.drawCenteredText(canvas, "\"Jam 9 pagi... istri di luar negeri, anak di pesantren.\"",
                Theme.WORLD_WIDTH / 2, cardY + 322, Theme.FONT_DIALOGUE, Theme.INK_LIGHT);
        Theme.drawCenteredText(canvas, "\"Saatnya ngopi hitam heula sebelum beraktivitas.\"",
                Theme.WORLD_WIDTH / 2, cardY + 344, Theme.FONT_DIALOGUE, Theme.SURFACE_RUMAH);

        GreenfootImage pill = Theme.createPromptPillImage("SPACE / E", "Bangun & Mulai Hari");
        canvas.drawImage(pill, Theme.WORLD_WIDTH / 2 - pill.getWidth() / 2, cardY + cardH - 36);
    }
}
