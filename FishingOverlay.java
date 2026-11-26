import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 * FR-10: Bobber Timing fishing mini-game at EmpangWorld (cast -> wait -> STRIKE! -> catch 3 fish).
 */
public class FishingOverlay extends MiniGameOverlay
{
    public static final int TARGET_FISH = 3;
    public static final int STRIKE_WINDOW_TICKS = 85; // ~1.4s (>= 1.2s spec)

    public enum Phase
    {
        READY_TO_CAST,
        WAITING_BITE,
        STRIKE_NOW,
        CAUGHT_FLASH
    }

    private final GreenfootImage bobberImg;
    private final GreenfootImage fishImg;
    private final GreenfootImage coffeePov;

    private int fishCaught = 0;
    private Phase phase = Phase.READY_TO_CAST;
    private int phaseTimer = 0;
    private int waitTargetTicks = 90;
    private String statusText = "Tekan [SPACE] untuk lempar kail!";

    public FishingOverlay()
    {
        bobberImg = Theme.loadNearest("images/bobber.png", 36, 36);
        fishImg = Theme.loadNearest("images/fish.png", 72, 48);
        coffeePov = Theme.loadNearest("images/bapak_pov_coffee.png", 120, 120);
        reset();
    }

    @Override
    public void reset()
    {
        fishCaught = 0;
        phase = Phase.READY_TO_CAST;
        phaseTimer = 0;
        miniGameState = MiniGameState.ACTIVE;
        stateTimer = 0;
        statusText = "Tekan [SPACE] untuk lempar kail!";
        redraw();
    }

    @Override
    public void act()
    {
        stateTimer++;
        phaseTimer++;

        if (miniGameState == MiniGameState.RETRY_FLASH)
        {
            if (stateTimer >= 50)
            {
                phase = Phase.READY_TO_CAST;
                phaseTimer = 0;
                statusText = "Tekan [SPACE] untuk lempar kail!";
                transitionState(MiniGameState.ACTIVE);
            }
            redraw();
            return;
        }

        if (miniGameState == MiniGameState.COMPLETE)
        {
            if (stateTimer >= 50 || keys.justPressedAny("space", "e"))
            {
                completeFishing();
            }
            redraw();
            return;
        }

        switch (phase)
        {
            case READY_TO_CAST:
                if (keys.justPressed("space"))
                {
                    castLine();
                }
                break;

            case WAITING_BITE:
                if (keys.justPressed("space"))
                {
                    triggerMiss("Belum nyangkut, sabar atuh!");
                }
                else if (phaseTimer >= waitTargetTicks)
                {
                    triggerStrike();
                }
                break;

            case STRIKE_NOW:
                if (keys.justPressed("space"))
                {
                    catchFish();
                }
                else if (phaseTimer >= STRIKE_WINDOW_TICKS)
                {
                    triggerMiss("Ikannya kabur! Lempar lagi, Mang!");
                }
                break;

            case CAUGHT_FLASH:
                if (phaseTimer >= 48 || keys.justPressed("space"))
                {
                    if (fishCaught >= TARGET_FISH)
                    {
                        transitionState(MiniGameState.COMPLETE);
                    }
                    else
                    {
                        phase = Phase.READY_TO_CAST;
                        phaseTimer = 0;
                        statusText = "Mantap! Lempar lagi buat ikan berikutnya.";
                    }
                }
                break;
        }

        redraw();
    }

    public void castLine()
    {
        phase = Phase.WAITING_BITE;
        phaseTimer = 0;
        waitTargetTicks = 85 + Greenfoot.getRandomNumber(75);
        statusText = "Menunggu umpan disambar nila...";
        SoundFX.play(SoundFX.SPLASH);
    }

    public void triggerStrike()
    {
        phase = Phase.STRIKE_NOW;
        phaseTimer = 0;
        statusText = "STRIKE!! TEKAN [SPACE] SEKARANG!";
        SoundFX.play(SoundFX.SPLASH);
    }

    public void catchFish()
    {
        fishCaught++;
        phase = Phase.CAUGHT_FLASH;
        phaseTimer = 0;
        statusText = "Dapat Ikan Nila (" + fishCaught + "/" + TARGET_FISH + ")!";
        SoundFX.play(SoundFX.HIT);
    }

    private void triggerMiss(String msg)
    {
        statusText = msg;
        SoundFX.play(SoundFX.MISS);
        transitionState(MiniGameState.RETRY_FLASH);
    }

    public void completeFishing()
    {
        GameState state = GameState.getInstance();
        state.setFishingDoneAtEmpang(true);
        state.completeAndAdvance(GameStep.STEP_7_FISHING, GameStep.STEP_8_SLEEP);
        transitionState(MiniGameState.COMPLETE);
        closeOverlay();
    }

    public int getFishCaught()
    {
        return fishCaught;
    }

    public Phase getPhase()
    {
        return phase;
    }

