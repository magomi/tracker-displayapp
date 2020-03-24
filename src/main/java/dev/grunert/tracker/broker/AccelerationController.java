package dev.grunert.tracker.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class AccelerationController {

  @SendTo("/position/acceleration")
  @MessageMapping("/registration")
  public Registration greeting(Registration registration) throws Exception {
    System.out.println(registration);
    return registration;
  }

  @Autowired
  private SimpMessagingTemplate template;

  public void publishAcceleration(Acceleration acceleration) {
    System.out.print(".");
    try {
      this.template.convertAndSend("/position/acceleration", acceleration);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}