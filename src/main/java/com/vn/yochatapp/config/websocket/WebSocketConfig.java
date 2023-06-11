package com.vn.yochatapp.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws");
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://127.0.0.1:8088")
                .withSockJS(); // Đăng ký endpoint "/ws" và kích hoạt SockJS fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.addDecoratorFactory(corsWebSocketDecoratorFactory());
//    }
//
//    @Bean
//    public WebSocketHandlerDecoratorFactory corsWebSocketDecoratorFactory() {
//        return new WebSocketHandlerDecoratorFactory() {
//            @Override
//            public WebSocketHandler decorate(WebSocketHandler handler) {
//                return new WebSocketHandlerDecorator(handler) {
//                    @Override
//                    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//                        // Add your CORS headers here
//                        HttpHeaders headers = session.getHandshakeHeaders();
//                        headers.setAccessControlAllowOrigin("*");
//
//                        super.afterConnectionEstablished(session);
//                    }
//                };
//            }
//        };
//    }
}
