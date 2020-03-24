package dev.grunert.tracker.broker;

public class Acceleration {
    Long x;
    Long y;
    Long z;

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

    public Acceleration(Long x, Long y, Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Acceleration [x=" + x + ", y=" + y + ", z=" + z + "]";
    }
}