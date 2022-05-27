package uz.agro.security.controller.socketController;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import uz.agro.security.model.MessageModel;
import uz.agro.security.service.UserService;

@Controller
public class MessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    public MessageController(SimpMessagingTemplate messagingTemplate, UserService userService) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
    }
    @MessageMapping("/test")
    @SendTo("/topic/message")
    public MessageModel sendMessage(@Payload MessageModel message){
        return new MessageModel(HtmlUtils.htmlEscape(message.getMessage()));
    }

    @MessageMapping("/private/me")
    @SendToUser(value = "/user/topic/private-message", broadcast = false)
    public MessageModel sendPrivateMessage(@Payload MessageModel message){
        messagingTemplate.convertAndSendToUser(message.getTo(), "/user/topic/private-message", message);
//        messagingTemplate.convertAndSend("/topic/"+message.getTo(), message);
        return new MessageModel(HtmlUtils.htmlEscape(message.getMessage()), HtmlUtils.htmlEscape(message.getFrom()), HtmlUtils.htmlEscape(message.getTo()));
    }

}
