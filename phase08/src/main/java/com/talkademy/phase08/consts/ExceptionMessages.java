package com.talkademy.phase08.consts;

public enum ExceptionMessages {
    ACTION_NOT_ALLOWED("action not allowed"),
    ACTION_EXPIRED("action is expired"),
    MESSAGE_NOT_FOUND("message is not found"),
    ROOM_NOT_FOUND("room is not found"),
    USER_NOT_FOUND("user is not found"),
    UNIQUE_ROOM_NAME_VIOLATION("unique roomName violation"),
    UNIQUE_USER_NAME_VIOLATION("unique userName violation");

    private final String label;

    ExceptionMessages(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
