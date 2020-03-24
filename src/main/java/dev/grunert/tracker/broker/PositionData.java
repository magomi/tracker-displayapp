package dev.grunert.tracker.broker;

public class PositionData {
    private Xyz gyro;
    private Xyz acc;

    public Xyz getGyro() {
        return gyro;
    }

    public void setGyro(Xyz gyro) {
        this.gyro = gyro;
    }

    public Xyz getAcc() {
        return acc;
    }

    public void setAcc(Xyz acc) {
        this.acc = acc;
    }

    public PositionData(Xyz gyro, Xyz acc) {
        this.gyro = gyro;
        this.acc = acc;
    }

    public PositionData() {
    }

    @Override
    public String toString() {
        return "PositionData [acc=" + acc + ", gyro=" + gyro + "]";
    }

    
}