    private void redraw()
    {
        int cardW = 540;
        int cardH = 420;
        int cardX = (Theme.WORLD_WIDTH - cardW) / 2;
        int cardY = (Theme.WORLD_HEIGHT - cardH) / 2;
        drawCardFrame(cardX, cardY, cardW, cardH, Theme.SURFACE_EMPANG);

        Theme.drawCenteredText(canvas, "MANCING DI EMPANG MANG JAJA", Theme.WORLD_WIDTH / 2, cardY + 36,
                Theme.FONT_SUBTITLE, Theme.ACCENT_WARNING);

        // Fish counter badge
        Theme.fillRoundRect(canvas, cardX + cardW - 115, cardY + 14, 98, 28, 6, Theme.ACCENT_SARUNG, null, 0);
        Theme.drawCenteredText(canvas, fishCaught + "/" + TARGET_FISH + " Ikan",
                cardX + cardW - 66, cardY + 33, Theme.FONT_HUD_VALUE, Theme.INK_LIGHT);

        // Pond water viewport
        int waterX = cardX + 30;
        int waterY = cardY + 56;
        int waterW = cardW - 60;
        int waterH = 230;
        Theme.fillRoundRect(canvas, waterX, waterY, waterW, waterH, 10,
                new Color(34, 96, 68), Theme.BORDER_WOOD, 3);

        // Small Mang Ujang portrait on dock corner puffing cigarette
        canvas.drawImage(coffeePov, waterX + 10, waterY + waterH - 124);

        // Bobber / Fish visual
        int bx = waterX + waterW / 2 + 40;
        int by = waterY + waterH / 2;
        if (phase == Phase.WAITING_BITE)
        {
            int bob = (int) Math.round(Math.sin(stateTimer * 0.15) * 4);
            int rippleR = 24 + (stateTimer % 30);
            canvas.setColor(new Color(200, 245, 220, 120));
            canvas.drawOval(bx - rippleR / 2, by + 10 - rippleR / 4, rippleR, rippleR / 2);
            canvas.drawImage(bobberImg, bx - 18, by - 18 + bob);
        }
        else if (phase == Phase.STRIKE_NOW)
        {
            int shake = (stateTimer % 4 < 2) ? -4 : 4;
            canvas.setColor(Theme.ACCENT_STRIKE);
            canvas.drawOval(bx - 28, by - 10, 56, 28);
            canvas.drawImage(bobberImg, bx - 18 + shake, by - 10);
            Theme.drawShadowedText(canvas, "STRIKE!", bx, waterY + 58,
                    Theme.FONT_TITLE, Theme.ACCENT_STRIKE, Theme.INK_PRIMARY);
        }
        else if (phase == Phase.CAUGHT_FLASH || miniGameState == MiniGameState.COMPLETE)
        {
            canvas.drawImage(fishImg, bx - 36, by - 24);
            Theme.drawCenteredText(canvas, "+1 Nila Segar!", bx, by + 44, Theme.FONT_PROMPT, Theme.ACCENT_WARNING);
        }
        else
        {
            Theme.drawCenteredText(canvas, "Air tenang...", bx, by, Theme.FONT_DIALOGUE, Theme.INK_LIGHT);
        }

        // Status & bottom prompt
        Color statusColor = (miniGameState == MiniGameState.RETRY_FLASH || phase == Phase.STRIKE_NOW)
                ? Theme.ACCENT_STRIKE
                : (miniGameState == MiniGameState.COMPLETE ? Theme.ACCENT_UANG : Theme.INK_LIGHT);
        Theme.drawCenteredText(canvas, statusText, Theme.WORLD_WIDTH / 2, cardY + 322,
                Theme.FONT_PROMPT, statusColor);

        if (miniGameState == MiniGameState.COMPLETE)
        {
            Theme.drawCenteredText(canvas, "3 Ikan dapet! Jalan ke motor di kiri-bawah buat pulang.",
                    Theme.WORLD_WIDTH / 2, cardY + 350, Theme.FONT_DIALOGUE, Theme.SURFACE_RUMAH);
            GreenfootImage pill = Theme.createPromptPillImage("SPACE / E", "Siap Pulang");
            canvas.drawImage(pill, Theme.WORLD_WIDTH / 2 - pill.getWidth() / 2, cardY + cardH - 46);
        }
        else if (phase == Phase.READY_TO_CAST)
        {
            GreenfootImage pill = Theme.createPromptPillImage("SPACE", "Lempar Kail");
            canvas.drawImage(pill, Theme.WORLD_WIDTH / 2 - pill.getWidth() / 2, cardY + cardH - 46);
        }
        else if (phase == Phase.STRIKE_NOW)
        {
            GreenfootImage pill = Theme.createPromptPillImage("SPACE", "TARIK SEKARANG!");
            canvas.drawImage(pill, Theme.WORLD_WIDTH / 2 - pill.getWidth() / 2, cardY + cardH - 46);
        }
    }
}
