package com.WebProjekt.MItfahrZentrale.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class FrontendNachrichtServiceImpl implements FrontendNachrichtService {
  @Autowired
  private SimpMessagingTemplate messaging;

  @Override
  public void sendEvent(FrontendNachrichtEvent ev) {
    messaging.convertAndSend("/topic/tour", ev); //sendet nachricht über WebsocketBroker an Clients, die /topic/tour abonniert haben.
  }
}
  