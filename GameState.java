import java.util.EnumSet;
import java.util.Set;

/**
 * Shared singleton state persisted across Greenfoot World transitions.
 */
public class GameState
{
    public static final int INITIAL_MONEY = 20000;
    public static final int WIFE_TRANSFER_AMOUNT = 500000;
    public static final int JUDOL_BET_AMOUNT = 100000;

    private static final GameState INSTANCE = new GameState();

    private GameStep currentStep;
    private int money;
    private int lastMoneyDelta;
    private int deltaTicksRemaining;
    private boolean wearingFishingShirt;
    private boolean wakeupIntroShown;
    private boolean empangIntroShown;
    private boolean fishingDoneAtEmpang;
    private final Set<GameStep> completedActivities;

    private GameState()
    {
        this.completedActivities = EnumSet.noneOf(GameStep.class);
        reset();
    }

    public static GameState getInstance()
    {
        return INSTANCE;
    }

    /**
     * Resets all progression, cash, and outfit state for a fresh 1-day run.
     */
    public void reset()
    {
        currentStep = GameStep.STEP_0_WAKEUP;
        money = INITIAL_MONEY;
        lastMoneyDelta = 0;
        deltaTicksRemaining = 0;
        wearingFishingShirt = false;
        wakeupIntroShown = false;
        empangIntroShown = false;
        fishingDoneAtEmpang = false;
        completedActivities.clear();
    }

    public GameStep getCurrentStep()
    {
        return currentStep;
    }

    public void setCurrentStep(GameStep step)
    {
        this.currentStep = step;
    }

    /**
     * Marks a step complete and advances to the specified next step.
     */
    public void completeAndAdvance(GameStep completedStep, GameStep nextStep)
    {
        if (completedStep != null)
        {
            completedActivities.add(completedStep);
        }
        this.currentStep = nextStep;
    }

    public boolean isActivityCompleted(GameStep step)
    {
        return completedActivities.contains(step);
    }

    public int getCompletedCount()
    {
        return completedActivities.size();
    }

    public int getMoney()
    {
        return money;
    }

    public void addMoney(int amount)
    {
        if (amount <= 0)
        {
            return;
        }
        money += amount;
        lastMoneyDelta = amount;
        deltaTicksRemaining = 95;
    }

    public void spendMoney(int amount)
    {
        if (amount <= 0)
        {
            return;
        }
        int actual = Math.min(money, amount);
        money -= actual;
        lastMoneyDelta = -actual;
        deltaTicksRemaining = 95;
    }

    public int getLastMoneyDelta()
    {
        return lastMoneyDelta;
    }

    public int getDeltaTicksRemaining()
    {
        return deltaTicksRemaining;
    }

    public void tickDeltaBanner()
    {
        if (deltaTicksRemaining > 0)
        {
            deltaTicksRemaining--;
        }
    }

    public boolean isWearingFishingShirt()
    {
        return wearingFishingShirt;
    }

    public void setWearingFishingShirt(boolean wearingFishingShirt)
    {
        this.wearingFishingShirt = wearingFishingShirt;
    }

    public boolean isWakeupIntroShown()
    {
        return wakeupIntroShown;
    }

    public void setWakeupIntroShown(boolean wakeupIntroShown)
    {
        this.wakeupIntroShown = wakeupIntroShown;
    }

    public boolean isEmpangIntroShown()
    {
        return empangIntroShown;
    }

    public void setEmpangIntroShown(boolean empangIntroShown)
    {
        this.empangIntroShown = empangIntroShown;
    }

    public boolean isFishingDoneAtEmpang()
    {
        return fishingDoneAtEmpang;
    }

    public void setFishingDoneAtEmpang(boolean fishingDoneAtEmpang)
    {
        this.fishingDoneAtEmpang = fishingDoneAtEmpang;
    }

    /**
     * Formats an integer Rupiah amount with dot thousands separators, e.g. "Rp 520.000".
     */
    public static String formatRupiah(int amount)
    {
        boolean negative = amount < 0;
        String digits = Integer.toString(Math.abs(amount));
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = digits.length() - 1; i >= 0; i--)
        {
            sb.append(digits.charAt(i));
            count++;
            if (count % 3 == 0 && i > 0)
            {
                sb.append('.');
            }
        }
        sb.reverse();
        return (negative ? "-Rp " : "Rp ") + sb.toString();
    }
}
