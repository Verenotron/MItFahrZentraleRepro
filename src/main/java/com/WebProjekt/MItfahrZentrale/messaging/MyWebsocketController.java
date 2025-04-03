package com.WebProjekt.MItfahrZentrale.messaging;



import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.WebProjekt.MItfahrZentrale.entities.tour.Tour;
import com.WebProjekt.MItfahrZentrale.services.tour.TourDTD;


@Controller
public class MyWebsocketController {
  @MessageMapping("/topic/tour")
  @SendTo("/topic/tour")
  public TourDTD msgRequestHandler(Tour t) {
    return TourDTD.fromTour(t);
  }
}