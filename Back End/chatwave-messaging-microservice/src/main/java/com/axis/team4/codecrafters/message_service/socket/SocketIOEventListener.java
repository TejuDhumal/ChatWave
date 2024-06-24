// Purpose: Implementation of SocketIOEventListener class. This class listens to the events on the socket and performs the required operations.

package com.axis.team4.codecrafters.message_service.socket;

import com.axis.team4.codecrafters.message_service.modal.DataObject;
import com.axis.team4.codecrafters.message_service.modal.Message;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.stereotype.Component;

@Component
public class SocketIOEventListener {

    private final SocketIOServer socketIOServer;
    
    // This constructor initializes the SocketIO server and adds connect and disconnect listeners.

    public SocketIOEventListener(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
        this.socketIOServer.addConnectListener(client -> {
            System.out.println("Client connected: " + client.getSessionId());
        });
        this.socketIOServer.addDisconnectListener(client -> {
            System.out.println("Client disconnected: " + client.getSessionId());
        });
    }
    
    // This method is used to handle the join event.

    @OnEvent("join")
    public void onJoin(SocketIOClient client, AckRequest ackRequest, DataObject data) {
        client.joinRoom(data.getChatId());
        System.out.println("Client joined room: " + data.getChatId());
    }
    
    // This method is used to handle the leave event.

    @OnEvent("sendMessage")
    public void onSendMessage(SocketIOClient client, AckRequest ackRequest, Message message) {
        socketIOServer.getRoomOperations(message.getChatId()).sendEvent("newMessage", message);
        System.out.println("Message sent to room: " + message.getChatId());
    }
}
