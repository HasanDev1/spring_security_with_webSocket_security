package uz.agro.security.config.configSocket;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.agro.security.entity.Users;
import uz.agro.security.repository.UserRepository;
import uz.agro.security.security.JwtUserDetailsService;
import uz.agro.security.security.jwt.JwtTokenProvider;
import uz.agro.security.security.jwt.SecurityUtils;

import java.security.Principal;

@Component
@Slf4j
public class CustomClientInboundChannelInterceptor implements ChannelInterceptor {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUserDetailsService jwtUserDetailsService;

    public CustomClientInboundChannelInterceptor( UserRepository userRepository, JwtTokenProvider jwtTokenProvider, JwtUserDetailsService jwtUserDetailsService) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand())){
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            log.debug("webSocket token is {}", jwtToken);
            jwtToken = jwtToken.substring(7);
            if (jwtToken != null){

                String username = jwtTokenProvider.getUsername(jwtToken);
                log.debug("current username {}", username);

                Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
                if(authentication != null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                    Principal principal = new UserPrincipal(authentication.getName());
                    log.debug("Current principal: {}", principal);
                    accessor.setUser(principal);
                    System.out.println("Principal: "+ accessor);
                }
            }
        }
        return message;
    }
}
