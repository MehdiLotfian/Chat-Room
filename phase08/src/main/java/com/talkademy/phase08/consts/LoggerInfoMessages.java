package com.talkademy.phase08.consts;

public enum LoggerInfoMessages {
    MESSAGE_SENT("message sent"),
    MESSAGE_EDITED("message edited"),
    ROOM_CREATED("room created"),
    ROOM_DELETED("room deleted"),
    USER_JOINED_THE_ROOM("user joined the room"),
    USER_LEFT_THE_ROOM("user left the room"),
    USER_SIGNED_UP("user signed up"),
    USER_LOGGED_IN("user logged in"),
    USER_STATUS_UPDATED("user status updated"),
    USER_LOGGED_OUT("user logged out");

    private final String label;

    LoggerInfoMessages(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
