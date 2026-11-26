import greenfoot.Actor;
import greenfoot.GreenfootImage;

/**
 * Persistent top HUD bar displaying current mission step and Rupiah cash balance.
 */
public class HudOverlay extends Actor
{
    private GameStep lastStep;
    private int lastMoney = -1;
    private int lastDeltaTicks = -1;
    private final GreenfootImage canvas;

    public HudOverlay()
    {
        canvas = new GreenfootImage(Theme.WORLD_WIDTH, Theme.HUD_HEIGHT);
        setImage(canvas);
        redraw();
    }

    @Override
    public void act()
    {
        GameState state = GameState.getInstance();
        state.tickDeltaBanner();
        if (state.getCurrentStep() != lastStep
                || state.getMoney() != lastMoney
                || state.getDeltaTicksRemaining() != lastDeltaTicks)
        {
            redraw();
        }
    }

    public void redraw()
    {
        GameState state = GameState.getInstance();
        lastStep = state.getCurrentStep();
        lastMoney = state.getMoney();
        lastDeltaTicks = state.getDeltaTicksRemaining();

        canvas.clear();
        canvas.setColor(Theme.SURFACE_HUD);
        canvas.fillRect(0, 0, Theme.WORLD_WIDTH, Theme.HUD_HEIGHT - 3);
        canvas.setColor(Theme.BORDER_WOOD);
        canvas.fillRect(0, Theme.HUD_HEIGHT - 3, Theme.WORLD_WIDTH, 3);

        // Left: Step badge + Mission label
        int stepNum = Math.min(8, Math.max(1, lastStep.ordinal()));
        String badgeText = lastStep == GameStep.STEP_0_WAKEUP ? "PAGI"
                : (lastStep == GameStep.COMPLETED ? "8/8" : stepNum + "/8");
        Theme.fillRoundRect(canvas, 12, 11, 46, 26, 6, Theme.BORDER_WOOD, Theme.ACCENT_WARNING, 1);
        Theme.drawCenteredText(canvas, badgeText, 35, 29, Theme.FONT_HUD_LABEL, Theme.ACCENT_WARNING);

        canvas.setFont(Theme.FONT_HUD_LABEL);
        canvas.setColor(Theme.INK_LIGHT);
        canvas.drawString(lastStep.getMissionLabel(), 68, 29);

        // Right: Uang balance + optional delta popup
        String uangLabel = "Uang: " + GameState.formatRupiah(lastMoney);
        int uangWidth = Theme.measureTextWidth(canvas, Theme.FONT_HUD_VALUE, uangLabel);
        int uangX = Theme.WORLD_WIDTH - 14 - uangWidth;

        if (lastDeltaTicks > 0 && state.getLastMoneyDelta() != 0)
        {
            int delta = state.getLastMoneyDelta();
            String deltaStr = (delta > 0 ? "+" : "") + GameState.formatRupiah(delta);
            int deltaW = Theme.measureTextWidth(canvas, Theme.FONT_HUD_LABEL, deltaStr);
            greenfoot.Color deltaColor = delta > 0 ? Theme.ACCENT_UANG : Theme.ACCENT_STRIKE;
            Theme.fillRoundRect(canvas, uangX - deltaW - 22, 12, deltaW + 12, 24, 6, deltaColor, null, 0);
            canvas.setFont(Theme.FONT_HUD_LABEL);
            canvas.setColor(Theme.INK_LIGHT);
            canvas.drawString(deltaStr, uangX - deltaW - 16, 28);
        }

        canvas.setFont(Theme.FONT_HUD_VALUE);
        canvas.setColor(Theme.ACCENT_UANG);
        canvas.drawString(uangLabel, uangX, 30);
    }
}
