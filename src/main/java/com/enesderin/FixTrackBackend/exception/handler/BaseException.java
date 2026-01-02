package com.enesderin.FixTrackBackend.exception.handler;


import com.enesderin.FixTrackBackend.exception.ErrorMessage;

public class BaseException extends RuntimeException {
    public BaseException(ErrorMessage message) {
        super(message.prepareErrorMessage());
    }
}
