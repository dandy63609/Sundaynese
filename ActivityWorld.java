/**
 * Abstract base world for standalone mini-game scenes implementing the 4-state MiniGame lifecycle.
 */
public abstract class ActivityWorld extends BaseWorld implements MiniGame
{
    protected MiniGameState miniGameState = MiniGameState.INTRO_POV;
    protected int stateTimer = 0;

    protected ActivityWorld(boolean showHud)
    {
        super(showHud);
    }

    @Override
    public void act()
    {
        stateTimer++;
        switch (miniGameState)
        {
            case INTRO_POV:
                actIntroPov();
                break;
            case ACTIVE:
                actActive();
                break;
            case RETRY_FLASH:
                actRetryFlash();
                break;
            case COMPLETE:
                actComplete();
                break;
        }
    }

    @Override
    public void start()
    {
        miniGameState = MiniGameState.ACTIVE;
        stateTimer = 0;
    }

    @Override
    public boolean isCompleted()
    {
        return miniGameState == MiniGameState.COMPLETE;
    }

    @Override
    public MiniGameState getMiniGameState()
    {
        return miniGameState;
    }

    protected void transitionState(MiniGameState next)
    {
        miniGameState = next;
        stateTimer = 0;
    }

    protected abstract void actIntroPov();
    protected abstract void actActive();
    protected abstract void actRetryFlash();
    protected abstract void actComplete();
}
