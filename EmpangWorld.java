import greenfoot.Greenfoot;

/**
 * FR-1, FR-10: Top-down Empang Mang Jaja pond world with NPC Sundanese dad banter,
 * bamboo dock fishing spot, and parked classic bike for returning home.
 */
public class EmpangWorld extends ExplorationWorld
{
    public EmpangWorld()
    {
        super();
        setBackgroundScaled("images/bg_empang.png");
        setupColliders();

        // Ambient Sundanese dads on left and right wings of the bamboo dock
        addObject(new NpcDadActor("Mang Asep", "images/npc_dad_1.png", false, 45, 0), 328, 242);
        addObject(new NpcDadActor("Mang Jaja", "images/npc_dad_2.png", true, 155, 2), 498, 242);

        InteractableObject fishingSpot = new InteractableObject("fishing_spot", "images/fishing_spot.png",
                64, 48, "Mancing di Empang", 74, new CollisionRect(374, 212, 60, 26), false,
                GameStep.STEP_7_FISHING);
        registerInteractable(fishingSpot, 404, 232);

        InteractableObject returnBike = new InteractableObject("return_bike", "images/classic_bike.png",
                72, 48, "Naik Motor Pulang ke Rumah", 78, new CollisionRect(94, 466, 72, 46), true,
                GameStep.STEP_8_SLEEP);
        registerInteractable(returnBike, 130, 490);

        player = new BapakActor();
        addObject(player, 200, 476);

        if (!GameState.getInstance().isEmpangIntroShown())
        {
            addObject(new EmpangIntroOverlay(), Theme.WORLD_WIDTH / 2, Theme.WORLD_HEIGHT / 2);
        }
    }

    private void setupColliders()
    {
        // Pond water left and right of center bamboo dock walkway (x=362..444 open!)
        colliders.add(new CollisionRect(0, 0, 362, 294));
        colliders.add(new CollisionRect(444, 0, 356, 294));
        colliders.add(new CollisionRect(362, 0, 82, 224));

        // Left & right banana trees and bottom prompt strip boundary
        colliders.add(new CollisionRect(0, 0, 88, Theme.WORLD_HEIGHT));
        colliders.add(new CollisionRect(712, 0, 88, Theme.WORLD_HEIGHT));
        colliders.add(new CollisionRect(0, 536, Theme.WORLD_WIDTH, 64));
    }

    @Override
    protected void onInteractActive(InteractableObject target)
    {
        String id = target.getId();
        if ("fishing_spot".equals(id))
        {
            addObject(new FishingOverlay(), Theme.WORLD_WIDTH / 2, Theme.WORLD_HEIGHT / 2);
        }
        else if ("return_bike".equals(id))
        {
            SoundFX.play(SoundFX.BIKE);
            Greenfoot.setWorld(new HouseWorld(HouseWorld.SpawnPoint.AT_DOOR));
        }
    }

    @Override
    protected void showOutOfOrderQuip(InteractableObject target)
    {
        GameStep step = GameState.getInstance().getCurrentStep();
        if (step == GameStep.STEP_7_FISHING)
        {
            showPlayerQuip("Belum dapat ikan nih! Naik ke dermaga tengah dulu buat mancing.");
        }
        else if (step == GameStep.STEP_8_SLEEP)
        {
            showPlayerQuip("Ikan udah 3 ekor! Tinggal naik motor balik ke rumah.");
        }
    }
}
