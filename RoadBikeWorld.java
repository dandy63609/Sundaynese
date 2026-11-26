import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 * FR-9: Classic motorcycle Obstacle Dodge mini-game traveling from Rumah to Empang.
 */
public class RoadBikeWorld extends ActivityWorld
{
    public static final int TARGET_DISTANCE = 840; // ~14 seconds at 1/tick

    private final GreenfootImage baseRoad;
    private final GreenfootImage frameBuffer;
    private final BikeRiderActor rider;
    private final KeyEdgeTracker keys = new KeyEdgeTracker("space", "e");

    private int scrollY = 0;
    private int distance = 0;
    private int spawnTimer = 35;

    public RoadBikeWorld()
    {
        super(true);
        baseRoad = Theme.loadNearest("images/bg_road.png", Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT);
        frameBuffer = new GreenfootImage(Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT);

        rider = new BikeRiderActor();
        addObject(rider, BikeRiderActor.LANE_X[1], 460);

        reset();
    }

    @Override
    public void reset()
    {
        distance = 0;
        scrollY = 0;
        spawnTimer = 40;
        removeObjects(getObjects(RoadObstacleActor.class));
        rider.resetLane();
        miniGameState = MiniGameState.INTRO_POV;
        stateTimer = 0;
        renderRoadFrame();
        showPrompt("LEFT / RIGHT  atau  A / D", "Hindari rintangan menuju Empang");
    }

    @Override
    protected void actIntroPov()
    {
        renderRoadFrame();
        if (stateTimer >= 65 || keys.justPressedAny("space", "e"))
        {
            start();
        }
    }

    @Override
    protected void actActive()
    {
        scrollY = (scrollY + 6) % Theme.WORLD_HEIGHT;
        distance++;

        spawnTimer--;
        if (spawnTimer <= 0 && distance < TARGET_DISTANCE - 90)
        {
            spawnObstacleWave();
            spawnTimer = 46 + Greenfoot.getRandomNumber(24);
        }

        for (RoadObstacleActor obs : getObjects(RoadObstacleActor.class))
        {
            if (obs.collidesWith(rider))
            {
                onCrash(obs.getCrashMessage());
                return;
            }
        }

        if (distance >= TARGET_DISTANCE)
        {
            SoundFX.play(SoundFX.BIKE);
            GameState.getInstance().completeAndAdvance(GameStep.STEP_6_BIKE_RIDE, GameStep.STEP_7_FISHING);
            transitionState(MiniGameState.COMPLETE);
            renderRoadFrame();
            return;
        }

        renderRoadFrame();
    }

    @Override
    protected void actRetryFlash()
    {
        renderRoadFrame();
        if (stateTimer >= 60)
        {
            distance = 0;
            spawnTimer = 40;
            removeObjects(getObjects(RoadObstacleActor.class));
            rider.resetLane();
            transitionState(MiniGameState.ACTIVE);
        }
    }

    @Override
    protected void actComplete()
    {
        if (stateTimer >= 45 || keys.justPressedAny("space", "e"))
        {
            Greenfoot.setWorld(new EmpangWorld());
        }
    }

    public void onCrash(String message)
    {
        SoundFX.play(SoundFX.MISS);
        showRetryFlash(message);
        transitionState(MiniGameState.RETRY_FLASH);
    }

    private void spawnObstacleWave()
    {
        RoadObstacleActor.Kind[] kinds = RoadObstacleActor.Kind.values();
        int lane1 = Greenfoot.getRandomNumber(3);
        RoadObstacleActor.Kind k1 = kinds[Greenfoot.getRandomNumber(kinds.length)];
        addObject(new RoadObstacleActor(k1, 6), BikeRiderActor.LANE_X[lane1], -30);

        // Occasionally spawn a second obstacle in another lane (always leaves at least 1 lane open)
        if (Greenfoot.getRandomNumber(100) < 40)
        {
            int lane2 = (lane1 + 1 + Greenfoot.getRandomNumber(2)) % 3;
            RoadObstacleActor.Kind k2 = kinds[Greenfoot.getRandomNumber(kinds.length)];
            addObject(new RoadObstacleActor(k2, 6), BikeRiderActor.LANE_X[lane2], -40);
        }
    }

    public int getDistancePercent()
    {
        return Math.min(100, (distance * 100) / TARGET_DISTANCE);
    }

    public void setDistanceForTest(int d)
    {
        this.distance = d;
    }

    private void renderRoadFrame()
    {
        frameBuffer.drawImage(baseRoad, 0, scrollY);
        frameBuffer.drawImage(baseRoad, 0, scrollY - Theme.WORLD_HEIGHT);

        // Distance bar below HUD
        int barW = 320;
        int barH = 22;
        int barX = (Theme.WORLD_WIDTH - barW) / 2;
        int barY = Theme.HUD_HEIGHT + 12;
        Theme.fillRoundRect(frameBuffer, barX - 4, barY - 4, barW + 8, barH + 8, 8,
                Theme.SURFACE_HUD, Theme.BORDER_WOOD, 2);
        int pct = getDistancePercent();
        int fillW = (barW * pct) / 100;
        if (fillW > 0)
        {
            frameBuffer.setColor(Theme.ACCENT_UANG);
            frameBuffer.fillRect(barX, barY, fillW, barH);
        }
        Theme.drawCenteredText(frameBuffer, "Jarak ke Empang: " + pct + "%",
                Theme.WORLD_WIDTH / 2, barY + 16, Theme.FONT_HUD_LABEL, Theme.INK_LIGHT);

        if (miniGameState == MiniGameState.INTRO_POV)
        {
            Theme.fillRoundRect(frameBuffer, 170, 210, 460, 110, 12,
                    new Color(31, 26, 23, 220), Theme.ACCENT_WARNING, 2);
            Theme.drawCenteredText(frameBuffer, "GAS KE EMPANG MANG JAJA!", Theme.WORLD_WIDTH / 2, 248,
                    Theme.FONT_SUBTITLE, Theme.ACCENT_WARNING);
            Theme.drawCenteredText(frameBuffer, "Pakai [LEFT / RIGHT] atau [A / D] hindari rintangan.",
                    Theme.WORLD_WIDTH / 2, 282, Theme.FONT_DIALOGUE, Theme.INK_LIGHT);
        }
        else if (miniGameState == MiniGameState.COMPLETE)
        {
            Theme.fillRoundRect(frameBuffer, 190, 220, 420, 100, 12,
                    new Color(31, 26, 23, 220), Theme.ACCENT_UANG, 2);
            Theme.drawCenteredText(frameBuffer, "SAMPAI DI EMPANG!", Theme.WORLD_WIDTH / 2, 262,
                    Theme.FONT_SUBTITLE, Theme.ACCENT_UANG);
            Theme.drawCenteredText(frameBuffer, "Tekan [SPACE] untuk turun dari motor...",
                    Theme.WORLD_WIDTH / 2, 294, Theme.FONT_DIALOGUE, Theme.INK_LIGHT);
        }

        setBackground(frameBuffer);
    }
}
