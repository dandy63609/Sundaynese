/**
 * Polymorphic contract for every mini-game and POV activity in Sundaynese.
 */
public interface MiniGame
{
    void start();
    boolean isCompleted();
    void reset();
    MiniGameState getMiniGameState();
}
