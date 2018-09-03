package map;

public class ExapHashCode {
    private int a;
    private boolean b;
    private char c;
    private long d;
    private float f;
    private double j;
    private User u;

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = a;
        result = 31 * result + (b ? 1 : 0);
        result = 31 * result + (int) c;
        result = 31 * result + (int) (d ^ (d >>> 32));
        result = 31 * result + (f != +0.0f ? Float.floatToIntBits(f) : 0);
        temp = Double.doubleToLongBits(j);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (u != null ? u.hashCode() : 0);
        return result;
    }
}
