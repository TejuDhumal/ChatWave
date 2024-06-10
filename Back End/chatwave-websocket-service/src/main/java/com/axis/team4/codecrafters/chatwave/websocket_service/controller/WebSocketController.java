package com.axis.team4.codecrafters.chatwave.websocket_service.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Message send(Message message) {
        return new Message(HtmlUtils.htmlEscape(message.getContent()));
    }

    public static class Message {
        private String content;

        public Message() {
        }

        public Message(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
