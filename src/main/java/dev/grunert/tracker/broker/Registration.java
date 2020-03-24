package dev.grunert.tracker.broker;

public class Registration {
    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Registration(String clientName) {
        this.clientName = clientName;
    }
}