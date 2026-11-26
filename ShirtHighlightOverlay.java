import greenfoot.GreenfootImage;

/**
 * FR-8b: Dramatic POV highlight showcase when Mang Ujang equips his iconic
 * black "BARAYA BAPAK AING / KANG DEDI MULYADI" fishing t-shirt.
 */
public class ShirtHighlightOverlay extends MiniGameOverlay
{
    private final GreenfootImage highlightImg;
    private final GreenfootImage closeupImg;

    public ShirtHighlightOverlay()
    {
        highlightImg = Theme.loadNearest("images/bapak_pov_shirt_highlight.png", 440, 330);
        closeupImg = Theme.loadNearest("images/shirt_design_closeup.png", 140, 140);
        reset();
    }

    @Override
    public void reset()
    {
        miniGameState = MiniGameState.ACTIVE;
        stateTimer = 0;
        SoundFX.play(SoundFX.SHIRT);
        redraw();
    }

    @Override
    public void act()
    {
        stateTimer++;
        if (stateTimer > 15 && keys.justPressedAny("space", "e"))
        {
            equipAndClose();
        }
    }

    public void equipAndClose()
    {
        GameState state = GameState.getInstance();
        state.setWearingFishingShirt(true);
        state.completeAndAdvance(GameStep.STEP_5_CHANGE_SHIRT, GameStep.STEP_6_BIKE_RIDE);
        transitionState(MiniGameState.COMPLETE);
        closeOverlay();
    }

    private void redraw()
    {
        int cardW = 640;
        int cardH = 470;
        int cardX = (Theme.WORLD_WIDTH - cardW) / 2;
        int cardY = (Theme.WORLD_HEIGHT - cardH) / 2;
        drawCardFrame(cardX, cardY, cardW, cardH, Theme.ACCENT_WARNING);

        Theme.drawShadowedText(canvas, "AURA BAPAK-BAPAK +999!", Theme.WORLD_WIDTH / 2, cardY + 38,
                Theme.FONT_SUBTITLE, Theme.ACCENT_WARNING, Theme.INK_PRIMARY);

        // Main dramatic highlight scene on left, zoomed shirt back graphic on right
        int mainX = cardX + 22;
        int mainY = cardY + 54;
        Theme.fillRoundRect(canvas, mainX - 3, mainY - 3, 446, 336, 8, Theme.ACCENT_WARNING, Theme.INK_PRIMARY, 2);
        canvas.drawImage(highlightImg, mainX, mainY);

        int sideX = cardX + 480;
        int sideY = cardY + 86;
        Theme.fillRoundRect(canvas, sideX - 4, sideY - 4, 148, 148, 8, Theme.INK_PRIMARY, Theme.ACCENT_WARNING, 2);
        canvas.drawImage(closeupImg, sideX, sideY);

        Theme.drawCenteredText(canvas, "BARAYA BAPAK AING", sideX + 70, sideY + 170,
                Theme.FONT_HUD_LABEL, Theme.INK_LIGHT);
        Theme.drawCenteredText(canvas, "KANG DEDI MULYADI", sideX + 70, sideY + 190,
                Theme.FONT_HUD_LABEL, Theme.ACCENT_WARNING);
        Theme.drawCenteredText(canvas, "Edisi Mancing", sideX + 70, sideY + 214,
                Theme.FONT_SMALL, Theme.SURFACE_RUMAH);

        Theme.drawCenteredText(canvas, "Kaos andalan terpasang! Motor klasik di depan pintu siap digas.",
                Theme.WORLD_WIDTH / 2, cardY + 412, Theme.FONT_DIALOGUE, Theme.INK_LIGHT);

        GreenfootImage pill = Theme.createPromptPillImage("SPACE / E", "Lanjut Naik Motor");
        canvas.drawImage(pill, Theme.WORLD_WIDTH / 2 - pill.getWidth() / 2, cardY + cardH - 42);
    }
}
