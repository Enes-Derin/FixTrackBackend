package com.enesderin.FixTrackBackend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {


    private MessageType messageType;
    private String staticMessage;

    public ErrorMessage(MessageType messageType) {
        this.messageType = messageType;
    }

    public String prepareErrorMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(messageType.getMessage());
        if (staticMessage != null) {
            builder.append(" : "+staticMessage);
        }
        return builder.toString();
    }
}
