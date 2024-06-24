// Purpose: Contains the EditMessageRequest class. This class is used to edit the content of a message.

package com.axis.team4.codecrafters.message_service.request;

public class EditMessageRequest {
    private String newContent;

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }
}
