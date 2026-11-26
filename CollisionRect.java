/**
 * Axis-aligned bounding box used for wall, water, and furniture collision checks.
 */
public class CollisionRect
{
    public final int x;
    public final int y;
    public final int width;
    public final int height;

    public CollisionRect(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(int rx, int ry, int rw, int rh)
    {
        return rx < x + width && rx + rw > x && ry < y + height && ry + rh > y;
    }
}
