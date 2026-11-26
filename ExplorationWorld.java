import greenfoot.World;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared top-down exploration world supporting AABB collision, hotspot arrows,
 * proximity prompts, and in-world POV mini-game overlays.
 */
public abstract class ExplorationWorld extends BaseWorld
{
    protected final List<CollisionRect> colliders = new ArrayList<>();
    protected final List<InteractableObject> interactables = new ArrayList<>();
    protected final KeyEdgeTracker keys = new KeyEdgeTracker("e", "space");
    protected BapakActor player;
    protected HotspotHighlight hotspotArrow;

    protected ExplorationWorld()
    {
        super(true);
        hotspotArrow = new HotspotHighlight(0, 0);
    }

    public List<CollisionRect> getColliders()
    {
        return colliders;
    }

    public boolean hasActiveOverlay()
    {
        return !getObjects(MiniGameOverlay.class).isEmpty();
    }

    protected void registerInteractable(InteractableObject obj, int x, int y)
    {
        addObject(obj, x, y);
        interactables.add(obj);
        if (obj.getSolidBox() != null)
        {
            colliders.add(obj.getSolidBox());
        }
    }

    @Override
    public void act()
    {
        if (hasActiveOverlay())
        {
            hidePrompt();
            if (hotspotArrow.getWorld() != null)
            {
                removeObject(hotspotArrow);
            }
            keys.syncHeldKeys("e", "space");
            return;
        }

        updateHotspotArrow();

        InteractableObject nearestActive = findNearestInteractable(true);
        InteractableObject nearestAny = nearestActive != null ? nearestActive : findNearestInteractable(false);

        if (nearestActive != null)
        {
            showPrompt("E / SPACE", nearestActive.getActionVerb());
        }
        else if (nearestAny != null)
        {
            showPrompt("E", "Cek " + nearestAny.getActionVerb());
        }
        else
        {
            showPrompt("WASD / Panah", "Jalan ke tanda panah kuning");
        }

        if (keys.justPressedAny("e", "space") && nearestAny != null)
        {
            if (nearestAny.isActiveFor(GameState.getInstance().getCurrentStep()))
            {
                onInteractActive(nearestAny);
            }
            else
            {
                showOutOfOrderQuip(nearestAny);
            }
        }
    }

    private void updateHotspotArrow()
    {
        GameStep step = GameState.getInstance().getCurrentStep();
        InteractableObject target = null;
        for (InteractableObject obj : interactables)
        {
            if (obj.isActiveFor(step))
            {
                target = obj;
                break;
            }
        }
        if (target != null)
        {
            int ax = target.getX();
            int ay = Math.max(Theme.HUD_HEIGHT + 18, target.getY() - target.getImage().getHeight() / 2 - 18);
            hotspotArrow.setAnchor(ax, ay);
            if (hotspotArrow.getWorld() == null)
            {
                addObject(hotspotArrow, ax, ay);
            }
        }
        else if (hotspotArrow.getWorld() != null)
        {
            removeObject(hotspotArrow);
        }
    }

    private InteractableObject findNearestInteractable(boolean activeOnly)
    {
        if (player == null)
        {
            return null;
        }
        GameStep step = GameState.getInstance().getCurrentStep();
        InteractableObject best = null;
        int bestDist = Integer.MAX_VALUE;
        for (InteractableObject obj : interactables)
        {
            if (activeOnly && !obj.isActiveFor(step))
            {
                continue;
            }
            if (obj.isNear(player))
            {
                int d = obj.distanceSq(player);
                if (d < bestDist)
                {
                    bestDist = d;
                    best = obj;
                }
            }
        }
        return best;
    }

    protected void showPlayerQuip(String quip)
    {
        removeObjects(getObjects(SpeechBubble.class));
        addObject(new SpeechBubble("Mang Ujang", quip, 120, player, -48), player.getX(), player.getY() - 48);
    }

    protected abstract void onInteractActive(InteractableObject target);
    protected abstract void showOutOfOrderQuip(InteractableObject target);
}
