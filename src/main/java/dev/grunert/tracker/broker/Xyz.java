package dev.grunert.tracker.broker;

public class Xyz {
    private Long x;
    private Long y;
    private Long z;

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    public Long getZ() {
        return z;
    }

    public void setZ(Long z) {
        this.z = z;
    }

    public Xyz(Long x, Long y, Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Xyz() {
    }

    @Override
    public String toString() {
        return "Xyz [x=" + x + ", y=" + y + ", z=" + z + "]";
    }
}