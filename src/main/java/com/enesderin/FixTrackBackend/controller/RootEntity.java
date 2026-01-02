package com.enesderin.FixTrackBackend.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RootEntity<T> {

    private Integer status;
    private T payload;
    private String error;

    public static <T> RootEntity<T> success(T payload) {
        RootEntity<T> rootEntity = new RootEntity<>();
        rootEntity.setPayload(payload);
        rootEntity.setStatus(200);
        rootEntity.setError(null);
        return rootEntity;
    }

    public static <T> RootEntity<T> error(String error) {
        RootEntity<T> rootEntity = new RootEntity<>();
        rootEntity.setError(error);
        rootEntity.setStatus(400);
        rootEntity.setPayload(null);
        return rootEntity;
    }
}
