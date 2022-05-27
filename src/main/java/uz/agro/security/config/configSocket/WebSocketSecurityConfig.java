package uz.agro.security.config.configSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpDestMatchers("/chat/**").permitAll()
                .simpMessageDestMatchers("/chat/**").permitAll()
                .simpDestMatchers("/app/**").permitAll()
                .simpDestMatchers("/topic/**").permitAll()
                .simpSubscribeDestMatchers("/topic/**").permitAll()
                .anyMessage().permitAll()
                .simpTypeMatchers(SimpMessageType.OTHER, SimpMessageType.HEARTBEAT, SimpMessageType.CONNECT,SimpMessageType.UNSUBSCRIBE, SimpMessageType.SUBSCRIBE, SimpMessageType.DISCONNECT).permitAll();
    }
}
