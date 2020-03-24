package dev.grunert.tracker.broker;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PositionService implements DisposableBean, Runnable {

    private boolean running = true;
    private Thread thread;
    private AccelerationController accelerationController;

    public PositionService(AccelerationController accelerationController) {
        System.out.println("PositionService initialized.");
        this.accelerationController = accelerationController;

        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {

        ObjectMapper mapper = new ObjectMapper();

        try {
            String mqttClientId = "tracker-broker-" + UUID.randomUUID().toString();
            IMqttClient client = new MqttClient("tcp://media:8883", mqttClientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            options.setUserName("yyyyyyyyyyyyyy");
            options.setPassword("xxxxxxxxxxxxxx".toCharArray());
            client.connect(options);

            CountDownLatch receivedSignal = new CountDownLatch(10);
            client.subscribe("tracker/tracker-dev-01/data/position", (topic, msg) -> {
                byte[] payload = msg.getPayload();
                PositionData positionData = mapper.readValue(new String(payload), PositionData.class);
                accelerationController.publishAcceleration(new Acceleration(positionData.getAcc().getX(),
                        positionData.getAcc().getY(), positionData.getAcc().getZ()));
                receivedSignal.countDown();
            });
            receivedSignal.await(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        running = false;
    }

    

}
