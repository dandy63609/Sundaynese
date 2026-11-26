import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 * FR-1, FR-4, FR-5, FR-8b, FR-11: Top-down interior of Mang Ujang's Sundanese house (Rumah).
 */
public class HouseWorld extends ExplorationWorld
{
    public enum SpawnPoint
    {
        BESIDE_BED,
        BESIDE_PHONE,
        AT_DOOR
    }

    private final InteractableObject bedProp;
    private final InteractableObject wardrobeProp;
    private final InteractableObject coffeeTableProp;
    private final InteractableObject phoneProp;
    private final InteractableObject bikeProp;

    public HouseWorld()
    {
        this(SpawnPoint.BESIDE_BED);
    }

    public HouseWorld(SpawnPoint spawn)
    {
        super();
        SoundFX.startBgm();
        applyBackground();
        setupWalls();

        bedProp = new InteractableObject("bed", "images/bed.png", 80, 56,
                "Tidur (Selesai Hari)", 78, new CollisionRect(75, 214, 80, 52), true,
                GameStep.STEP_8_SLEEP);
        registerInteractable(bedProp, 115, 240);

        wardrobeProp = new InteractableObject("wardrobe", "images/wardrobe_shirt.png", 56, 48,
                "Pakai Kaos Mancing", 72, new CollisionRect(352, 208, 56, 48), true,
                GameStep.STEP_5_CHANGE_SHIRT);
        registerInteractable(wardrobeProp, 380, 232);

        coffeeTableProp = new InteractableObject("coffee", "images/table_coffee.png", 64, 48,
                "Seduh Kopi & Udud", 72, new CollisionRect(603, 226, 64, 48), true,
                GameStep.STEP_1_COFFEE);
        registerInteractable(coffeeTableProp, 635, 250);

        phoneProp = new InteractableObject("phone", "images/phone_prop.png", 36, 36,
                "Buka HP", 72, new CollisionRect(668, 358, 36, 36), true,
                GameStep.STEP_2_CHAT_WIFE, GameStep.STEP_3_JUDOL, GameStep.STEP_4_FRIEND_INVITE);
        registerInteractable(phoneProp, 686, 376);

        bikeProp = new InteractableObject("bike", "images/classic_bike.png", 72, 48,
                "Naik Motor ke Empang", 74, new CollisionRect(366, 502, 68, 44), true,
                GameStep.STEP_6_BIKE_RIDE);
        registerInteractable(bikeProp, 400, 522);

        player = new BapakActor();
        switch (spawn)
        {
            case BESIDE_PHONE:
                addObject(player, 622, 376);
                break;
            case AT_DOOR:
                addObject(player, 400, 456);
                break;
            case BESIDE_BED:
            default:
                addObject(player, 200, 258);
                break;
        }

        GameState state = GameState.getInstance();
        if (state.getCurrentStep() == GameStep.STEP_0_WAKEUP && !state.isWakeupIntroShown())
        {
            addObject(new WakeupOverlay(), Theme.WORLD_WIDTH / 2, Theme.WORLD_HEIGHT / 2);
        }
    }

    private void applyBackground()
    {
        GreenfootImage bg = Theme.loadNearest("images/bg_rumah.png", Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT);
        if (GameState.getInstance().getCurrentStep() == GameStep.STEP_8_SLEEP)
        {
            bg.setColor(new Color(18, 22, 48, 80));
            bg.fillRect(0, 0, Theme.WORLD_WIDTH, Theme.WORLD_HEIGHT);
        }
        setBackground(bg);
    }

    private void setupWalls()
    {
        colliders.add(new CollisionRect(0, 0, Theme.WORLD_WIDTH, 204));    // Top wall
        colliders.add(new CollisionRect(0, 0, 52, Theme.WORLD_HEIGHT));     // Left wall
        colliders.add(new CollisionRect(748, 0, 52, Theme.WORLD_HEIGHT));   // Right wall
        colliders.add(new CollisionRect(446, 195, 42, 204));                // Center bamboo partition
        colliders.add(new CollisionRect(0, 495, 366, 105));                 // Bottom wall left
        colliders.add(new CollisionRect(434, 495, 366, 105));               // Bottom wall right
        colliders.add(new CollisionRect(366, 558, 68, 42));                 // Door threshold
    }

    @Override
    protected void onInteractActive(InteractableObject target)
    {
        String id = target.getId();
        if ("coffee".equals(id))
        {
            addObject(new CoffeeOverlay(), Theme.WORLD_WIDTH / 2, Theme.WORLD_HEIGHT / 2);
        }
        else if ("phone".equals(id))
        {
            Greenfoot.setWorld(new PhoneWorld());
        }
        else if ("wardrobe".equals(id))
        {
            addObject(new ShirtHighlightOverlay(), Theme.WORLD_WIDTH / 2, Theme.WORLD_HEIGHT / 2);
        }
        else if ("bike".equals(id))
        {
            SoundFX.play(SoundFX.BIKE);
            Greenfoot.setWorld(new RoadBikeWorld());
        }
        else if ("bed".equals(id))
        {
            GameState.getInstance().completeAndAdvance(GameStep.STEP_8_SLEEP, GameStep.COMPLETED);
            Greenfoot.setWorld(new WinWorld());
        }
    }

    @Override
    protected void showOutOfOrderQuip(InteractableObject target)
    {
        GameStep step = GameState.getInstance().getCurrentStep();
        if (step == GameStep.STEP_1_COFFEE)
        {
            showPlayerQuip("Ngopi dulu atuh, mata masih sepet.");
        }
        else if (step == GameStep.STEP_2_CHAT_WIFE || step == GameStep.STEP_3_JUDOL
                || step == GameStep.STEP_4_FRIEND_INVITE)
        {
            showPlayerQuip("Cek HP dulu di meja sebelah kanan, lur.");
        }
        else if (step == GameStep.STEP_5_CHANGE_SHIRT)
        {
            showPlayerQuip("Ganti kaos mancing dulu di rak kamar biar aura keluar!");
        }
        else if (step == GameStep.STEP_6_BIKE_RIDE)
        {
            showPlayerQuip("Kaos udah keren! Langsung ke motor depan pintu.");
        }
        else if (step == GameStep.STEP_8_SLEEP)
        {
            showPlayerQuip("Capek seharian mancing, langsung ke kasur aja ah.");
        }
    }
}
