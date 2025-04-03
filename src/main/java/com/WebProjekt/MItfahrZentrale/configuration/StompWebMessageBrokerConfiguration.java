package com.WebProjekt.MItfahrZentrale.configuration;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //WebSocket-basierte Nachrichtenvermittlung in einer Spring-Anwendung zu aktivieren. 
public class StompWebMessageBrokerConfiguration implements WebSocketMessageBrokerConfigurer{

    Logger logger = LoggerFactory.getLogger(StompWebMessageBrokerConfiguration.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        logger.info("Registry: ", registry);
        registry.enableSimpleBroker("/topic");
        //registry.setApplicationDestinationPrefixes("/api");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/stompbroker")
        .setAllowedOrigins("*");
    }
    
}
